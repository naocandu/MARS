package Server;

public class QueryBuilder {
	public static String GetAirportQuery(String team)
	{
		return "?team=" + team + "&action=list&list_type=airports";
	}
	
	public static String GetAirplaneQuery(String team)
	{
		return "?team=" + team + "&action=list&list_type=airplanes";
	}
	
	public static String GetFlightQuery(String team, String airport_code, String departure_date, boolean departure)
	{
		return "?team="+team + "&action=list&list_type="+(departure?"departing":"arriving")+
				"&airport="+airport_code+"&day="+departure_date;
	}
	
	public static String GetResetQuery(String team)
	{
		return "?team="+team+"&action=resetDB";
	}
	
	public static String GetTimezoneQuery(String team, double latitude, double longitude)
	{
		return "?lat="+latitude+"&lng="+longitude+"&username="+team;
	}
	
	public static String GetReserveAction (String team, String xmlFlights) {
		return "team=" + team + "&action=buyTickets" + "&flightData=" + xmlFlights;
	}
	
	public static String lock (String team) {
		return "team=" + team + "&action=lockDB";
	}
	
	public static String unlock (String team) {
		return "team=" + team + "&action=unlockDB";
	}
}
