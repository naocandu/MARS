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
	
	/**
	 * gets the number of hops of the trip
	 * @return number of hops
	 */
	public int GetNumberofHops()
	{
		return num_hops;
	}
	
	/**
	 * gets the id of the trip
	 * @return the id of the trip object
	 */
	public int GetID()
	{
		return ID;
	}
	
	/**
	 * gets the string representation of flight numbers and seating options of the trip
	 * @return string of trip flights and seating
	 */
	public String GetFlightSequence()
	{
		String sequence = "";
		for (int i = 0;i < trip.size();i++)
		{
			sequence += seating.get(i) + trip.get(i).Flightnumber+ " " + " " + " ";
		}
		
		return sequence;
	}
	
	/**
	 * gets a list of string representations of each flight with associated seating option
	 * @return list of flight numbers and seating options
	 */
	public List<String> ListFlightSequence()
	{
		List<String> seq = new ArrayList<String>();
		
		for (int i = 0;i < trip.size();i++)
		{
			seq.add(seating.get(i) + trip.get(i).Flightnumber);
		}
		
		return seq;
	}
	
	/**
	 * gets the string representation of flight numbers and seating options for display to the application window
	 * @return string of trip flights and seating
	 */
	public String GetFlightSequenceShow()
	{
		String sequence = "";
		for (int i = 0;i < trip.size();i++)
		{
			sequence += seating.get(i) + trip.get(i).Flightnumber+ "&nbsp;" + "&nbsp;" + "&nbsp;";
		}
		
		return sequence;
	}
	
	/**
	 * gets the string representation of the trips sequence of airport hops
	 * @return sequence of airports
	 */
	public String GetAiportSequence()
	{
		String sequence = (trip.size()!=0?trip.get(0).Departure_Airport:"");

		for (int i = 0;i < trip.size();i++)
		{
			sequence += "_" + trip.get(i).Arrival_Airport;
		}
		
		return sequence;
	}
	
	/**
	 * gets the numeric value of departure time in minutes since epoch
	 * @return absolute departure time value
	 */
	public int GetDepartureTimeinMinutes()
	{
		return trip.get(0).DepartureTime.GetLocalTimeinMinutes();
	}
	
	/**
	 * gets the numeric value of arrival time in minutes since epoch
	 * @return absolute arrival time value
	 */
	public int GetArrivalTimeinMinutes()
	{
		return trip.get(trip.size()-1).ArrivalTime.GetLocalTimeinMinutes();
	}
	
	/**
	 * gets the combined price of the trip as a float
	 * @return the price of the trip
	 */
	public float GetPrice()
	{
		return totalPrice.toFloat();
	}
	
	/**
	 * gets the number of days, hours, and minutes of the entire trip combined into a single float value
	 * @return the absolute time duration of the trip
	 */
	public float GetDuration()
	{
		if (trip.size() == 0)
			return 0;
		else
			return (float)DateTime.NumericSpan(trip.get(0).DepartureTime, trip.get(trip.size()-1).ArrivalTime);
	}
	
	/**
	 * gets the departure time in local time
	 * @return local departure time
	 */
	public String GetDepartureDate()
	{
		return trip.get(0).DepartureTime.getLocalDateString();
	}
	
	/**
	 * gets the duration of the trip as a string
	 * @return the duration of the trip
	 */
	public String TimeDifference()
	{
		if (trip.size() == 0)
			return "";
		else
			return DateTime.TimeSpan(trip.get(0).DepartureTime, trip.get(trip.size()-1).ArrivalTime).getDurationString();
	}
	
	/**
	 * gets a new trip object copied from this instance
	 * @return a copied trip object
	 */
	public Trip Clone()
	{
		Trip t = new Trip();
		for (int i = 0;i < trip.size();i++)
			t.AddFlight(trip.get(i));
		
		return t;
	}
	
	/**
	 * gets whether or not this trip contains flights that do not have the preferred seating available, but is still viable
	 * @return true if all flights comply with preferred seating, false otherwise
	 */
	public boolean ContainsMixedSeating()
	{
		return mixSeating;
	}
	
	/**
	 * merges another trip instance with this one without performing viablility checks
	 * presumed to be used solely for merging trips for round-trip booking
	 * @param other
	 */
	public void Merge(Trip other)
	{
		for (int i = 0;i < other.trip.size();i++)
		{
			trip.add(other.trip.get(i));
			seating.add(other.seating.get(i));
		}
	}
	
	/**
	 * attempts to add a flight to this trip while checking for available seating and layover time constraints
	 * @param flight
	 * @return true if the flight was added, false if the checks failed
	 */
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
	
	/**
	 * gets the departure airport of the trip
	 * @return the departure airport
	 */
	public String GetDepartureAirport()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(0).Departure_Airport;
	}
	
	/**
	 * gets the last arrival airport of the trip
	 * @return the arrival airport
	 */
	public String GetArrivalAirport()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(trip.size()-1).Arrival_Airport;
	}
	
	/**
	 * gets the departure time of the trip as a string
	 * @return the departure time
	 */
	public String GetDepartureTime()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(0).DepartureTime.getLocalFullDateString();
	}
	
	/**
	 * gets the arrival time of the trip as a string
	 * @return the arrival time
	 */
	public String GetArrivalTime()
	{
		if (trip.size() == 0)
			return "";
		else
			return trip.get(trip.size()-1).ArrivalTime.getLocalFullDateString();
	}
	
	/**
	 * gets the trip information as a string for display
	 * @return the trip display information
	 */
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
	
	/**
	 * gets the trip information as a string for display to the application window
	 * @return the application window-formatted display string
	 */
	public String toStringShow()
	{
		String ports = "";
		for (int i = 0;i < trip.size();i++)
		{
			ports += trip.get(i).Departure_Airport + " -- ";
		}
		
		String display = //ports + GetArrivalAirport() + "\n" + 
				GetDepartureTime() + " -- " + GetArrivalTime() + "  duration: " + TimeDifference() + "<br/>" +
				"hops: " + num_hops + "  price: " + totalPrice.toString() + (this.mixSeating==true?" mix":" no mix");
		return display;
	}
	
	public String confirmationString()
	{
		String display = "\n";
		for (int i = 0;i < trip.size();i++)
		{
			display += trip.get(i).Departure_Airport + " to " + trip.get(i).Arrival_Airport + 
					", Departing " + trip.get(i).DepartureTime.getLocalFullDateString() + " and arriving " +
					trip.get(i).ArrivalTime.getLocalFullDateString() + ", " + 
					(seating.get(i).compareTo("E")==0?"Economy":"First Class") + " Seating\n";
		}
		
		return display;
	}
	
	/**
	 * default constructor
	 */
	public Trip()
	{
		ID = id++;
		this.totalPrice = new Money();

	}
	
	/**
	 * parameterized constructor used presumably for testing
	 * @param hops
	 * @param price
	 * @param time
	 * @param mixSeating
	 */
	public Trip(int hops, float price, float time, boolean mixSeating)
	{
		ID = id++;
		this.num_hops = hops;
		this.totalPrice = new Money(price);
		this.totalTime = time;
		this.mixSeating = mixSeating;
	}
	
	
}
