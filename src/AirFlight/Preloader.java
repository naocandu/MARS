package AirFlight;

import Utility.*;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

public class Preloader implements Runnable {
	private String AirportCode;
	private DateTime DepartureDate;
	private Thread t = null;
	
	public static List<Thread> pool = new ArrayList<Thread>();
	
	public static void WaitforAll()
	{
		for (int i = 0;i < pool.size();i++)
		{
			try {
				pool.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		pool.clear();
	}
	
	public Preloader(String AirportCode, DateTime DepartureDate)
	{
		this.AirportCode = AirportCode;
		this.DepartureDate = DepartureDate;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
				Airports.GetTimezoneOffset(AirportCode);

				Flights.GetFlightsFromAirport(AirportCode, DepartureDate);

		} catch (DocumentException e) {

			//do nothing
		}
	}
	
	public void start()
	{
		if (t == null)
		{
			synchronized (Preloader.class)
			{
				if (t == null)
				{
					t = new Thread(this,AirportCode);
					pool.add(t);
					pool.get(pool.size()-1).start();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
