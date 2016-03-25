package AirFlight;

import XMLparser.parseAirports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;

import AirFlight.Airport;
import Controller.ValidationController;

public class Airports {
	private static List airportsList = new ArrayList();
	private static List<Airport> AirportList = new ArrayList<Airport>();
	
	public static List getAirportList() throws DocumentException{
		List airportCode = parseAirports.getCode();
		
		for (Iterator itr = airportCode.iterator(); itr.hasNext();) {  
			  String code = (String) itr.next();  
			  airportsList.add(Airport.getAirport(code));
			} 
		
		for (int i = 0;i < airportsList.size();i++)
		{
			List raw = (List)airportsList.get(i);
			AirportList.add(new Airport((String)raw.get(0),(String)raw.get(1),(float)raw.get(2),(float)raw.get(3)));
		}
		
		return airportsList;
	}
	
	public static Airport GetAirport(String Code) throws DocumentException
	{
		if (AirportList.size() == 0)
			getAirportList();
		
		for (int i = 0;i < AirportList.size();i++)
		{
			if (AirportList.get(i).Code.compareTo(Code)==0)
			{
				return AirportList.get(i);
			}
		}
		
		return null;
	}
	
	public static int GetTimezoneOffset(String Code) throws DocumentException
	{	
		//if (AirportList.size() == 0)
		//	getAirportList();
		
		for (int i = 0;i < AirportList.size();i++)
		{
			if (AirportList.get(i).Code.compareTo(Code)==0)
			{
				return AirportList.get(i).GetTimezoneOffset();
			}
		}
		
		return 0;
	}

	public static String[] getAirportInfo() throws DocumentException{
		List<String> a = parseAirports.getInfo();
		String[] b = a.toArray(new String[a.size()]);
		return  b;
	}


	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getAirportList());
		for(int i=0;i<52;i++)
		System.out.println(getAirportInfo()[i]);
	}

}
