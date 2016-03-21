package Controller;

import java.util.ArrayList;
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
	
	public static void LinkFlights(String departure, String arrival, boolean firstClass)
	{
		Random rand = new Random();
		int n = rand.nextInt(20);
		
		if (trip == null)
			trip = new ArrayList<Trip>();
		else
			trip.clear();
		
		if (rand.nextInt(4)==0)
			return;
		
		for (int i = 0;i < n;i++)
		{
			trip.add(new Trip());
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
		Trips.LinkFlights("BOS", "AUS", true);
		
		if (Trips.GetNumberofTrips() == 0)
			System.out.println("No Flights Found");
		else
		{
			for (int i = 0;i < Trips.GetNumberofTrips();i++)
			{
				System.out.println("Trip " + i + ": " +
						Trips.Get(i).GetDuration() + " flight time, " +
						"$" + Trips.Get(i).GetPrice() + ", with " + 
						Trips.Get(i).GetNumberofHops() + " hops. " +
						(Trips.Get(i).ContainsMixedSeating()==true?
								" Note: This trip contains a flight that does not match your seating preference.":""));
			}
		}
	}
}
