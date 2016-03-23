package Controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import AirFlight.*;
import Utility.*;

public class Trip {
	private int num_hops = 0;
	private Money totalPrice = null;
	private float totalTime = 0;
	private boolean mixSeating = false;
	
	private static int id = 0;
	
	private List<Flight> trip = new ArrayList<Flight>();
	
	private int ID;
	
	public int GetNumberofHops()
	{
		return num_hops;
	}
	
	public int GetID()
	{
		return ID;
	}
	
	public float GetPrice()
	{
		return totalPrice.toFloat();
	}
	
	public float GetDuration()
	{
		return totalTime;
	}
	
	public Trip Clone()
	{
		Trip t = new Trip();
		for (int i = 0;i < trip.size();i++)
			t.AddFlight(trip.get(i));
		
		return t;
	}
	
	public boolean ContainsMixedSeating()
	{
		return mixSeating;
	}
	
	public void AddFlight(Flight flight)
	{
		trip.add(flight);
		num_hops++;
		totalPrice.Add(flight.Price_EC);
	}
	
	public String GetDepartureAirport()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(0).Departure_Airport;
	}
	
	public String GetArrivalAirport()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(trip.size()-1).Arrival_Airport;
	}
	
	public String GetDepartureTime()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(0).DepartureTime.getFullDateString();
	}
	
	public String GetArrivalTime()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(trip.size()-1).ArrivalTime.getFullDateString();
	}
	
	//toString() will transform trip into a string.
	public String toString()
	{
		String ports = "";
		for (int i = 0;i < trip.size();i++)
		{
			ports += trip.get(i).Departure_Airport + " -- ";
		}
		
		String display = ports + GetArrivalAirport() + "\n" + 
				GetDepartureTime() + " -- " + GetArrivalTime() + "  duration: " + totalTime + "\n" +
		"hops: " + num_hops + "  price: " + totalPrice.toString();
		return display;
	}
	
	public Trip()
	{
		ID = id++;
		this.totalPrice = new Money();
		/*Random rand = new Random();
		this.num_hops = rand.nextInt(2);
		this.totalPrice = (float) ((rand.nextInt()%(30000-10000)+10000)/100.0);
		this.totalTime = (float) ((rand.nextInt()%(2400-100)+100)/100.0);
		
		if (this.num_hops > 0 && rand.nextInt(5)==0)
			this.mixSeating = true;
		
		if (this.totalPrice < 0)
			this.totalPrice *= -1;
		
		if (this.totalTime < 0)
			this.totalTime *= -1;*/
	}
	
	public Trip(int hops, float price, float time, boolean mixSeating)
	{
		ID = id++;
		this.num_hops = hops;
		this.totalPrice = new Money(price);
		this.totalTime = time;
		this.mixSeating = mixSeating;
	}
	
	
}
