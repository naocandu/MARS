package TripPlanner;
import AirFlight.Airports;
import windowbuilder1.FlightsReservation;
import windowbuilder1.secondwindow;
import windowbuilder1.thirdwindow;

import Controller.Trip;
import Controller.Trips;

import org.dom4j.DocumentException;
/*
 * Class provides functions for obtaining the specific airports and flights the user asks for 
 * and displaying them on the interface.
 * Also provides functions of sorting and filtering information
 * 
 * @author Bian Du
 * @since 2016-3-18
 */


import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TripPlanner {
	
	/*
	 * The maximum and minimum layover time of each flight
	 */
	private static int layoverMin = 1; // one hour minimum layover
	private static int layoverMax = 3; // one hour maximum layover

	/*
	 * display potential trips beyond the date, airport, and seating class
	 * can also think of 'seatingOpt' as boolean values
	 */
	private static int seatingOpt;
	
	/*
	 * search for the airports from Populate() in the class Airports
	 */
	public static List FindAirports() throws DocumentException {
		/*
		 * announce a new Airports and call the function Populate()
		 */
		return Airports.getAirportList();
	}
	
	/*
	 * return and display the flights the user wants
	 */
	
	private static boolean round;
	private static boolean FC_seating;
	
	public static String departure;
	public static String arrival;
	public static String departureDate;
	public static String returnDate;
	public static List<Trip> trip = null;
	public static List<Trip> trip1 = new ArrayList<Trip>();
	public static List<Trip> trip2 = new ArrayList<Trip>();
	public static List<Trip> triphop1 = new ArrayList<Trip>();
	public static List<Trip> triphop2 = new ArrayList<Trip>();
	public static List<Trip> tripfilter1 = null;
	public static List<Trip> tripfilter2 = null;
	public static List<Trip> tripsort1 = null;
	public static List<Trip> tripsort2 = null;
	
	
	//get the depart airport code
	public static String getAirportCode(String a)
	{
		String b = a;
		String c = b.substring(0,3);
		return c;
	}
	//get results sequence
	public static String[] results(List<Trip> a)
	{
		List<String> b = new ArrayList<String>();
		int length = a.size();
		for(int i = 0; i<length; i++)
		{
			b.add(a.get(i).GetAiportSequence()+"\n"+ 
					"\n" + a.get(i).GetFlightSequence() + 
						"\n" + a.get(i).toString());
		}
		String[] c = b.toArray(new String[b.size()]);
		return c;
		
	}
	public static void SearchFlights2(boolean firstclass)
	{
		departure = getAirportCode(FlightsReservation.getDeparture()); //use airport code to search
		arrival = getAirportCode(FlightsReservation.getArrival());
		departureDate = FlightsReservation.getDepartureDate();
		returnDate =  FlightsReservation.getReturnDate();
		if(firstclass==true)
		{
		try
		{
		trip = Trips.LinkFlights(departure, arrival, departureDate, true);
		} catch (Exception e2)
		{
			e2.printStackTrace();
		}
		for(int i=0; i<trip.size();i++) trip1.add(trip.get(i));
		try
		{
		trip = Trips.LinkFlights(arrival, departure, returnDate, true);
		} catch (Exception e3)
		{
			e3.printStackTrace();
		}
		for(int i=0; i<trip.size();i++) trip2.add(trip.get(i));
		try
		{
		thirdwindow window = new thirdwindow();
		window.frmRoundtrip.setVisible(true);
		} catch (Exception e4)
		{
			e4.printStackTrace();
		}
		}
		else
		{
			try
			{
			trip = Trips.LinkFlights(departure, arrival, departureDate, false);
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
			for(int i=0; i<trip.size();i++) trip1.add(trip.get(i));
			try
			{
			trip = Trips.LinkFlights(arrival, departure, returnDate, false);
			} catch (Exception e3)
			{
				e3.printStackTrace();
			}
			for(int i=0; i<trip.size();i++) trip2.add(trip.get(i));
			try
			{
			thirdwindow window = new thirdwindow();
			window.frmRoundtrip.setVisible(true);
			} catch (Exception e4)
			{
				e4.printStackTrace();
			}
		}
	
	}
	
	public static void SearchFlights(boolean firstclass) {
		/* 
		 * the information round, departure, arrival, departureDate and seating should be
		 * achieved from the interface FlightsReservation
		 */
		departure = getAirportCode(FlightsReservation.getDeparture()); //use airport code to search
		arrival = getAirportCode(FlightsReservation.getArrival());
		departureDate = FlightsReservation.getDepartureDate();
		if(firstclass==true)
		{
		/*
		 * call the static function LinkFlights in the class Trips
		 */

		try
		{
			trip = Trips.LinkFlights(departure, arrival, departureDate, true);
			for(int i=0; i<trip.size();i++) trip1.add(trip.get(i));
			
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
			try
			{
				secondwindow window = new secondwindow();
				window.frmFlightsResults.setVisible(true);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
	}
		else
		{
			try
			{
				trip = Trips.LinkFlights(departure, arrival, departureDate, false);
				for(int i=0; i<trip.size();i++) trip1.add(trip.get(i));
				
				
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
				try
				{
					secondwindow window = new secondwindow();
					window.frmFlightsResults.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
		}
	}

	/*
	 * provides if the user reserves the trip or not
	 */
	public void ReserveTrip() {
		
		/*
		 * Call the function ConfirmTrip in the class ValidationController
		 */
		
		
		//ValidationController VC = new ValidationController();
		// To be coded

		 	
	}
	
	public void Cancel() {
		/*
		 * if click the cancel button, exit the system
		 */
		
	}
	

	
	/*
	 * SortBy and Filter should be connected with interface using actionEvent
	 */
	public static List<Trip> SortBy(int opt, List<Trip> tripListOld) {
		/*
		 * 1:price  2:duration  3:departureTime(earliest)/ 4:departureTime(latest)
		 * 5:arrivalTime(earliest)/ 6:arrivalTime(latest)
		 */
		
		List<Trip> tripList = new ArrayList<Trip> (); // state an arraylist that hold trip results
		
		/*int n = Trips.GetNumberofTrips(); 
		
		for (int i=0;i<n;i++) {
			tripListOld.add(Trips.Get(i));
		}
		*/
		
		// order by the price from low to high
if (opt == 1) {
			
		    Collections.sort(tripListOld,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
			    	float a = t1.GetPrice() - t2.GetPrice();
				    return (a == 0)?0:(a > 0)?1:-1;
			    }
		    });
		    tripList = tripListOld;
		}
		
		// order by the duration from short to long
		if (opt == 2) {
			
		    Collections.sort(tripListOld,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
			    	float a = t1.GetDuration() - t2.GetDuration();
			    	return (a == 0)?0:(a > 0)?1:-1;		   
			    }
		    });
		    tripList = tripListOld;
		}
		
		// order by the departure time from early to late
        if (opt == 3) {
			
		    Collections.sort(tripListOld,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
			    	float a = t1.GetDepartureTimeinMinutes() - t2.GetDepartureTimeinMinutes();
			    	return (a == 0)?0:(a > 0)?1:-1;
				  
			    }
		    });
		    tripList = tripListOld;
		}
		
        // order by the arrival time from early to late
        if (opt == 4) {
			
		    Collections.sort(tripListOld,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
			    	float a = t1.GetArrivalTimeinMinutes() - t2.GetArrivalTimeinMinutes();
			    	return (a == 0)?0:(a > 0)?1:-1;
				    
			    }
		    });
		    tripList = tripListOld;
		}      
      
        
		return tripList;
		
	}
	
	public static List<Trip> Filter(int opt, List<Trip> tripListOld) {
		/*
		 * 7:Nonstop  8:one Stop  9:two Stops
		 * 
		 */
        List<Trip> tripList = new ArrayList<Trip> (); // state an arraylist that hold trip results
		
//		int n = Trips.GetNumberofTrips(); 
//		
//		for (int i=0;i<n;i++) {
//			tripList.add(Trips.Get(i));
//		}
		
        int n = tripListOld.size();
        
		if (opt == 7) {
			for (int i=n-1;i>=0;i--) {
				if (tripListOld.get(i).GetNumberofHops() != 1) {
					tripListOld.remove(i);
				}
			}
			tripList = tripListOld;
		}
		if (opt == 8) {
			for (int i=n-1;i>=0;i--) {
				if (tripListOld.get(i).GetNumberofHops() != 2) {
					tripListOld.remove(i);

				}
			}
			tripList = tripListOld;
		}
		if (opt == 9) {
			for (int i=n-1;i>=0;i--) {
				if (tripListOld.get(i).GetNumberofHops() != 3) {
					tripListOld.remove(i);

				}
			}
			tripList = tripListOld;
		}
		
		return tripList;
		
	}
	
	public static void main(String[] args) {
		//SearchFlights2(false);
		//SearchFlights(true);
		try
		{
			FlightsReservation window = new FlightsReservation();
			window.frmFlightsReservation.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
