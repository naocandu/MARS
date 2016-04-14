package Controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import AirFlight.Airplane;
import AirFlight.Airports;
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
	
	private String last_error = "NULL";
	private int last_error_code = 0;
	
	private boolean safemode = false;
	
	public String GetLastErrorMessage()
	{
		return last_error;
	}
	
	public int GetLastErrorCode()
	{
		return last_error_code;
	}
	
	public boolean isSafe()
	{
		return safemode;
	}
	
	public void SetSafety(boolean safe)
	{
		safemode = safe;
	}
	
	public String GetCodeMessage(int code)
	{
		String msg = ValidationConstants.RESPONSE_MESSAGE.getOrDefault(code, "Unknown Response");
		if (code != 200)
		{
			last_error = msg;
			last_error_code = code;
		}
		return msg;
	}
	
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
	
	private boolean PopulateAirports()
	{
		synchronized (ValidationController.class)
		{
			if (parseAirports.xml == null)
			{
				String xml = ServerInterface.QueryAirports();
				int response = ServerInterface.ParseResponseCode(xml);
				if (response != 200)
				{
					xml = "";
					System.out.println("Server returned " + response + ": " + GetCodeMessage(response));
					return false;
				}
				
				parseAirports.xml = xml;
			}
		}
		
		return true;
	}
	
	public List<?> GetAirportList()
	{
		if (!PopulateAirports())
			return new ArrayList();
		
		try {
			return parseAirports.getCode();
		} catch (DocumentException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}
	
	public List<String> getAirportsNames()
	{
		if (!PopulateAirports())
			return new ArrayList<String>();
		
		try {
			return parseAirports.getName();
		} catch (DocumentException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}
	
	public List<String> GetAirportInfo()
	{
		if (!PopulateAirports())
			return new ArrayList<String>();
		
		try {
			return parseAirports.getInfo();
		} catch (DocumentException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}
	
	public boolean PopulateAirplanes()
	{
		synchronized (ValidationController.class)
		{
			String xml = ServerInterface.QueryAirplanes();
			parseAirplanes.xml = xml;
			
			int response = ServerInterface.ParseResponseCode(xml);
			if (response != 200)
			{
				xml = "";
				System.out.println("Server returned " + response + ": " + GetCodeMessage(response));
				return false;
			}
		}
		
		return true;
	}
	
	public Airplane GetAirplane(String model)
	{
		synchronized (ValidationController.class)
		{
			String line = "";
	        BufferedReader in = null;
	        FileReader fr = null;
	        
	        for (int i = 0;i < 2;i++)
	        {
				try {
					fr = new FileReader(ValidationConstants.AIRPLANE_CACHE_DIRECTORY);
					break;
				} catch (FileNotFoundException e1) {
					try {
						FileWriter out = new FileWriter(ValidationConstants.AIRPLANE_CACHE_DIRECTORY, false);
						out.close();
						out = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        }
			in = new BufferedReader(fr);
	        try {
				line = in.readLine();
			
		        while(line != null)
		        {
		        	   String[] values = line.split(",");
		        	   if (values.length == 4)
		        	   {
		        		   String airplane_model = values[0].trim();
		        		   String name = values[1].trim();
		        		   int fc = Integer.parseInt(values[2].trim());
		        		   int ec = Integer.parseInt(values[3].trim());
		        		   
		        		   if (airplane_model.compareTo(model) == 0)
		        			   return new Airplane(name,airplane_model,fc,ec);
		        	   }
		        	   
		        	   line = in.readLine();
		        }
		        in.close();
	        } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        Airplane a = Airplane.findAirplane(model);
	        
	        try {
				FileWriter out = new FileWriter(ValidationConstants.AIRPLANE_CACHE_DIRECTORY, true);
				out.write(a.model + "," + a.name + "," + a.FC_Capacity + "," + a.EC_Capacity + "\n");
				out.close();
				out = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			return a;
		}
	}
	
	public List<?> GetAirplaneList()
	{
		if (!PopulateAirplanes())
			return new ArrayList();
		
		try {
			return parseAirports.getCode();
		} catch (DocumentException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}
	
	public List<Map> getFlights(String airport_code,String departure_date,boolean departure)
	{
		String xml = ServerInterface.QueryFlights(airport_code, departure_date, departure);
		
		int response = ServerInterface.ParseResponseCode(xml);
		if (response != 200)
		{
			xml = "";
			System.out.println("Server returned " + response + ": " + GetCodeMessage(response));
			return new ArrayList<Map>();
		}
		
		try {
			return parseFlights.getFlights(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
			return new ArrayList<Map>();
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
		synchronized (ValidationController.class)
		{
			String line = "";
	        BufferedReader in = null;
	        FileReader fr = null;
	        
	        for (int i = 0;i < 2;i++)
	        {
				try {
					fr = new FileReader(ValidationConstants.TIMEZONE_CACHE_DIRECTORY);
					break;
				} catch (FileNotFoundException e1) {
					try {
						FileWriter out = new FileWriter(ValidationConstants.TIMEZONE_CACHE_DIRECTORY, false);
						out.close();
						out = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        }
			in = new BufferedReader(fr);
	        try {
				line = in.readLine();
			
		        while(line != null)
		        {
		        	   String[] values = line.split(",");
		        	   if (values.length == 3)
		        	   {
		        		   double lat = Double.parseDouble(values[0].trim());
		        		   double lon = Double.parseDouble(values[1].trim());
		        		   
		        		   if ((lat - Latitude)*(lat - Latitude) < 0.025 && (lon - Longitude)*(lon - Longitude) < 0.025)
		        			   return Integer.parseInt(values[2].trim());
		        	   }
		        	   
		        	   line = in.readLine();
		        }
		        in.close();
	        } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String xml = ServerInterface.QueryTimezone(Latitude, Longitude);
			if (xml.indexOf("timezone")==-1)
			{
				System.out.println("ERROR - QueryTimezone returned invalid response");
				System.exit(0);
				return 0;
			}
			
			try {
				FileWriter out = new FileWriter(ValidationConstants.TIMEZONE_CACHE_DIRECTORY, true);
				out.write(Latitude + "," + Longitude + "," + ParseTime.timeOffset(xml).intValue() + "\n");
				out.close();
				out = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return ParseTime.timeOffset(xml).intValue();
		}
	}
	
	public void ReportError(int code)
	{
		if (code != 200)
		{
			last_error = GetCodeMessage(code);
			last_error_code = code;
			System.out.println("ERROR REPORTED: " + last_error);
		}
	}
	
	public static void Reset()
	{
		ServerInterface.ResetDB();
		Airports.Clear();
		parseAirplanes.xml = null;
		parseAirports.xml = null;
		instance = null;
	}
	
	public void RefreshAll()
	{
		Airports.Clear();
		parseAirplanes.xml = null;
		parseAirports.xml = null;
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
		String xml = ReserveXML.MakeXML(f_sequence);
		if (this.verbose > 1)
			System.out.println(xml);
		
		if (this.verbose > 1)
			System.out.println("\nSending to Server...");
		int result = ServerInterface.ReserveFlights(xml);
		if (this.verbose > 0)
			System.out.println("Server returned " + result + ": " + GetCodeMessage(result));
		
		if (result != 200)
			last_error = GetCodeMessage(result);
		
		return result == 200;
	}
	
	public static void main(String[] args) throws IOException {
		ValidationController.Instance().verbose = 3;
		
		ServerInterface.ResetDB();
		
		DateTime d = new DateTime();
		d.Set("2016 May 06 02:47 GMT","YYYY MMM DD hh:mm zzz");
		
		DateTime r = new DateTime();
		r.Set("2016 May 10 02:47 GMT","YYYY MMM DD hh:mm zzz");

		System.out.println("Searching Departure Flights...");
		//simulate round trip from bos to aus
		//first outbound
		Trips.LinkFlights("BOS", "AUS", d.getDateString(), false);
		
		/*System.out.print(Trips.GetNumberofTrips() + " trips found\nEnter your selection and press enter: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int sel = Integer.parseInt(br.readLine());
		
		//user selects trip
		Trip outbound = Trips.Get(sel);*/
		Trip outbound = Trips.Get(1);
		
		System.out.println("Searching Returning Flights...");		
		//then the returning trip
		Trips.LinkFlights("AUS", "BOS", r.getDateString(), false);
		
		/*System.out.print(Trips.GetNumberofTrips() + " trips found\nEnter your selection and press enter: ");
		br = new BufferedReader(new InputStreamReader(System.in));
		sel = Integer.parseInt(br.readLine());
		
		//user selects trip
		Trip returning = Trips.Get(sel);*/
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
