package AirFlight;

import java.util.List;

import org.dom4j.DocumentException;

import XMLparser.parseAirplanes;;

public class Airplane {
	
	public static List findAirplane(String model) throws DocumentException{
		return parseAirplanes.getAirplane(model);
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(findAirplane("777"));
	}

}
