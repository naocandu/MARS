package AirFlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import XMLparser.parseFlights;
import Utility.DateTime;

public class Flights {
	public List<Flight> departing = null;
	
	//private static List<Flights> found_flights = new ArrayList<Flights>;
	public static Flights GetFlightsFromAirport(String airport_code, DateTime date)
	{
			try {
				return Airports.GetAirport(airport_code).GetDepartureFlights(date);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	
	public Flights(String airport_code, DateTime date)
	{
		List<Map> raw_departure = null;
		List<Map> raw_departure_ND = null;
		
		DateTime nextday = DateTime.NextDay(date);
		
		
		try {
			raw_departure = parseFlights.getFlights(airport_code, date.getDateString(), true);
			raw_departure_ND = parseFlights.getFlights(airport_code, nextday.getDateString(), true);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		/*
		 * String Airplane_Model, int Flightnumber, String Departure_Airport,
		 * String DepartureTime, String Arrival_Airport, String ArrivalTime, int Seats_FC, 
		 * int Seats_EC, String Price_FC, String Price_EC
		*/
		if (raw_departure == null)
			return;
		
		departing = new ArrayList<Flight>();
		
		//long start = System.currentTimeMillis();
		for (int i = 0;i < raw_departure.size();i++)
		{
			ArrayList dep = (ArrayList)raw_departure.get(i).get("Departure");
			ArrayList avl = (ArrayList)raw_departure.get(i).get("Arrival");
			ArrayList fc = (ArrayList)raw_departure.get(i).get("FirstClass");
			ArrayList ec = (ArrayList)raw_departure.get(i).get("Coach");
			
			String dep_code = (String)dep.get(0);
			if (dep_code.compareTo(airport_code) != 0)
				continue;
			
			
			departing.add(new Flight((String)raw_departure.get(i).get("AirplaneModel"),
					(int)raw_departure.get(i).get("Flightnumber"),
					(String)dep.get(0),
					(String)dep.get(1),
					(String)avl.get(0),
					(String)avl.get(1),
					(int)fc.get(1),
					(int)ec.get(1),
					(String)fc.get(0),
					(String)ec.get(0)));
			
		}
		//System.out.println(System.currentTimeMillis()-start);
		
		for (int i = 0;i < raw_departure_ND.size();i++)
		{
			ArrayList dep = (ArrayList)raw_departure_ND.get(i).get("Departure");
			ArrayList avl = (ArrayList)raw_departure_ND.get(i).get("Arrival");
			ArrayList fc = (ArrayList)raw_departure_ND.get(i).get("FirstClass");
			ArrayList ec = (ArrayList)raw_departure_ND.get(i).get("Coach");
			
			String dep_code = (String)dep.get(0);
			if (dep_code.compareTo(airport_code) != 0)
				continue;
			
			departing.add(new Flight((String)raw_departure_ND.get(i).get("AirplaneModel"),
					(int)raw_departure_ND.get(i).get("Flightnumber"),
					(String)dep.get(0),
					(String)dep.get(1),
					(String)avl.get(0),
					(String)avl.get(1),
					(int)fc.get(1),
					(int)ec.get(1),
					(String)fc.get(0),
					(String)ec.get(0)));
		}
		
	}
			
	public static List getFlights(String airport_code, 
			String departure_date, boolean depart) throws DocumentException{
		return parseFlights.getFlights(airport_code, departure_date, depart);
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getFlights("BOS", "2016_05_10", true));
	}

}
