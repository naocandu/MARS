package Controller;

import java.util.ArrayList;
import java.util.Calendar;

import AirFlight.*;
import Server.ServerInterface;
import Utility.*;
import java.util.List;

import org.dom4j.DocumentException;

public class Trips {
	private static List<Trip> trip = null;
	
	public static int GetNumberofTrips()
	{
		return (trip==null?0:trip.size());
	}

	public static Trip Get(int index)
	{
		return ((trip == null || index >= trip.size())?null:trip.get(index));
	}
	
	public static List<Trip> LinkFlights(String departure, String arrival, String localDate, boolean firstClass)
	{
		DateTime date = new DateTime();
		date.Set(localDate , "YYYY_MM_DD");
		
		Airports.PreloadAirports(date);
		
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
				
				Flights f = Flights.GetFlightsFromAirport(search.get(i), date);
				
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
		return trip;
	}
	
	public static Trip MergeTrips(Trip outbound, Trip returning)
	{
		return new Trip(outbound.GetNumberofHops()+returning.GetNumberofHops(),
				outbound.GetPrice()+returning.GetPrice(),
				outbound.GetDuration()+returning.GetDuration(),
				outbound.ContainsMixedSeating()||returning.ContainsMixedSeating());
	}
	
	public static void main(String[] args) {
		DateTime d = new DateTime();
		d.Set("2016 May 04 02:47 GMT","YYYY MMM DD hh:mm zzz");


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
	}
}
