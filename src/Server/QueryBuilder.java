package Server;

/*************************************************************************
 * Class provides functions for building http query strings
 * for GET and POST actions
 * 
 * @author Anthony Botelho 
 * @since 2016-03-13
 */
public class QueryBuilder {
	
	/**
	 * Constructs the parameter list for querying the airports
	 * 
	 * @param team the name of the project team
	 * @return A string containing the parameters of an HTTP get request
	 */
	public static String GetAirportQuery(String team)
	{
		return "?team=" + team + "&action=list&list_type=airports";
	}
	
	/**
	 * Constructs the parameter list for querying the airplanes
	 * 
	 * @param team the name of the project team
	 * @return A string containing the parameters of an HTTP get request
	 */
	public static String GetAirplaneQuery(String team)
	{
		return "?team=" + team + "&action=list&list_type=airplanes";
	}
	
	/**
	 * Constructs the parameter list for querying the flights
	 * 
	 * @param team the name of the project team
	 * @param airport_code the 3 character aiport code
	 * @param departure_date the date of the flights as a string
	 * @param departure whether the request is for departure or arrival flights
	 * @return A string containing the parameters of an HTTP get request
	 */
	public static String GetFlightQuery(String team, String airport_code, String departure_date, boolean departure)
	{
		return "?team="+team + "&action=list&list_type="+(departure?"departing":"arriving")+
				"&airport="+airport_code+"&day="+departure_date;
	}
	
	/**
	 * Constructs the parameter list for resetting the database
	 * 
	 * @param team the name of the project team
	 * @return A string containing the parameters of an HTTP get request (for reset)
	 */
	public static String GetResetQuery(String team)
	{
		return "?team="+team+"&action=resetDB";
	}
	
	/**
	 * Constructs the parameter list for querying the timezone associated with a coordinate
	 * 
	 * @param team the username (project team name) to access the server
	 * @param latitude the latitude of the coordinate
	 * @param longitude the longitude of the coordinate
	 * @return A string containing the parameters of an HTTP get request
	 */
	public static String GetTimezoneQuery(String team, double latitude, double longitude)
	{
		return "?lat="+latitude+"&lng="+longitude+"&username="+team;
	}
	
	/**
	 * Constructs the parameter list for purchasing a ticket in the database
	 * 
	 * @param team the name of the project team
	 * @param xmlFlights the xml containing flight and seating information for purchase
	 * @return A string containing the parameters of an HTTP post action
	 */
	public static String GetReserveAction (String team, String xmlFlights) {
		return "team=" + team + "&action=buyTickets" + "&flightData=" + xmlFlights;
	}
	
	/**
	 * Constructs the parameter list for locking the database
	 * 
	 * @param team the name of the project team
	 * @return A string containing the parameters of an HTTP post action
	 */
	public static String lock (String team) {
		return "team=" + team + "&action=lockDB";
	}
	
	/**
	 * Constructs the parameter list for unlocking the database
	 * 
	 * @param team the name of the project team
	 * @return A string containing the parameters of an HTTP post action
	 */
	public static String unlock (String team) {
		return "team=" + team + "&action=unlockDB";
	}
}
