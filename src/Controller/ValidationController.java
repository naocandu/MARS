package Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import Server.ServerInterface;
import Utility.DateTime;
import Server.ServerConstants;
import XMLparser.*;

public class ValidationController {
	
	private static ValidationController instance = null;
	private int min_layover = ValidationConstants.MIN_LAYOVER_MINUTES;
	private int max_layover = ValidationConstants.MAX_LAYOVER_MINUTES;
	private int max_hops = ValidationConstants.MAX_HOPS;
	public int verbose = 0;
	
	private void SetDefaultsFromConstants()
	{
		ServerInterface.SetTimeout(ServerConstants.TIMEOUT_MILLISECONDS);
		ServerInterface.SetReservationURL(ServerConstants.RESERVATION_URL);
		ServerInterface.SetTimezoneURL(ServerConstants.TIMEZONE_URL);
		ServerInterface.SetTeamName(ServerConstants.TEAM_ID);
		
		min_layover = ValidationConstants.MIN_LAYOVER_MINUTES;
		max_layover = ValidationConstants.MAX_LAYOVER_MINUTES;
		max_hops = ValidationConstants.MAX_HOPS;
	}
	
	private void SetDefaults() throws IOException
	{
		String line;
        BufferedReader in;
        FileReader fr;
        
        List<String> index = new ArrayList<String>();
        List<String> value = new ArrayList<String>();
        
        SetDefaultsFromConstants();

        try {
			fr = new FileReader(ValidationConstants.CONFIG_DIRECTORY);
			in = new BufferedReader(fr);
	        line = in.readLine();
	        while(line != null)
	        {
	        	   String[] pair = line.split("=");
	        	   if (pair.length == 2)
	        	   {
	        		   index.add(pair[0].trim());
	        		   value.add(pair[1].trim());
	        		   
	        		   System.out.println(index.get(index.size()-1) + " : " + value.get(value.size()-1));
	        	   }
	        	   
	        	   line = in.readLine();
	        }
	        in.close();
	        
            for (int i = 0;i < index.size();i++)
            {
	         	for (int j = 0;j < ValidationConstants.DEFAULT_LABEL.length;j++)
	         	{
	         		if (index.get(i).compareTo(ValidationConstants.DEFAULT_LABEL[j]) == 0)
	         		{
	         			switch(j)
	         			{
	         			case 0:
	         				ServerInterface.SetReservationURL(value.get(i));
	         				break;
	         			case 1:
	         				ServerInterface.SetTimezoneURL(value.get(i));
	         				break;	
	         			case 2:
	         				ServerInterface.SetTeamName(value.get(i));
	         				break;	
	         			case 3:
	         				min_layover = (Integer.parseInt(value.get(i))>=0?
	         						Integer.parseInt(value.get(i)):ValidationConstants.MIN_LAYOVER_MINUTES);
	         				break;	
	         			case 4:
	         				max_layover = (Integer.parseInt(value.get(i))>=0?
	         						Integer.parseInt(value.get(i)):ValidationConstants.MAX_LAYOVER_MINUTES);
	         				break;	
	         			case 5:
	         				max_hops = (Integer.parseInt(value.get(i))>=0?
	         						Integer.parseInt(value.get(i)):ValidationConstants.MAX_HOPS);
	         				break;	
	         			case 6:
	         				ServerInterface.SetTimeout(Integer.parseInt(value.get(i))>0?
	         						Integer.parseInt(value.get(i)):ServerConstants.TIMEOUT_MILLISECONDS);
	         				break;	
	         			}
	         		}
	         	}
            }
	        
            if (max_layover < min_layover)
            {
            	max_layover = ValidationConstants.MAX_LAYOVER_MINUTES;
            	min_layover = ValidationConstants.MIN_LAYOVER_MINUTES;
            }
           
		} catch (FileNotFoundException e) {
			SetDefaultsFromConstants();
			return;
		}
        
	}
	
	private ValidationController()
	{
		try {
			this.SetDefaults();
		} catch (IOException e) {
			this.SetDefaultsFromConstants();
		}
	}
	
	public static ValidationController Instance()
	{
		if (instance == null)
		{
			synchronized (ValidationController.class)
			{
				if(instance == null)
				{
					instance = new ValidationController();
				}
			}
		}
		
		return instance;
	}
	
	public static int GetMaxLayoverMinutes()
	{
		return ValidationController.Instance().max_layover;
	}
	
	public static int GetMinLayoverMinutes()
	{
		return ValidationController.Instance().min_layover;
	}
	
	public static int GetMaxHops()
	{
		return ValidationController.Instance().max_hops;
	}
	
	public int GetTimezoneOffset(float Latitude, float Longitude) throws DocumentException
	{
		String xml = ServerInterface.QueryTimezone(Latitude, Longitude);
		if (xml.length() == 0)
		{
			System.out.println("ERROR - QueryTimezone returned invalid response");
			return 0;
		}
		
		return ParseTime.timeOffset(xml).intValue();
	}
	
	public boolean ConfirmTrip(Trip trip)
	{
		List<String> f_sequence = trip.ListFlightSequence();
		
		if (this.verbose > 2)
		{
			System.out.println("Reserving flights:");
			for (int i = 0;i < f_sequence.size();i++)
			{
				System.out.println(f_sequence.get(i));
			}
		}
		
		if (this.verbose > 1)
			System.out.println("\nGenerating XML...");
		String xml = ReserveXML.ReserveXML(f_sequence);
		if (this.verbose > 1)
			System.out.println(xml);
		
		if (this.verbose > 1)
			System.out.println("\nSending to Server...");
		boolean result = ServerInterface.ReserveFlights(xml);
		if (this.verbose > 0)
			System.out.println("Server returned " + (result?"Success":"Failure"));
		
		return result;
	}
	
	public static void main(String[] args) {
		ValidationController.Instance().verbose = 3;
		
		ServerInterface.ResetDB();
		
		DateTime d = new DateTime();
		d.Set("2016 May 04 02:47 GMT","YYYY MMM DD hh:mm zzz");
		
		DateTime r = new DateTime();
		r.Set("2016 May 10 02:47 GMT","YYYY MMM DD hh:mm zzz");

		//simulate round trip from bos to aus
		//first outbound
		Trips.LinkFlights("BOS", "AUS", d.getDateString(), false);
		
		//user selects second trip
		Trip outbound = Trips.Get(1);
				
		//then the returning trip
		Trips.LinkFlights("AUS", "BOS", r.getDateString(), false);
		
		//user selects last trip
		Trip returning = Trips.Get(Trips.GetNumberofTrips()-1);
		
		//merge the two trips into one for reservation
		Trip full_trip = Trips.MergeTrips(outbound, returning);
		
		//send out for booking and check if successful
		boolean result = ValidationController.Instance().ConfirmTrip(full_trip);
		
		
		System.out.println("\n\n>>> Beginning Stress Test <<<");
		ValidationController.Instance().verbose = 1;
		ServerInterface.ResetDB();
		System.out.println("Server Reset");
		System.out.println("\nAttempting to overbook...");
		for (int i = 0;i < 100;i++)
		{
			if (!ValidationController.Instance().ConfirmTrip(full_trip))
			{
				System.out.println("\nCase Handled Successfully");
				break;
			}
		}
		
		ServerInterface.ResetDB();
	}

}
