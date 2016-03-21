package Controller;

import java.util.Random;

public class Trip {
	private int num_hops = 0;
	private float totalPrice = 0;
	private float totalTime = 0;
	private boolean mixSeating = false;
	
	public int GetNumberofHops()
	{
		return num_hops;
	}
	
	public float GetPrice()
	{
		return totalPrice;
	}
	
	public float GetDuration()
	{
		return totalTime;
	}
	
	public boolean ContainsMixedSeating()
	{
		return mixSeating;
	}
	
	public Trip()
	{
		Random rand = new Random();
		this.num_hops = rand.nextInt(2);
		this.totalPrice = (float) ((rand.nextInt()%(30000-10000)+10000)/100.0);
		this.totalTime = (float) ((rand.nextInt()%(2400-100)+100)/100.0);
		
		if (this.num_hops > 0 && rand.nextInt(5)==0)
			this.mixSeating = true;
		
		if (this.totalPrice < 0)
			this.totalPrice *= -1;
		
		if (this.totalTime < 0)
			this.totalTime *= -1;
	}
	
	public Trip(int hops, float price, float time, boolean mixSeating)
	{
		this.num_hops = hops;
		this.totalPrice = price;
		this.totalTime = time;
		this.mixSeating = mixSeating;
	}
}
