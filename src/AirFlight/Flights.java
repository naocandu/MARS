package AirFlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import Controller.ValidationController;
import XMLparser.parseFlights;
import Utility.DateTime;

public class Flights {
	public List<Flight> departing = null;
	
	
	/**
	 * gets all flights departing or arriving to/from an airport on a given date
	 * @param airport_code 
	 * @param date
	 * @return a raw list of flights or an empty list if an error is found
	 */
	
	//private static List<Flights> found_flights = new ArrayList<Flights>;
	public static Flights GetFlightsFromAirport(String airport_code, DateTime date)
	{
		if (airport_code.length() != 3)
			return null;
		
		Airport airport = Airports.GetAirport(airport_code);
		
		if (airport == null)
			return null;
		
		return airport.GetDepartureFlights(date);
	}
	
	public Flights(String airport_code, DateTime date)
	{
		List<Map> raw_departure = null;
		List<Map> raw_departure_ND = null;
		
		DateTime nextday = DateTime.NextDay(date);
		
		raw_departure = ValidationController.Instance().getFlights(airport_code, date.getDateString(), true);
		raw_departure_ND = ValidationController.Instance().getFlights(airport_code, nextday.getDateString(), true);

		if (raw_departure == null)
		{
			return;
		}
		
		departing = new ArrayList<Flight>();
		
		for (int i = 0;i < raw_departure.size();i++)
		{
			if (raw_departure.get(i) == null)
			{
				departing.clear();
				departing = null;
				ValidationController.Instance().ReportError(600);
				return;
			}
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

		for (int i = 0;i < raw_departure_ND.size();i++)
		{
			if (raw_departure_ND.get(i) == null)
			{
				departing.clear();
				departing = null;
				return;
			}
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
	
	/**
	 * gets all flights departing or arriving to/from an airport on a given date
	 * @param airport_code 
	 * @param date
	 * @param depart boolean value.True means departing flight and false means arrival flight
	 * @return a raw list of flights or an empty list if an error is found
	 */
			
	public static List getFlights(String airport_code, String departure_date, boolean depart)
	{
		return ValidationController.Instance().getFlights(airport_code, departure_date, depart);
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getFlights("BOS", "2016_05_10", true));
	}

}
