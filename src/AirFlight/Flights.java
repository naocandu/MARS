package AirFlight;

import java.util.List;

import org.dom4j.DocumentException;

import XMLparser.parseFlights;

public class Flights {
	
	public static List getFlights(String airport_code, 
			String departure_date, boolean depart) throws DocumentException{
		return parseFlights.getFlights(airport_code, departure_date, depart);
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getFlights("BOS", "2016_05_10", true));
	}

}
