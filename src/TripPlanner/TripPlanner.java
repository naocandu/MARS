package TripPlanner;

/**
 * class is used to connect UI and other parts of system.
 * class provides several functions, such as sort and filter.
 * 
 * @author Bian Du, Hao Liu
 * @since 2016-3-22
 */
import AirFlight.Airports;
import windowbuilder1.FlightsReservation;
import windowbuilder1.secondwindow;
import windowbuilder1.thirdwindow;

import Controller.Trip;
import Controller.Trips;
import Controller.ValidationController;

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

import javax.swing.JOptionPane;

public class TripPlanner
{
	/**
	 * define user information
	 */
	private static boolean round;
	private static boolean FC_seating;
	public static String departure;
	public static String arrival;
	public static String departureDate;
	public static String returnDate;
	/**
	 * create several list in order to realize sort and filter function.
	 */
	public static List<Trip> trip = null;
	public static List<Trip> trip1 = new ArrayList<Trip>();
	public static List<Trip> trip2 = new ArrayList<Trip>();
	public static List<Trip> trip3 = new ArrayList<Trip>();
	public static List<Trip> tripmix1 = new ArrayList<Trip>();
	public static List<Trip> tripmix2 = new ArrayList<Trip>();
	public static List<Trip> triphop1 = new ArrayList<Trip>();
	public static List<Trip> triphop2 = new ArrayList<Trip>();
	public static List<Trip> tripfilter1 = null;
	public static List<Trip> tripfilter2 = null;
	public static List<Trip> tripsort1 = null;
	public static List<Trip> tripsort2 = null;
	
	/**
	 * get the depart airport code
	 * 
	 * @param a
	 * @return a string of airport code.
	 */
	public static String getAirportCode(String a)
	{
		String b = a;
		String c = b.substring(0, 3);
		return c;
	}
	
	/**
	 * search round trip.
	 * 
	 * @param firstclass
	 */
	public static void SearchFlights2(boolean firstclass)
	{
		departure = getAirportCode(FlightsReservation.getDeparture()); // use
																		// airport
																		// code
																		// to
																		// search
		arrival = getAirportCode(FlightsReservation.getArrival());
		departureDate = FlightsReservation.getDepartureDate();
		returnDate = FlightsReservation.getReturnDate();
		
		try
		{
			trip = Trips.LinkFlights(departure, arrival, departureDate, firstclass);
		} catch (Exception e2)
		{
			e2.printStackTrace();
		}
		
		trip1.clear();
		for (int i = 0; i < trip.size(); i++)
			trip1.add(trip.get(i));
		for (int i = 0; i < trip.size(); i++)
			trip3.add(trip.get(i));
		trip3 = Filter(10, trip3);
		for (int i = 0; i < trip3.size(); i++)
			tripmix1.add(trip3.get(i));
		trip3.clear();
		
		if (trip.size() == 0)
		{
			JOptionPane.showMessageDialog(null,
					"No Flights Found\n" + ValidationController.Instance().GetLastErrorMessage());
			ValidationController.Instance().RefreshAll();
			return;
		}
		
		try
		{
			trip = Trips.LinkFlights(arrival, departure, returnDate, firstclass);
		} catch (Exception e3)
		{
			e3.printStackTrace();
		}
		
		trip2.clear();
		for (int i = 0; i < trip.size(); i++)
			trip2.add(trip.get(i));
		for (int i = 0; i < trip.size(); i++)
			trip3.add(trip.get(i));
		trip3 = Filter(10, trip3);
		for (int i = 0; i < trip3.size(); i++)
			tripmix2.add(trip3.get(i));
		trip3.clear();
		
		if (trip.size() == 0)
		{
			JOptionPane.showMessageDialog(null,
					"No Flights Found\n" + ValidationController.Instance().GetLastErrorMessage());
			ValidationController.Instance().RefreshAll();
			return;
		}
		
		try
		{
			thirdwindow window = new thirdwindow();
			window.frmRoundtrip.setVisible(true);
		} catch (Exception e4)
		{
			e4.printStackTrace();
		}
		
	}
	
