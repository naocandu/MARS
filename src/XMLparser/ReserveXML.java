package XMLparser;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

/**
 * Reserve XML
 * @author Zheng Nie
 *
 */

public class ReserveXML {
	
	/**
	 * creating xml given a trip
	 * @param trip the trip containing seat class and seat number
	 * @return xml string
	 */
	
	public static String MakeXML(List<String> trip){
		String components="";
		String seating = null;
		String flightNumber;
		for (int i=0; i<trip.size(); i++){
			if (trip.get(i).substring(0,1).equals("F")){
				seating="FirstClass";
			} else if (trip.get(i).substring(0,1).equals("E")){
				seating = "Coach";
			}  else {
				return "Seating class must begin with \"F\" or \"E\"";
			}
			flightNumber=trip.get(i).substring(1);
			components+="<Flight number=\"" + flightNumber + "\" seating=\"" + seating + "\"/>";
		}
		return "<Flights>"+components+"</Flights>";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> a = new ArrayList();
		a.add("F123");
		a.add("E23");
		a.add("E458");
		System.out.println(MakeXML(a));

	}

}
