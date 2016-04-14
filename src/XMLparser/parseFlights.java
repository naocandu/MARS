package XMLparser;

import Server.ServerInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class parseFlights{
	static Document document;

	
	public static List getFlights(String xml) throws DocumentException{
		List flightInfo = new ArrayList();
		//String xml = ServerInterface.QueryFlights(airport_code, departure_date, depart);
		document = DocumentHelper.parseText(xml);
		Element flightRoot = document.getRootElement();
		for (Iterator flightIter = flightRoot.elementIterator(); flightIter.hasNext();){
			Element flight = (Element) flightIter.next();
			Map Flight = new HashMap();
			// get flight number
			Flight.put("Flightnumber", Integer.parseInt(flight.attributeValue("Number")));
			// get airplane model
			Flight.put("AirplaneModel", flight.attributeValue("Airplane"));
			// get flight time
			Flight.put("Flighttime", Integer.parseInt(flight.attributeValue("FlightTime")));
			// get departure information
			List departList = new ArrayList();
			Element departure = flight.element("Departure");
			String departCode = departure.elementTextTrim("Code");
			String departTime = departure.elementTextTrim("Time");
			departList.add(departCode);
			departList.add(departTime);
			Flight.put("Departure", departList);
			// get arrival information
			List<Comparable> arrivalList = new ArrayList();
			Element arrival = flight.element("Arrival");
			String arrivalCode = arrival.elementTextTrim("Code");
			String arrivalTime = arrival.elementTextTrim("Time");
			arrivalList.add(arrivalCode);
			arrivalList.add(arrivalTime);
			Flight.put("Arrival", arrivalList);
			// get seating information
			Element seating = flight.element("Seating");
			// First class
			List firstClass = new ArrayList();
			Element Firstclass = seating.element("FirstClass");
			String firstPrice = Firstclass.attributeValue("Price");
			int firstTicket = Integer.parseInt(seating.elementTextTrim("FirstClass"));
			firstClass.add(firstPrice);
			firstClass.add(firstTicket);
			Flight.put("FirstClass", firstClass);
			// Coach
			List coach = new ArrayList();
			Element Coachclass = seating.element("Coach");
			String coachPrice = Coachclass.attributeValue("Price");
			int coachTicket = Integer.parseInt(seating.elementTextTrim("Coach"));
			coach.add(coachPrice);
			coach.add(coachTicket);
			Flight.put("Coach", coach);
			// collect all information
			flightInfo.add(Flight);	
		}
		return flightInfo;
	}

	public static void main(String[] args){
		// TODO Auto-generated method stub
		/*try {
			parseFlights parseFlight = new parseFlights();
			System.out.println(parseFlights.getFlights("BOS", "2016_05_10", true));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

}
