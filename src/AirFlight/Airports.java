package AirFlight;

import XMLparser.parseAirports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;

import AirFlight.Airport;
import Controller.ValidationController;
import Utility.DateTime;

public class Airports {
	private static List airportsList = new ArrayList();
	private static List<Airport> AirportList = new ArrayList<Airport>();
	
	public static List getAirportList() throws DocumentException{
		if (airportsList.size() != 0)
			return airportsList;
		
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
	
	public static void Clear()
	{
		if (AirportList.size() == 0)
			return;
		
		for (int i = 0;i < AirportList.size();i++)
		{
			AirportList.get(i).Clear();
		}
		
		AirportList.clear();
		airportsList.clear();
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
		if (AirportList.size() == 0)
			getAirportList();
		
		for (int i = 0;i < AirportList.size();i++)
		{
			if (AirportList.get(i).Code.compareTo(Code)==0)
			{
				return AirportList.get(i).GetTimezoneOffset();
			}
		}
		
		return 0;
	}
	
	public static void PreloadAirports(DateTime DepartureDate)
	{
		if (AirportList.size() == 0)
		{
			try {
				getAirportList();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i = 0;i < AirportList.size();i++)
		{
			Preloader t = new Preloader(AirportList.get(i).Code, DepartureDate);
			t.start();
		}
		
		//Preloader.WaitforAll();
	}
	
	public static String[] getAirportName() throws DocumentException{
		
		List<String> a = parseAirports.getName();
		String[] b = a.toArray(new String[a.size()]);
		return  b;
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
		System.out.println(getAirportName()[i]);
		System.out.println(getAirportInfo());
	}

}
