package Controller;

import java.util.ArrayList;
import AirFlight.*;
import Server.ServerInterface;
import Utility.*;
import java.util.List;

public class Trips {
	private static List<Trip> trip = null;
	private static boolean preferred_seating = true;
	private static boolean retry = true;
	
	/**
	 * gets the number of trips returned by the last search
	 * @return the number of trips
	 */
	public static int GetNumberofTrips()
	{
		return (trip==null?0:trip.size());
	}

	/**
	 * gets a single trip from the list returned by a search
	 * @param index
	 * @return a trip object with corresponding array index
	 */
	public static Trip Get(int index)
	{
		return ((trip == null || index >= trip.size())?null:trip.get(index));
	}
	
	/**
	 * gets the preferred seating option
	 * @return 1 if first class, 0 if economy
	 */
	public static boolean GetPreferredSeating()
	{
		//1 = first class,  0 = economy
		return preferred_seating;
	}
	
	/**
	 * searches and constructs a list of valid trips from a departure to arrival airport on a given date with seating preference
	 * @param departure
	 * @param arrival
	 * @param localDate
	 * @param firstClass
	 * @return the list of viable trips
	 */
	public static List<Trip> LinkFlights(String departure, String arrival, String localDate, boolean firstClass)
	{
		preferred_seating = firstClass;
		DateTime date = new DateTime();
		date.Set(localDate , "YYYY_MM_DD");
		
		Airports.PreloadAirports(date);
		
		
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//long start = System.currentTimeMillis();
		
		if (trip == null)
			trip = new ArrayList<Trip>();
		else
			trip.clear();
		
		
		ValidationController.Instance();
		
		System.out.println("\nSearching for flights from " + departure + " to " + arrival + " departing on " + date.getDateString() + "...");
		List<Trip> potential = new ArrayList<Trip>();
		
		List<String> search = new ArrayList<String>();
		List<String> next_search = new ArrayList<String>();
		List<String> explored = new ArrayList<String>();
		
		next_search.add(departure);
		
		explored.add(arrival);
		
		for (int h = 0;h < ValidationController.GetMaxHops();h++)
		{
			
			search.clear();
			for (int i = 0;i < next_search.size();i++)
			{	
				search.add(next_search.get(i));
			}
			next_search.clear();
			List<Trip> temp = new ArrayList<Trip>();
			
			for (int i = 0;i < search.size();i++)
			{
				explored.add(search.get(i));
				
				
				//Flights f = new Flights(search.get(i),date);
				//Preloader.WaitforThread(search.get(i));
				Flights f = Flights.GetFlightsFromAirport(search.get(i), date);
				if (f == null)
					continue;
				
				if (potential.size() == 0)
				{
					for (int k = 0;k < f.departing.size();k++)
					{
						if (f.departing.get(k).DepartureTime.getLocalDateString().compareTo(date.getDateString())!=0)
							continue;
						Trip t = new Trip();
						//System.out.println(f.departing.get(k).DepartureTime.getLocalDateString());
						t.AddFlight(f.departing.get(k));
						
						if (f.departing.get(k).Departure_Airport.compareTo(departure)==0)
							potential.add(t);
						
						boolean exists = false;
						
						for (int m = 0;m < next_search.size();m++)
						{
							if (next_search.get(m).compareTo(t.GetArrivalAirport()) == 0)
							{
								exists = true;
								break;
							}
						}
						
						if (!exists)
							next_search.add(t.GetArrivalAirport());
					}
					
					continue;
				}
				
				for (int j = 0;j < potential.size();j++)
				{
					if (potential.get(j).GetArrivalAirport().compareTo(search.get(i)) != 0 ||
							potential.get(j).GetArrivalAirport().compareTo(potential.get(j).GetDepartureAirport()) == 0 ||
							potential.get(j).GetArrivalAirport().compareTo(arrival) == 0)
						continue;
					
					
					Trip t = potential.remove(j);
					j--;
					for (int k = 0;k < f.departing.size();k++)
					{
						Trip t_prime = t.Clone();
						
						
						if (!t_prime.AddFlight(f.departing.get(k)))
							continue;
							
						temp.add(t_prime);
						boolean exists = false;
						
						for (int m = 0;m < next_search.size();m++)
						{
							if (next_search.get(m).compareTo(t_prime.GetArrivalAirport()) == 0)
							{
								exists = true;
								break;
							}
						}
						
						for (int m = 0;m < search.size() && !exists;m++)
						{
							if (search.get(m).compareTo(t_prime.GetArrivalAirport()) == 0)
							{
								exists = true;
								break;
							}
						}
						
						for (int m = 0;m < explored.size() && !exists;m++)
						{
							if (explored.get(m).compareTo(t_prime.GetArrivalAirport()) == 0)
							{
								exists = true;
								break;
							}
						}
						
						if (!exists)
							next_search.add(t_prime.GetArrivalAirport());
					}
				}
				
			}
			for (int j = 0;j < temp.size();j++)
			{
				potential.add(temp.get(j));
			}
			temp.clear();

		}
		System.out.println("Potential: " + potential.size());
		//System.out.println(date.getDateString());
		for (int i = 0;i < potential.size();i++)
		{
			if (potential.get(i).GetDepartureAirport().compareTo(departure)==0 &&
					potential.get(i).GetArrivalAirport().compareTo(arrival)==0)
			{
				boolean valid = true;
				for (int j = 0;j < trip.size();j++)
				{
					if (potential.get(i).GetFlightSequence().compareTo(trip.get(j).GetFlightSequence())==0 )
					{
						valid = false;
						break;
					}
					if (potential.get(i).GetID() == trip.get(j).GetID())
					{
						valid = false;
						break;
					}
				}
				if (valid)
					trip.add(potential.get(i));
			}
				
		}
		if (trip.size() == 0)
		{
			if (ValidationController.Instance().GetLastErrorCode() == 600)
			{
				if (retry)
				{
					retry = false;
					Preloader.WaitforAll();
					ValidationController.Instance().RefreshAll();
					ValidationController.Instance().SetSafety(true);
					trip = LinkFlights(departure, arrival, localDate, firstClass);
				}
			}
		}
		retry = true;
		return trip;
	}
	
