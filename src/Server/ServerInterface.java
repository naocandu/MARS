package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import Utility.Time;


public class ServerInterface {
	
	private static int timeout = 1000*120; //two minute timeout
	
	private static String QueryDatabase(String url, String query)
	{
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
			connection.setRequestProperty("User-Agent", ServerConstants.TEAM_ID);
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
	
	private static boolean UpdateDatabase(String action)
	{
		URL url;
		HttpURLConnection connection;
		int responseCode;
		
		try {
			url = new URL(ServerConstants.URL);
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
	
	private static boolean Lock()
	{
		return UpdateDatabase(QueryBuilder.lock(ServerConstants.TEAM_ID));
	}
	
	private static boolean Unlock()
	{
		return UpdateDatabase(QueryBuilder.unlock(ServerConstants.TEAM_ID));
	}
	
	public static void SetTimeout(int milliseconds)
	{
		timeout = milliseconds;
	}
	
	public static String QueryAirports()
	{
		return QueryDatabase(ServerConstants.URL, QueryBuilder.GetAirportQuery(ServerConstants.TEAM_ID));
	}
	
	public static String QueryAirplanes()
	{
		return QueryDatabase(ServerConstants.URL, QueryBuilder.GetAirplaneQuery(ServerConstants.TEAM_ID));
	}
	
	public static String QueryFlights(String airport_code,String departure_date,boolean departure)
	{
		return QueryDatabase(ServerConstants.URL, QueryBuilder.GetFlightQuery(ServerConstants.TEAM_ID, airport_code, departure_date, departure));
	}
	
	public static String QueryTimezone(double latitude, double longitude)
	{
		return QueryDatabase(ServerConstants.TIMEZONE_URL, QueryBuilder.GetTimezoneQuery(ServerConstants.TEAM_ID, latitude, longitude));
	}
	
	public static boolean ResetDB()
	{
		//Lock();
		QueryDatabase(ServerConstants.URL, QueryBuilder.GetResetQuery(ServerConstants.TEAM_ID));
		//Unlock();
		
		return true;
	}
	
	public static boolean ReserveFlights(String xmlFlights)
	{
		Lock();
		boolean response = UpdateDatabase(QueryBuilder.GetReserveAction(ServerConstants.TEAM_ID, xmlFlights));
		Unlock();
		
		return response;
	}
	
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
		query = ServerConstants.URL + QueryBuilder.GetAirportQuery(ServerConstants.TEAM_ID);
		System.out.println("Querying " + query + "...");
		result = QueryAirports();
		System.out.println("Query returned " + (result.length()>0?("XML String: \n"+result):"an error"));
		System.out.println("--\n");
		
		System.out.println("<<< TEST CASE 2: Get Airplane XML from Database >>>\n--");
		query = ServerConstants.URL + QueryBuilder.GetAirplaneQuery(ServerConstants.TEAM_ID);
		System.out.println("Querying " + query + "...");
		result = QueryAirplanes();
		System.out.println("Query returned " + (result.length()>0?("XML String: \n"+result):"an error"));
		System.out.println("--\n");
		
		System.out.println("<<< TEST CASE 3: Get Departure Flight XML from Database >>>\n--");
		String airport_code = "BOS";
		Time t = new Time();
		t.SetDate(10, 5, 2016);
		System.out.println("Getting departure flights from "+airport_code+" on "+t.getDateString());
		query = ServerConstants.URL + QueryBuilder.GetFlightQuery(ServerConstants.TEAM_ID, airport_code, t.getDateString(), true);
		System.out.println("Querying " + query + "...");
		result = QueryFlights(airport_code,t.getDateString(),true);
		System.out.println("Query returned " + (result.length()>0?("XML String: \n"+result):"an error"));
		System.out.println("--\n");
		
		System.out.println("<<< TEST CASE 4: Get Arrival Flight XML from Database >>>\n--");
		System.out.println("Getting arrival flights to "+airport_code+" on "+t.getDateString());
		query = ServerConstants.URL + QueryBuilder.GetFlightQuery(ServerConstants.TEAM_ID, airport_code, t.getDateString(), false);
		System.out.println("Querying " + query + "...");
		result = QueryFlights(airport_code,t.getDateString(),false);
		System.out.println("Query returned " + (result.length()>0?("XML String: \n"+result):"an error"));
		System.out.println("--\n");
		
		System.out.println("<<< TEST CASE 5: Reserve Flight From TEST 4 >>>\n--");
		
		System.out.println("Reserving Coach for Flight " + flightNumber + "...");
		ReserveFlights(xmlReservation);
		
		System.out.println("Getting arrival flights to "+airport_code+" on "+t.getDateString());
		query = ServerConstants.URL + QueryBuilder.GetFlightQuery(ServerConstants.TEAM_ID, airport_code, t.getDateString(), false);
		System.out.println("Querying " + query + "...");
		
		String test4xml = result;
		
		result = QueryFlights(airport_code,t.getDateString(),false);
		System.out.println("A change has"+(result.compareTo(test4xml)!=0?"":" not")+" been detected in the database");
		System.out.println("--\n");
		
		System.out.println("<<< TEST CASE 6: Resetting Database >>>\n--");
		query = ServerConstants.URL + QueryBuilder.GetResetQuery(ServerConstants.TEAM_ID);
		System.out.println("Reseting DB with " + query + "...");
		ResetDB();
		
		System.out.println("Getting arrival flights to "+airport_code+" on "+t.getDateString());
		query = ServerConstants.URL + QueryBuilder.GetFlightQuery(ServerConstants.TEAM_ID, airport_code, t.getDateString(), false);
		System.out.println("Querying " + query + "...");
		
		result = QueryFlights(airport_code,t.getDateString(),false);
		System.out.println("The database has"+(result.compareTo(test4xml)==0?"":" not")+" been reset");
		System.out.println("--\n");
		
		System.out.println("<<< TEST CASE 7: Get Timezone XML from Database >>>\n--");
		double lat = 42.26;
		double lng = -71.8;
		query = ServerConstants.TIMEZONE_URL + QueryBuilder.GetTimezoneQuery(ServerConstants.TEAM_ID,lat,lng);
		System.out.println("Querying " + query + "...");
		result = QueryTimezone(lat,lng);
		
		System.out.println("Query returned " + (result.indexOf("timezone")!=-1?("XML String: \n"+result):"an error"));
		System.out.println("--\n");
		
	}
}
