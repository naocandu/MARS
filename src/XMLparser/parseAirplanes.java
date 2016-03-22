package XMLparser;


import Server.ServerInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class parseAirplanes {
	static Document document;
	static String xml = ServerInterface.QueryAirplanes();
	static List airplaneInfo = new ArrayList<Integer>();
	
	public static List getAirplane(String model) throws DocumentException{
		Document document = DocumentHelper.parseText(xml);
		Element airplaneRoot = document.getRootElement();
		for (Iterator<?> airplaneiter = airplaneRoot.elementIterator(); airplaneiter.hasNext();){
			Element airplane = (Element) airplaneiter.next();
			if (airplane.attributeValue("Model").equals(model)){
				String FirstClass = airplane.elementTextTrim("FirstClassSeats");
				String Coach = airplane.elementTextTrim("CoachSeats");
				airplaneInfo.add(airplane.attributeValue("Manufacturer"));
				airplaneInfo.add(airplane.attributeValue("Model"));
				airplaneInfo.add(Integer.parseInt(FirstClass));
				airplaneInfo.add(Integer.parseInt(Coach));
			}
		}
		return airplaneInfo;
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(getAirplane("A320"));
	}
}
