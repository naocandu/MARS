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
	
	public static String[] getAirportName() throws DocumentException{
		
		List<String> a = parseAirports.getName();
		String[] b = a.toArray(new String[a.size()]);
		return  b;
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getAirportList());
		for(int i=0;i<52;i++)
		System.out.println(getAirportName()[i]);
	}

}
