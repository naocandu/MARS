package XMLparser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Parsing Time
 * @author Zheng Nie
 *
 */

public class ParseTime {
	static Document document;
	
	/**
	 * gets the timezone offset given a set of coordinates
	 * @param xml the xml with timezone offset information 
	 * @return the hour offset from GMT of the coordinates
	 * @throws DocumentException
	 */
	
	public static Double timeOffset(String xml) throws DocumentException{
		//String xml = ServerInterface.QueryTimezone(lat,lng);
		document = DocumentHelper.parseText(xml);
		Element time = document.getRootElement();
		Element timezone = (Element) time.element("timezone");
		Double offset = Double.parseDouble(timezone.elementTextTrim("gmtOffset"));
		return offset;
	}
	
	/**
	 * main function test driver for retrieving timezone offset information
	 * @param args
	 */

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		//System.out.println(timeOffset(33.94443, -118.408356));

	}

}
