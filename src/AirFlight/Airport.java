package AirFlight;

import java.util.List;
import java.util.ArrayList;
import Utility.*;
import org.dom4j.DocumentException;

import Controller.ValidationController;
import XMLparser.parseAirports;

public class Airport {
	public String Code;
	public String Name;
	public float Latitude;
	public float Longitude;
	
	private int timezoneOffset;
	private boolean TimezoneSet = false;
	
	private Flights outbound = null;
	private String outboundDate = "";
	
	public static List getAirport(String code){
		return parseAirports.readXML().get(code);
	}
	
	private Flights GetDepartureFlights_Unsafe(DateTime Date)
	{
		if (outbound == null || outboundDate.compareTo(Date.getDateString())!= 0)
		{
			
			outbound = new Flights(Code, Date);
			
			if (outbound.departing == null)
			{
				outbound = null;
				return outbound;
			}
			
			outboundDate = Date.getDateString();
		}
		
		return outbound;
	}
	
	private Flights GetDepartureFlights_Safe(DateTime Date)
	{
		if (outbound == null || outboundDate.compareTo(Date.getDateString())!= 0)
		{
			synchronized(Airport.class)
			{
				if (outbound == null || outboundDate.compareTo(Date.getDateString())!= 0)
				{
					outbound = new Flights(Code, Date);
					
					if (outbound.departing == null)
					{
						outbound = null;
						return outbound;
					}
					
					outboundDate = Date.getDateString();
				}
			}
		}
		
		return outbound;
	}
	
	public Flights GetDepartureFlights(DateTime Date)
	{
		if (ValidationController.Instance().isSafe())
			return GetDepartureFlights_Safe(Date);
		
		return GetDepartureFlights_Unsafe(Date);
	}
	
	public void Clear()
	{
		outbound = null;
		outboundDate = "";
	}
	
	public Airport()
	{
		Code = "N/A";
		Name = "Unknown";
		Latitude = 0;
		Longitude = 0;
	}
	
	public int GetTimezoneOffset()
	{
		if (!TimezoneSet)
		{
			try {
				timezoneOffset = ValidationController.Instance().GetTimezoneOffset(Latitude, Longitude);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			TimezoneSet = true;
		}
		
		return timezoneOffset;
	}
	
	public Airport(String Code, String Name, float Latitude, float Longitude)
	{
		Set(Code,Name,Latitude,Longitude);
	}
	
	public void Set(String Code, String Name, float Latitude, float Longitude)
	{
		this.Code = Code;
		this.Name = Name;
		this.Latitude = Latitude;
		this.Longitude = Longitude;
	}
	
	public static void main(String[] args){
		System.out.print("Test:\n");
		System.out.println(getAirport("LAX"));
	}

}
