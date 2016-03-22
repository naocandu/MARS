package AirFlight;

import java.util.List;

import XMLparser.parseAirports;

public class Airport {
	public static List getAirport(String code){
		return parseAirports.readXML().get(code);
	}
	
	public static void main(String[] args){
		System.out.print("Test:\n");
		System.out.println(getAirport("LAX"));
	}

}
