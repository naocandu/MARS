package TripPlanner;
import AirFlight.Airports;

/*
 * Class provides functions for obtaining the specific airports and flights the user asks for 
 * and displaying them on the interface.
 * Also provides functions of sorting and filtering information
 * 
 * @author Bian Du
 * @since 2016-3-18
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dom4j.DocumentException;

import AirFlight.Airports;
import Controller.Trip;
import Controller.Trips;
import Controller.ValidationController;
import windowbuilder1.FlightsReservation;
import Utility.*;

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
	
	private static String departure;
	private static String arrival;
	private static String departureDate;
	
	//get the depart airport code
	public static String getAirportCode(String a)
	{
		String b = a;
		String c = b.substring(0,3);
		return c;
	}
	
	public static void SearchFlights() {
		/* 
		 * the information round, departure, arrival, departureDate and seating should be
		 * achieved from the interface FlightsReservation
		 */
		FlightsReservation fr = new FlightsReservation();
		round = (fr.getTripType() == "round trip");
		FC_seating = (fr.getSeatClass() == "first class");
		departure = fr.getDeparture();
		arrival = fr.getArrival();
		departureDate = fr.getDepartureDate();
		
		/*
		 * call the static function LinkFlights in the class Trips
		 */
		DateTime dt = new DateTime();
		//dt.Set(departureDate, format);
		//Trips.LinkFlights(departure, arrival, dt, FC_seating);
		
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
	public static ArrayList<Trip> SortBy(int opt) {
		/*
		 * 1:price  2:duration  3:departureTime(earliest)/ 4:departureTime(latest)
		 * 5:arrivalTime(earliest)/ 6:arrivalTime(latest)
		 */
		
		ArrayList<Trip> tripList = new ArrayList<> (); // state an arraylist that hold trip results
		
		int n = Trips.GetNumberofTrips(); 
		
		for (int i=0;i<n;i++) {
			tripList.add(Trips.Get(i));
		}
		
		// order by the price from low to high
		if (opt == 1) {
			
		    Collections.sort(tripList,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
				    return (int) (t1.GetPrice() - t2.GetPrice());
			    }
		    });
		}
		
		// order by the duration from short to long
		if (opt == 2) {
			
		    Collections.sort(tripList,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
				    return (int) (t1.GetDuration() - t2.GetDuration());
			    }
		    });
		}
		
		// order by the departure time from early to late
        if (opt == 3) {
			
		    Collections.sort(tripList,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
				    return (int) (t1.GetDepartureTimeinMinutes() - t2.GetDepartureTimeinMinutes());
			    }
		    });
		}
        
        // order by the departure time from late to early
        if (opt == 4) {
	
            Collections.sort(tripList,new Comparator<Trip>(){
	            @Override
	            public int compare(Trip t2, Trip t1) {
		            return (int) (t1.GetDepartureTimeinMinutes() - t2.GetDepartureTimeinMinutes());
	            }
            });
        }
		
        // order by the arrival time from early to late
        if (opt == 5) {
			
		    Collections.sort(tripList,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
				    return (int) (t1.GetArrivalTimeinMinutes() - t2.GetArrivalTimeinMinutes());
			    }
		    });
		}
        
        // order by the arrival time from late to early
        if (opt == 6) {
			
		    Collections.sort(tripList,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t2, Trip t1) {
				    return (int) (t1.GetArrivalTimeinMinutes() - t2.GetArrivalTimeinMinutes());
			    }
		    });
		}
		return tripList;
		
	}
	
	public static void Filter(int opt) {
		/*
		 * 7:Nonstop  8:1 Stop  9:2 Stops
		 */
        ArrayList<Trip> tripList = new ArrayList<> (); // state an arraylist that hold trip results
		
		int n = Trips.GetNumberofTrips(); 
		
		for (int i=0;i<n;i++) {
			tripList.add(Trips.Get(i));
		}
		
		if (opt == 7) {
			for (int i=0;i<n;i++) {
				if (tripList.get(i).GetNumberofHops() != 0) {
					tripList.remove(i);
				}
			}
		}
		if (opt == 8) {
			for (int i=0;i<n;i++) {
				if (tripList.get(i).GetNumberofHops() != 1) {
					tripList.remove(i);
				}
			}
		}
		if (opt == 9) {
			for (int i=0;i<n;i++) {
				if (tripList.get(i).GetNumberofHops() != 2) {
					tripList.remove(i);
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		
		
	}

}
