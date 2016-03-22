package TripPlanner;

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
import Controller.Trip;
import Controller.Trips;
import Controller.ValidationController;

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
	public String FindAirports() {
		/*
		 * announce a new Airports and call the function Populate()
		 */
		Airports airports = new Airports();
		return airports.Populate();
	}
	
	/*
	 * return and display the flights the user wants
	 */
	
	private boolean round;
	private boolean FC_seating;
	
	private String departure;
	private String arrival;
	private String departureDate;
	
	public String SearchFlights() {
		/* 
		 * the information round, departure, arrival, departureDate and seating should be
		 * achieved from the interface FlightsReservation
		 */
		round = (FlightsReservation.getTripType() == "round trip");
		FC_seating = (FlightsReservation.getSeatClass() == "first class");
		departure = FlightsReservation.getDeparture();
		arrival = FlightsReservation.getArrival();
		departureDate = FlightsReservation.getDepartureDate();
		
		/*
		 * call the static function LinkFlights in the class Trips
		 */
		
		Trips.LinkFlights(departure, arrival, FC_seating);
		
	}
	
	/*
	 * provides if the user reserves the trip or not
	 */
	public void ReserveTrip() {
		
		/*
		 * Call the function ConfirmTrip in the class ValidationController
		 */
		
		
		ValidationController VC = new ValidationController();
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
		
		if (opt == 1) {
			
		    Collections.sort(tripList,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
				    return (int) (t1.GetPrice() - t2.GetPrice());
			    }
		    });
		}
		
        if (opt == 2) {
			
		    Collections.sort(tripList,new Comparator<Trip>(){
			    @Override
			    public int compare(Trip t1, Trip t2) {
				    return (int) (t1.GetDuration() - t2.GetDuration());
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
	

}