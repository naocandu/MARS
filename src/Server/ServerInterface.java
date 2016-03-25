/**
 * Package contains classes pertaining to working with the database server
 */
package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import Utility.DateTime;

/*************************************************************************
 * Class provides functions to Interface with the airline and timezone
 * servers to query and update information.
 * <p>
 * The main function in this file supplies several test cases to validate
 * all server functionality
 * 
 * @author Anthony Botelho 
 * @since 2016-03-13
 */
public class ServerInterface {
	
	/**
	 * Amount of time to leave the url connection open when making a request or posting an action
	 */
	private static int timeout = 1000*120; //two minute timeout
	private static String res_system_url = ServerConstants.RESERVATION_URL;
	private static String timezone_url = ServerConstants.TIMEZONE_URL;
	private static String team_name = ServerConstants.TEAM_ID;
	
	public static int DataBaseCalls = 0;
	public static int AirportCalls = 0;
	public static int FlightCalls = 0;
	public static int TimezoneCalls = 0;
	
	/**
	 * Handles a generic HTTP GET to a specified url with arguments defined
	 * by query
	 * 
	 * @param url
	 * @param query
	 * @return result
	 */
	private static String QueryDatabase(String url, String query)
	{
		DataBaseCalls++;
		URL request_string;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
		try {
			/**
			 * Create an HTTP connection to the server for a GET 
			 */
			request_string = new URL(url + query);
			connection = (HttpURLConnection) request_string.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", team_name);
			connection.setConnectTimeout(timeout);
			
			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			
			int responseCode = connection.getResponseCode();
			if ((responseCode >= 200) && (responseCode <= 299)) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "URF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}
	
	/**
	 * Handles a generic POST command to the database with the associated
	 * arguments defined in action
	 * 
	 * @param action list of arguments for the post command
	 * @return
	 */
	private static boolean UpdateDatabase(String action)
	{
		URL url;
		HttpURLConnection connection;
		int responseCode;
		
		try {
			url = new URL(res_system_url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(timeout);
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(action);
			writer.flush();
			writer.close();
			
			responseCode = connection.getResponseCode();
			//System.out.println("\nSending 'POST'");
			//System.out.println(("\nResponse Code : " + responseCode));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			
			//System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return (responseCode > 200 && responseCode <= 299);
	}
	
	/**
	 * Locks the database for a POST action
	 * 
	 * @return success or failure
	 */
	private static boolean Lock()
	{
		return UpdateDatabase(QueryBuilder.lock(team_name));
	}
	
	/**
	 * Unlocks the database after a POST
	 * 
	 * @return success or failure
	 */
	private static boolean Unlock()
	{
		return UpdateDatabase(QueryBuilder.unlock(team_name));
	}
	
	/**
	 * Sets the timeout period for connections to each server
	 * 
	 * @param milliseconds the time in milliseconds to wait before a timeout
	 */
	public static void SetTimeout(int milliseconds)
	{
		timeout = milliseconds;
	}
	
	public static void SetReservationURL(String url)
	{
		res_system_url = url;
	}
	
	public static void SetTimezoneURL(String url)
	{
		timezone_url = url;
	}
	
	public static void SetTeamName(String team)
	{
		team_name = team;
	}
	
	/**
	 * Gets the XML string containing all airports
	 * 
	 * @return the XML string containing all airports
	 */
	public static String QueryAirports()
	{
		AirportCalls++;
		return QueryDatabase(res_system_url, QueryBuilder.GetAirportQuery(team_name));
	}
	
	/**
	 * Gets the XML string containing all airplanes
	 * 
	 * @return the XML string containing all airplanes
	 */
	public static String QueryAirplanes()
	{
		return QueryDatabase(res_system_url, QueryBuilder.GetAirplaneQuery(team_name));
	}
	
	/**
	 * Gets the XML string containing flight information for the given airport
	 * on a given day
	 * 
	 * @param airport_code the 3 character airport code
	 * @param departure_date the date as a string in the format YYYY_MM_DD
	 * @param departure whether or not the request is for departure or arrival flights
	 * @return the XML string contianing flight information
	 */
	public static String QueryFlights(String airport_code,String departure_date,boolean departure)
	{
		FlightCalls++;
		return QueryDatabase(res_system_url, QueryBuilder.GetFlightQuery(team_name, airport_code, departure_date, departure));
	}
	
	/**
	 * Gets the XML string containing timezone information for the given
	 * coordinates
	 * 
	 * @param latitude the latitude of the coordinate
	 * @param longitude the longitude of the coordinate
	 * @return an XML string containing timezone information
	 */
	public static String QueryTimezone(double latitude, double longitude)
	{
		TimezoneCalls++;
		return QueryDatabase(timezone_url, QueryBuilder.GetTimezoneQuery(team_name, latitude, longitude));
	}
	
	/**
	 * Resets the database to its initial default state
	 * 
	 * @return the success or failure of the action
	 */
	public static boolean ResetDB()
	{
		//Lock();
		QueryDatabase(res_system_url, QueryBuilder.GetResetQuery(team_name));
		//Unlock();
		
		return true;
	}
	
	/**
	 * Initiates the process to commit the reserved flights to the airline
	 * database
	 * 
	 * @param xmlFlights the xlm string of the flights and seating to purchase
	 * @return the success or failure of the POST
	 */
	public static boolean ReserveFlights(String xmlFlights)
	{
		if (!Lock())
			return false;
		
		boolean response = UpdateDatabase(QueryBuilder.GetReserveAction(team_name, xmlFlights));
		Unlock();
		
		return response;
	}
	
	/**
	 * Function supplies several test cases to verify the proper functionality
	 * of the class methods
	 * 
	 * @param args arguments to the main function (unused)
	 */
	public static void main(String[] args)
	{
		String result = "";
		String query = "";
		
		int flightNumber = 1560;
		String xmlReservation = "<Flights>"
				+ "<Flight number=\"" + flightNumber + "\" seating=\"Coach\"/>"
				+ "</Flights>";
		System.out.println(xmlReservation);
		
		System.out.println("<<< TEST CASE 1: Get Airport XML from Database >>>\n--");
		query = res_system_url + QueryBuilder.GetAirportQuery(team_name);
		System.out.println("Querying " + query + "...");
		result = QueryAirports();
		System.out.println("Query returned " + (result.length()>0?("XML String: \n"+result):"an error"));
		System.out.println("--\n\n\n");
		
		
		
		
		System.out.println("<<< TEST CASE 2: Get Airplane XML from Database >>>\n--");
		query = res_system_url + QueryBuilder.GetAirplaneQuery(team_name);
		System.out.println("Querying " + query + "...");
		result = QueryAirplanes();
		System.out.println("Query returned " + (result.length()>0?("XML String: \n"+result):"an error"));
		System.out.println("--\n\n\n");
		
		
		
		
		System.out.println("<<< TEST CASE 3: Get Departure Flight XML from Database >>>\n--");
		String airport_code = "BOS";
		DateTime t = new DateTime();
		//t.SetDate(10, 5, 2016);
		t.Set("2016_5_10", "YYYY_M_DD");
		
		System.out.println("Getting departure flights from "+airport_code+" on "+t.getDateString());
		query = res_system_url + QueryBuilder.GetFlightQuery(team_name, airport_code, t.getDateString(), true);
		System.out.println("Querying " + query + "...");
		result = QueryFlights(airport_code,t.getDateString(),true);
		System.out.println("Query returned " + (result.length()>0?("XML String: \n"+result):"an error"));
		System.out.println("--\n\n\n");
		
		
		
		
		System.out.println("<<< TEST CASE 4: Get Arrival Flight XML from Database >>>\n--");
		System.out.println("Getting arrival flights to "+airport_code+" on "+t.getDateString());
		query = res_system_url + QueryBuilder.GetFlightQuery(team_name, airport_code, t.getDateString(), false);
		System.out.println("Querying " + query + "...");
		result = QueryFlights(airport_code,t.getDateString(),false);
		System.out.println("Query returned " + (result.length()>0?("XML String: \n"+result):"an error"));
		System.out.println("--\n\n\n");
		
		
		
		
		System.out.println("<<< TEST CASE 5: Reserve Flight From TEST 4 >>>\n--");
		
		System.out.println("Reserving Coach for Flight " + flightNumber + "...");
		ReserveFlights(xmlReservation);
		
		System.out.println("Getting arrival flights to "+airport_code+" on "+t.getDateString());
		query = res_system_url + QueryBuilder.GetFlightQuery(team_name, airport_code, t.getDateString(), false);
		System.out.println("Querying " + query + "...");
		
		String test4xml = result;
		
		result = QueryFlights(airport_code,t.getDateString(),false);
		System.out.println("A change has"+(result.compareTo(test4xml)!=0?"":" not")+" been detected in the database");
		System.out.println("--\n\n\n");
		
		
		
		
		System.out.println("<<< TEST CASE 6: Resetting Database >>>\n--");
		query = res_system_url + QueryBuilder.GetResetQuery(team_name);
		System.out.println("Reseting DB with " + query + "...");
		ResetDB();
		
		System.out.println("Getting arrival flights to "+airport_code+" on "+t.getDateString());
		query = res_system_url + QueryBuilder.GetFlightQuery(team_name, airport_code, t.getDateString(), false);
		System.out.println("Querying " + query + "...");
		
		result = QueryFlights(airport_code,t.getDateString(),false);
		System.out.println("The database has"+(result.compareTo(test4xml)==0?"":" not")+" been reset");
		System.out.println("--\n\n\n");
		
		
		
		
		System.out.println("<<< TEST CASE 7: Get Timezone XML from Database >>>\n--");
		double lat = 42.26;
		double lng = -71.8;
		query = timezone_url + QueryBuilder.GetTimezoneQuery(team_name,lat,lng);
		System.out.println("Querying " + query + "...");
		result = QueryTimezone(lat,lng);
		
		System.out.println("Query returned " + (result.indexOf("timezone")!=-1?("XML String: \n"+result):"an error"));
		System.out.println("--\n\n\n");
		
	}
}
