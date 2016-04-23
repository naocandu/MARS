package AirFlight;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import Controller.ValidationController;
import XMLparser.parseAirplanes;;


/**
 * Airplane
 * @author Zheng Nie
 *
 */

public class Airplane {
	
	private static List<Airplane> airplanes = new ArrayList<Airplane>();
	
	public String name = "";
	public String model = "";
	public int FC_Capacity = 0;
	public int EC_Capacity = 0;
	
	/**
	 * gets airplane information given the airplane model
	 * @param model the airplane model
	 * @return a list containing the airplane information 
	 */
	
	public static Airplane findAirplane(String model){
		
		for (int i = 0;i < airplanes.size();i++)
		{
			if (airplanes.get(i).model == model)
				return airplanes.get(i);
		}
		
		List raw_airplane = ValidationController.Instance().GetAirplaneList();

		airplanes.add(new Airplane(
				(String)raw_airplane.get(0),
				(String)raw_airplane.get(1),
				(int)raw_airplane.get(2),
				(int)raw_airplane.get(3)));
		return airplanes.get(airplanes.size()-1);
		
	}
	
	public Airplane(String name, String model, int FC_Capacity, int EC_Capacity)
	{
		this.name = name;
		this.model = model;
		this.FC_Capacity = FC_Capacity;
		this.EC_Capacity = EC_Capacity;
	}
	
	/**
	 * gets first class seat capacity
	 * @return the number of remaining first class seats
	 */
	
	public int FirstClassCapacity()
	{
		return this.FC_Capacity;
	}	
	
	/**
	 * gets economy class seat capacity
	 * @return the number of remaining economy class seats
	 */
	public int EconomyCapacity()
	{
		return this.EC_Capacity;
	}

	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		System.out.println(findAirplane("777"));
	}

}
