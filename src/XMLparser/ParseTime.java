package XMLparser;

import Server.ServerInterface;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseTime {
	static Document document;
	
	public static Double timeOffset(String xml) throws DocumentException{
		//String xml = ServerInterface.QueryTimezone(lat,lng);
		document = DocumentHelper.parseText(xml);
		Element time = document.getRootElement();
		Element timezone = (Element) time.element("timezone");
		Double offset = Double.parseDouble(timezone.elementTextTrim("gmtOffset"));
		return offset;
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		//System.out.println(timeOffset(33.94443, -118.408356));

	}

}
