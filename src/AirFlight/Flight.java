package AirFlight;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import Controller.ValidationController;
import XMLparser.parseFlights;
import Utility.*;

public class Flight {
	static Map singleFlight;
	
	public String Airplane_Model;
	public int Flightnumber;
	public String Departure_Airport;
	public DateTime DepartureTime;
	public String Arrival_Airport;
	public DateTime ArrivalTime;
	public int Seats_FC;
	public int Seats_EC;
	public Money Price_FC;
	public Money Price_EC;
	
	private int FC_cap;
	private int EC_cap;
	
	/**
	 * gets all flights departing or arriving to/from an airport on a given date
	 * @param number Flight number
	 * @param airport_code Airport code
	 * @param departure_date Departure date
	 * @param depart boolean value.True means departing flight and false means arrival flight
	 * @return a raw list of flights or an empty list if an error is found
	 */
	
	public static Map getFlight(int number, String airport_code,
			String departure_date,boolean depart) throws DocumentException{
		List Flights= ValidationController.Instance().getFlights(airport_code, departure_date, depart);
		for (Iterator itr=Flights.iterator(); itr.hasNext();){
			Map Flight = (Map) itr.next();
			if (Flight.get("Flightnumber").equals(number)){
				singleFlight=Flight;
			}
		}
		return singleFlight;
	}
	
	public Flight(String Airplane_Model, int Flightnumber, String Departure_Airport, 
			String DepartureTime, String Arrival_Airport, String ArrivalTime, int Seats_FC, 
			int Seats_EC, String Price_FC, String Price_EC)
	{
		this.DepartureTime = new DateTime();
		this.ArrivalTime = new DateTime();
		
		this.Price_FC = new Money();
		this.Price_EC = new Money();
		
		this.Airplane_Model = Airplane_Model;

		Airplane airplane = ValidationController.Instance().GetAirplane(this.Airplane_Model);
		this.FC_cap = airplane.FirstClassCapacity();
		this.EC_cap = airplane.EconomyCapacity();
		
		this.Flightnumber = Flightnumber;
		this.Departure_Airport = Departure_Airport;
		this.DepartureTime.Set(DepartureTime, "YYYY MMM DD hh:mm zzz");
		this.Arrival_Airport = Arrival_Airport;
		this.ArrivalTime.Set(ArrivalTime, "YYYY MMM DD hh:mm zzz");
		this.Seats_FC = Seats_FC;
		this.Seats_EC = Seats_EC;
		this.Price_FC.Set(Price_FC);
		this.Price_EC.Set(Price_EC);
		
		try {
			this.DepartureTime.SetTimezoneOffset(Airports.GetTimezoneOffset(Departure_Airport));
			this.ArrivalTime.SetTimezoneOffset(Airports.GetTimezoneOffset(Arrival_Airport));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Check the availability of seats
	 * @param FirstClass True means firstclass and false means economy class
	 * @return boolean True means seats of a specific class are available or otherwise.
	 */
	
	public boolean CheckAvailableSeating(boolean FirstClass)
	{
		return (FirstClass?(Seats_FC < this.FC_cap):(Seats_EC < this.EC_cap));
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getFlight(1560, "BOS", "2016_05_10", false));
		
		Map flight = getFlight(1560, "BOS", "2016_05_10", false);
		
		DateTime d = new DateTime();
		d.Set("2016 May 10 02:47 GMT","YYYY MMM DD hh:mm zzz");

		System.out.println(d.getFullDateString());
		
	}

}