	/**
	 * search one-way trip.
	 * 
	 * @param firstclass
	 */
	public static void SearchFlights(boolean firstclass)
	{
		
		departure = getAirportCode(FlightsReservation.getDeparture()); // use
																		// airport
																		// code
																		// to
																		// search
		arrival = getAirportCode(FlightsReservation.getArrival());
		departureDate = FlightsReservation.getDepartureDate();
		
		try
		{
			trip = Trips.LinkFlights(departure, arrival, departureDate, firstclass);
			trip1.clear();
			for (int i = 0; i < trip.size(); i++)
				trip1.add(trip.get(i));
			for (int i = 0; i < trip.size(); i++)
				trip3.add(trip.get(i));
			trip3 = Filter(10, trip3);
			for (int i = 0; i < trip3.size(); i++)
				tripmix1.add(trip3.get(i));
			trip3.clear();
			
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		if (trip.size() == 0)
		{
			JOptionPane.showMessageDialog(null,
					"No Flights Found\n" + ValidationController.Instance().GetLastErrorMessage());
			ValidationController.Instance().RefreshAll();
			return;
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
	
	/**
	 * sort function, with 4 options, 1:price 2:duration
	 * 3:departureTime(earliest) 4:arrivalTime(earliest)
	 * 
	 * @param opt
	 * @param tripListOld
	 * @return a sorted list
	 */
	public static List<Trip> SortBy(int opt, List<Trip> tripListOld)
	{
		List<Trip> tripList = new ArrayList<Trip>(); // state an arraylist that
														// hold trip results
		
		if (opt == 1)
		{
			
			Collections.sort(tripListOld, new Comparator<Trip>()
			{
				@Override
				public int compare(Trip t1, Trip t2)
				{
					float a = t1.GetPrice() - t2.GetPrice();
					return (a == 0) ? 0 : (a > 0) ? 1 : -1;
				}
			});
			tripList = tripListOld;
		}
		
		// order by the duration from short to long
		if (opt == 2)
		{
			
			Collections.sort(tripListOld, new Comparator<Trip>()
			{
				@Override
				public int compare(Trip t1, Trip t2)
				{
					float a = t1.GetDuration() - t2.GetDuration();
					return (a == 0) ? 0 : (a > 0) ? 1 : -1;
				}
			});
			tripList = tripListOld;
		}
		
		// order by the departure time from early to late
		if (opt == 3)
		{
			
			Collections.sort(tripListOld, new Comparator<Trip>()
			{
				@Override
				public int compare(Trip t1, Trip t2)
				{
					float a = t1.GetDepartureTimeinMinutes() - t2.GetDepartureTimeinMinutes();
					return (a == 0) ? 0 : (a > 0) ? 1 : -1;
					
				}
			});
			tripList = tripListOld;
		}
		
		// order by the arrival time from early to late
		if (opt == 4)
		{
			
			Collections.sort(tripListOld, new Comparator<Trip>()
			{
				@Override
				public int compare(Trip t1, Trip t2)
				{
					float a = t1.GetArrivalTimeinMinutes() - t2.GetArrivalTimeinMinutes();
					return (a == 0) ? 0 : (a > 0) ? 1 : -1;
					
				}
			});
			tripList = tripListOld;
		}
		
		return tripList;
		
	}
	
	/**
	 * filter function, with 4 options, 7:Non stop 8:one Stop 9:two Stops 10:
	 * non mix-seating
	 * 
	 * @param opt
	 * @param tripListOld
	 * @return a filtered list
	 */
	public static List<Trip> Filter(int opt, List<Trip> tripListOld)
	{
		
		List<Trip> tripList = new ArrayList<Trip>(); // state an arraylist that
														// hold trip results
		
		int n = tripListOld.size();
		
		if (opt == 7)
		{
			for (int i = n - 1; i >= 0; i--)
			{
				if (tripListOld.get(i).GetNumberofHops() != 1)
				{
					tripListOld.remove(i);
				}
			}
			tripList = tripListOld;
		}
		if (opt == 8)
		{
			for (int i = n - 1; i >= 0; i--)
			{
				if (tripListOld.get(i).GetNumberofHops() != 2)
				{
					tripListOld.remove(i);
					
				}
			}
			tripList = tripListOld;
		}
		if (opt == 9)
		{
			for (int i = n - 1; i >= 0; i--)
			{
				if (tripListOld.get(i).GetNumberofHops() != 3)
				{
					tripListOld.remove(i);
					
				}
			}
			tripList = tripListOld;
		}
		if (opt == 10)
		{
			for (int i = n - 1; i >= 0; i--)
			{
				int m = tripListOld.get(i).GetNumberofHops();
				if (m == 1)
				{
					continue;
				}
				if (m == 2)
				{
					if (tripListOld.get(i).ContainsMixedSeating() == true)
					{
						tripListOld.remove(i);
					}
				}
				if (m == 3)
				{
					if (tripListOld.get(i).ContainsMixedSeating() == true)
					{
						tripListOld.remove(i);
					}
				}
			}
			
			tripList = tripListOld;
		}
		
		return tripList;
		
	}
	
	public static void main(String[] args)
	{
		
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
