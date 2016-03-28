/*
 * Class provide a method of transforming a date string into 3 int type numbers.
 * @author Hao Liu
 * @since 2016-3-22
 */
package windowbuilder1;

public class getselectdate {

	private static String a="2016_03_20";
	public static int[] mydate(String date)
	{
		String[] mydate = date.split("-");
		int imydate[]={0,0,0};
		imydate[0] = Integer.parseInt(mydate[0]);
		imydate[1] = Integer.parseInt(mydate[1]);
		imydate[2] = Integer.parseInt(mydate[2]);
		
		return imydate;
	}
	public static String[] strmydate(String date)
	{
		String[] mydate = date.split("-");
		return mydate;
	}
	public static void main(String[] args) {
	
		int b[]={0,0,0};
		for(int i=0;i<3;i++)
		{
		b[i] = mydate(a)[i];
		System.out.println(b[i]);
		}
	}
}
