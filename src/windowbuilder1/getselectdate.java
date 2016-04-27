package windowbuilder1;

/**
 * Class provide a method of transforming a date string into 3 int type numbers.
 * 
 * @author Hao Liu
 * @since 2016-3-22
 */
public class getselectdate
{
	/**
	 * create a string for test
	 */
	private static String a = "2016_03_20";
	
	/**
	 * create a int-type array of date.
	 * 
	 * @param date
	 * @return an array of date, whose items are year, month, day.
	 */
	public static int[] mydate(String date)
	{
		String[] mydate = date.split("-");
		int imydate[] =
		{ 0, 0, 0 };
		imydate[0] = Integer.parseInt(mydate[0]);
		imydate[1] = Integer.parseInt(mydate[1]);
		imydate[2] = Integer.parseInt(mydate[2]);
		
		return imydate;
	}
	
	/**
	 * get string of date without "-", in order for later coding.
	 * 
	 * @param date
	 * @return a string without "-"
	 */
	public static String[] strmydate(String date)
	{
		String[] mydate = date.split("-");
		return mydate;
	}
	
	public static void main(String[] args)
	{
		
		int b[] =
		{ 0, 0, 0 };
		for (int i = 0; i < 3; i++)
		{
			b[i] = mydate(a)[i];
			System.out.println(b[i]);
		}
	}
}
