package Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import Server.ServerInterface;
import Server.ServerConstants;
import XMLparser.*;

public class ValidationController {
	
	private static ValidationController instance = null;
	private int min_layover = ValidationConstants.MIN_LAYOVER_MINUTES;
	private int max_layover = ValidationConstants.MAX_LAYOVER_MINUTES;
	private int max_hops = ValidationConstants.MAX_HOPS;
	
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
	
	public static void main(String[] args) {
		ValidationController.Instance();
	}

}
