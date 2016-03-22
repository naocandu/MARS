package AirFlight;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import XMLparser.parseFlights;


public class Flight {
	static Map singleFlight;
	
	public static Map getFlight(int number, String airport_code,
			String departure_date,boolean depart) throws DocumentException{
		List Flights= parseFlights.getFlights(airport_code, departure_date, depart);
		for (Iterator itr=Flights.iterator(); itr.hasNext();){
			Map Flight = (Map) itr.next();
			if (Flight.get("Flightnumber").equals(number)){
				singleFlight=Flight;
			}
		}
		return singleFlight;
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getFlight(1560, "BOS", "2016_05_10", false));
	}

}
