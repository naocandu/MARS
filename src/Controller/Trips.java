package Controller;

import java.util.ArrayList;
import AirFlight.*;
import Utility.*;
import java.util.List;
import java.util.Random;

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
	
	public static void LinkFlights(String departure, String arrival, DateTime Date, boolean firstClass)
	{
		if (trip == null)
			trip = new ArrayList<Trip>();
		else
			trip.clear();
		
		ValidationController.Instance();
		
		System.out.println("\nSearching for flights from " + departure + " to " + arrival + " departing on " + Date.getDateString() + "...");
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
				boolean valid = true;
				for (int j = 0;j < explored.size();j++)
				{
					if (search.get(i).compareTo(explored.get(j)) == 0)
					{
						valid = false;
						break;
					}
				}
				
				//if (!valid)
				//	continue;
				
				explored.add(search.get(i));
				
				Flights f = new Flights(search.get(i),Date);
				
				if (potential.size() == 0)
				{
					for (int k = 0;k < f.departing.size();k++)
					{
						Trip t = new Trip();
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
						t_prime.AddFlight(f.departing.get(k));
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
				potential.add(temp.get(j));
			temp.clear();

		}
		
		for (int i = 0;i < potential.size();i++)
		{
			if (potential.get(i).GetDepartureAirport().compareTo(departure)==0 &&
					potential.get(i).GetArrivalAirport().compareTo(arrival)==0)
				trip.add(potential.get(i));
		}
		
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

		Trips.LinkFlights("BOS", "AUS", d, true);
		System.out.println("\n\n" + Trips.GetNumberofTrips() + " Flights Found\n");
		if (Trips.GetNumberofTrips() != 0)
		{
			System.out.println("Displaying " + (Trips.GetNumberofTrips()>10?"first 10 ":"all ") + "trips:");
			for (int i = 0;i < (Trips.GetNumberofTrips()>10?10:Trips.GetNumberofTrips());i++)
			{
				System.out.println(Trips.trip.get(i).toString());
			}
		}
	}
}
