package Controller;

import java.util.List;
import java.util.ArrayList;
import AirFlight.*;
import Utility.*;

public class Trip {
	private int num_hops = 0;
	private Money totalPrice = null;
	private float totalTime = 0;
	private boolean mixSeating = false;
	
	private static int id = 0;
	
	private List<Flight> trip = new ArrayList<Flight>();
	private List<String> seating = new ArrayList<String>();
	
	private int ID;
	
	public int GetNumberofHops()
	{
		return num_hops;
	}
	
	public int GetID()
	{
		return ID;
	}
	
	public String GetFlightSequence()
	{
		String sequence = "";
		for (int i = 0;i < trip.size();i++)
		{
			sequence += seating.get(i) + trip.get(i).Flightnumber+ " " + " " + " ";
		}
		
		return sequence;
	}
	
	public List<String> ListFlightSequence()
	{
		List<String> seq = new ArrayList<String>();
		
		for (int i = 0;i < trip.size();i++)
		{
			seq.add(seating.get(i) + trip.get(i).Flightnumber);
		}
		
		return seq;
	}
	
	public String GetFlightSequenceShow()
	{
		String sequence = "";
		for (int i = 0;i < trip.size();i++)
		{
			sequence += seating.get(i) + trip.get(i).Flightnumber+ "&nbsp;" + "&nbsp;" + "&nbsp;";
		}
		
		return sequence;
	}
	
	public String GetAiportSequence()
	{
		String sequence = (trip.size()!=0?trip.get(0).Departure_Airport:"");

		for (int i = 0;i < trip.size();i++)
		{
			sequence += "_" + trip.get(i).Arrival_Airport;
		}
		
		return sequence;
	}
	
	public int GetDepartureTimeinMinutes()
	{
		return trip.get(0).DepartureTime.GetLocalTimeinMinutes();
	}
	
	public int GetArrivalTimeinMinutes()
	{
		return trip.get(trip.size()-1).ArrivalTime.GetLocalTimeinMinutes();
	}
	
	public float GetPrice()
	{
		return totalPrice.toFloat();
	}
	
	public float GetDuration()
	{
		if (trip.size() == 0)
			return 0;
		else
			return (float)DateTime.NumericSpan(trip.get(0).DepartureTime, trip.get(trip.size()-1).ArrivalTime);
	}
	
	public String GetDepartureDate()
	{
		return trip.get(0).DepartureTime.getLocalDateString();
	}
	
	public String TimeDifference()
	{
		if (trip.size() == 0)
			return "";
		else
			return DateTime.TimeSpan(trip.get(0).DepartureTime, trip.get(trip.size()-1).ArrivalTime).getDurationString();
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
	
	public void Merge(Trip other)
	{
		for (int i = 0;i < other.trip.size();i++)
		{
			trip.add(other.trip.get(i));
			seating.add(other.seating.get(i));
		}
	}
	
	public boolean AddFlight(Flight flight)
	{
		double layover = 0;
		
		if (this.GetAiportSequence().contains(flight.Arrival_Airport))
			return false;
		
		if (trip.size() > 0)
		{
			layover = DateTime.NumericSpan(trip.get(trip.size()-1).ArrivalTime,flight.DepartureTime)*60;
			
			if (layover < ValidationController.GetMinLayoverMinutes() || layover > ValidationController.GetMaxLayoverMinutes())
				return false;
		}
		
		if (flight.CheckAvailableSeating(Trips.GetPreferredSeating()))
			seating.add((Trips.GetPreferredSeating()?"F":"E"));
		else if (flight.CheckAvailableSeating(!Trips.GetPreferredSeating()))
		{
			seating.add((Trips.GetPreferredSeating()?"E":"F"));
			this.mixSeating = true;
		}
		
		trip.add(flight);
		num_hops++;
		totalPrice.Add((seating.get(seating.size()-1).compareTo("E") == 0?flight.Price_EC:flight.Price_FC));
		return true;
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
			return trip.get(0).DepartureTime.getLocalFullDateString();
	}
	
	public String GetArrivalTime()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(trip.size()-1).ArrivalTime.getLocalFullDateString();
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
				GetDepartureTime() + " -- " + GetArrivalTime() + "  duration: " + TimeDifference() + "\n" +
		"hops: " + num_hops + "  price: " + totalPrice.toString();
		return display;
	}
	public String toStringShow()
	{
		String ports = "";
		for (int i = 0;i < trip.size();i++)
		{
			ports += trip.get(i).Departure_Airport + " -- ";
		}
		
		String display = //ports + GetArrivalAirport() + "\n" + 
				GetDepartureTime() + " -- " + GetArrivalTime() + "  duration: " + TimeDifference() + "<br/>" +
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
