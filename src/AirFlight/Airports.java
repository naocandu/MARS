package AirFlight;

import XMLparser.parseAirports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;

import AirFlight.Airport;

public class Airports {
	static List airportsList = new ArrayList();
	
	public static List getAirportList() throws DocumentException{
		List airportCode = parseAirports.getCode();
		
		for (Iterator itr = airportCode.iterator(); itr.hasNext();) {  
			  String code = (String) itr.next();  
			  airportsList.add(Airport.getAirport(code));
			} 
		return airportsList;
	}
	
	public static List getAirportName() throws DocumentException{
		return parseAirports.getName();
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getAirportList());
		System.out.println(getAirportName());
	}

}
