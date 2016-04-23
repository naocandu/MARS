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
	
	
	/**
	 * gets a list of airports
	 * @return a raw list of airports or an empty list if an error is found
	 */
	
	public static List<?> getAirportList(){
		if (airportsList.size() != 0)
			return airportsList;
		
		List airportCode = ValidationController.Instance().GetAirportList();
		
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
	
	/**
	 * gets airport information given airport code
	 * @param Code Airport code
	 * @return specific airport information
	 */
	
	public static Airport GetAirport(String Code)
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
	
	
	/**
	 * gets timezone offset information given airport code
	 * @param Code Airport code
	 * @return timezone offset information
	 */
	
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
			getAirportList();
		}
		
		for (int i = 0;i < AirportList.size();i++)
		{
			Preloader t = new Preloader(AirportList.get(i).Code, DepartureDate);
			t.start();
		}
		
		//Preloader.WaitforAll();
	}
	
	/**
	 * gets airport names
	 * @return a string array containing airport names
	 */
	
	public static String[] getAirportName() throws DocumentException{
		
		List<String> a = ValidationController.Instance().getAirportsNames();
		String[] b = a.toArray(new String[a.size()]);
		return  b;
	}
	
	/**
	 * gets airport codes and names
	 * @return a string array containing airport codes and names
	 */
	public static String[] getAirportInfo() throws DocumentException{
		List<String> a = ValidationController.Instance().GetAirportInfo();
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