	/**
	 * merges two trips into a single trip object for booking round trip
	 * @param outbound
	 * @param returning
	 * @return the combination of the two trip parameters
	 */
	public static Trip MergeTrips(Trip outbound, Trip returning)
	{
		Trip full = outbound.Clone();
		full.Merge(returning);
		return full;
	}
	
	/**
	 * main function test driver for searching flights
	 * @param args
	 */
	public static void main(String[] args) {
		
		for (int t = 0;t < 1;t++)
		{
			ValidationController.Reset();
			DateTime d = new DateTime();
			d.Set("2016 May 06 02:47 GMT","YYYY MMM DD hh:mm zzz");
	
			DateTime d2 = new DateTime();
			d2.Set("2016 May 06 01:47 GMT","YYYY MMM DD hh:mm zzz");
			
			
			DateTime d3 = DateTime.TimeSpan(d2, d);
	
			//System.out.println(d3.getFullDateString());
			//System.out.println(d3.getDurationString());
			//d.Set("2016-05-10", "YYYY-MM-DD");
			long start = System.currentTimeMillis();
			Trips.LinkFlights("BOS", "AUS", d.getDateString(), false);
			long end = System.currentTimeMillis();
			
			
			System.out.println("\n\n" + Trips.GetNumberofTrips() + " Flights Found in " + ((end-start)/1000.0) + " seconds\n");
			if (Trips.GetNumberofTrips() != 0)
			{
				System.out.println("Displaying " + (Trips.GetNumberofTrips()>10?"first 10 ":"all ") + "trips:");
				for (int i = 0;i < (Trips.GetNumberofTrips()>10?10:Trips.GetNumberofTrips());i++)
				{	
					System.out.println(Trips.trip.get(i).GetAiportSequence());
					System.out.println(Trips.trip.get(i).GetFlightSequence());
				
					System.out.println(Trips.trip.get(i).toString());
					System.out.println("\n");
				}
			}
			
			System.out.println("Total Queries: " + ServerInterface.DataBaseCalls);
			System.out.println("Airport Queries: " + ServerInterface.AirportCalls);
			System.out.println("Flight Queries: " + ServerInterface.FlightCalls);
			System.out.println("Timezone Queries: " + ServerInterface.TimezoneCalls);
			
			System.out.println(Trips.GetNumberofTrips() + " Flights Found in " + ((end-start)/1000.0) + " seconds\n");
			System.out.println("\n");
			if (Trips.GetNumberofTrips() == 0)
				break;
		}
	}
}
