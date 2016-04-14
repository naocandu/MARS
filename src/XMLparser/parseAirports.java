package XMLparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * Parsing Airports
 * @author zhengnie
 *
 */


public class parseAirports {
	public static String xml = null;
	static Document document;
	static Map<String, List<Comparable>> airport_collection = new HashMap();

	// get collected information of all airports	
	public static Map<String, List<Comparable>> readXML(){
		try{
			// reading XML file
			/*
			InputStream inputStream = new FileInputStream(new File(
					"/Users/zhengnie/Documents/Courses/CS509-Software Design/Project/Project Interfaces(API)/Sample XML for list of Airports.xml"));
			SAXReader saxReader=new SAXReader();
			Document document = saxReader.read(inputStream);
			*/
			document = DocumentHelper.parseText(xml);
			// retrieve information about the root
			Element rootElement = document.getRootElement();
			
			// retrieve information about child nodes
			List<?> childrenList = rootElement.elements();
			
			// iterate over the document
			//List airportCode = new ArrayList();
			//List airportName = new ArrayList();
			//List geo_collection = new ArrayList();// store latitude and longitude information
			for (Iterator iter=rootElement.elementIterator(); iter.hasNext();){
				List geography = new ArrayList();
				Element e = (Element) iter.next();
				//airportCode.add(e.attributeValue("Code"));
				//airportName.add(e.attributeValue("Name"));
				// get geographical information of an airport
				Element latitude = e.element("Latitude");
				Element longtitude = e.element("Longitude");
				geography.add(e.attributeValue("Code"));
				geography.add(e.attributeValue("Name"));
				geography.add(Float.parseFloat(latitude.getText()));
				geography.add(Float.parseFloat(longtitude.getText()));
				// collect geographical information of all airports
				//geo_collection.add(geography.subList(1, 3));
				airport_collection.put(e.attributeValue("Code"), geography);
			}
			//System.out.println(airportCode);
			//System.out.println(airportName);
			//System.out.println(airport_collection);
			//System.out.println(geo_collection);
		} catch (Exception e){
			e.printStackTrace();
		}
		return airport_collection;
	}
	
	// get the code of a designated airport
	public static List getCode() throws DocumentException {
		List airportCode = new ArrayList();
		document = DocumentHelper.parseText(xml);
		Element rootElement = document.getRootElement();
		for (Iterator iter=rootElement.elementIterator(); iter.hasNext();){
			Element airports = (Element) iter.next();
			airportCode.add(airports.attributeValue("Code"));
			/*
			if (airports.attributeValue("Code").equals(airportcode)){
				String latitude = airports.elementTextTrim("Latitude");
				String longitude = airports.elementTextTrim("Longitude");
				geoInfo.add(Float.parseFloat(latitude));
				geoInfo.add(Float.parseFloat(longitude));	
			}
			*/
		}
		return airportCode; 
	}
		
		// get the code and name of a designated airport
	public static List<String> getName() throws DocumentException {
		List<String> airportName = new ArrayList<String>();
		document = DocumentHelper.parseText(xml);
		Element rootElement = document.getRootElement();
		for (Iterator iter=rootElement.elementIterator(); iter.hasNext();){
			Element airports = (Element) iter.next();
			airportName.add(airports.attributeValue("Name"));
			/*
			if (airports.attributeValue("Code").equals(airportcode)){
				String latitude = airports.elementTextTrim("Latitude");
				String longitude = airports.elementTextTrim("Longitude");
				geoInfo.add(Float.parseFloat(latitude));
				geoInfo.add(Float.parseFloat(longitude));	
			}
			*/
		}
		return airportName; 
	}
				
	// get the info of a designated airport
	public static List<String> getInfo() throws DocumentException {
		List airportNameCode = new ArrayList();
		document = DocumentHelper.parseText(xml);
		Element rootElement = document.getRootElement();
		for (Iterator iter=rootElement.elementIterator(); iter.hasNext();){
			Element airports = (Element) iter.next();
			airportNameCode.add(airports.attributeValue("Code")+", "+airports.attributeValue("Name"));
		}
		return airportNameCode; 
	}
	

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		parseAirports Parse = new parseAirports();
		Map<String, List<Comparable>> collection=Parse.readXML();
		System.out.println(collection);
		System.out.println(getCode());
		System.out.println(getName());
		System.out.println(getInfo());
	}
}
