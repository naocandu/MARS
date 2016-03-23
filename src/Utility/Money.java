package Utility;

public class Money {
	public int dollars;
	public int cents;
	
	public Money(int Dollars, int Cents)
	{
		Set(Dollars,Cents);
	}
	
	public Money(String value)
	{
		Set(value);
	}
	
	public Money(double value)
	{
		Set(value);
	}
	
	public Money()
	{
		dollars = 0;
		cents = 0;
	}
	
	public void Set(int Dollars, int Cents)
	{
		dollars = Dollars;
		cents = Cents;
		
		validate();
	}
	
	public void Set(String value)
	{
		String stripped = "";
		
		for (int i = 0;i < value.length();i++)
		{
			if ((value.charAt(i) >= '0' && value.charAt(i) <= '9') || value.charAt(i) == '.')
			{
				stripped += value.charAt(i);
			}
		}
		if (stripped.length() < 2)
		{
			System.out.println("error");
		}
		double numerical = Double.parseDouble(stripped);
		Set(numerical);
	}
	
	public void Set(double value)
	{
		dollars = (int)value;
		cents = (int)((value-dollars)*100);
	}
	
	public void Add(Money value)
	{
		Set(this.dollars + value.dollars,this.cents + value.cents);
	}
	
	public void validate()
	{
		while (cents >= 100)
		{
			cents-=100;
			dollars++;
		}
		
		while (cents < 0)
		{
			cents+=100;
			dollars--;
		}
	}
	
	public String toString()
	{
		return "$" + dollars + "." + (cents<10?"0":"") + cents;
	}
	
	public float toFloat()
	{
		return (float)(dollars + (cents/100.));
	}
}
