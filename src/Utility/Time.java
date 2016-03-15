/*************************************************************************
 * Author(s): Anthony Botelho 
 * Created: March 2016
 * 
 * Description:
 * Class provides functionality to work with dates and times and provide
 * such information in the format expected by ServerInterface
 */

package Utility;
import java.util.Calendar;

public class Time {

	private static Calendar dateTime;
	
	public Time()
	{
		dateTime = Calendar.getInstance();
	}
	
	public void SetDate(int day, int month, int year)
	{
		dateTime.set(year, month, day);
	}
	
	public void SetTime(int hour, int minute, int second)
	{
		dateTime.set(Calendar.HOUR_OF_DAY, hour);
		dateTime.set(Calendar.MINUTE, minute);
		dateTime.set(Calendar.SECOND, second);
	}
	
	public void Set(int day, int month, int year, int hour, int minute, int second)
	{
		this.SetDate(day,month,year);
		this.SetTime(hour,minute,second);
	}
	
	public String getFullDateString()
	{
		String month = Integer.toString(dateTime.get(Calendar.MONTH));
		String day = Integer.toString(dateTime.get(Calendar.DATE));
		String year = Integer.toString(dateTime.get(Calendar.YEAR));
		
		String hour = Integer.toString(dateTime.get(Calendar.HOUR_OF_DAY));
		String minute = Integer.toString(dateTime.get(Calendar.MINUTE));
		String second = Integer.toString(dateTime.get(Calendar.SECOND));
		
		month = (month.length()==1?"0":"") + month;
		day = (day.length()==1?"0":"") + day;
		hour = (hour.length()==1?"0":"") + hour;
		minute = (minute.length()==1?"0":"") + minute;
		second = (second.length()==1?"0":"") + second;
		
		return month+"/"+day+"/"+year+" "+hour+":"+minute+":"+second;
	}
	
	public String getDateString()
	{
		String month = Integer.toString(dateTime.get(Calendar.MONTH));
		String day = Integer.toString(dateTime.get(Calendar.DATE));
		String year = Integer.toString(dateTime.get(Calendar.YEAR));
		

		month = (month.length()==1?"0":"") + month;
		day = (day.length()==1?"0":"") + day;

		return year+"_"+month+"_"+day;
	}
	
	public String getTimeDateString()
	{
		String hour = Integer.toString(dateTime.get(Calendar.HOUR_OF_DAY));
		String minute = Integer.toString(dateTime.get(Calendar.MINUTE));
		String second = Integer.toString(dateTime.get(Calendar.SECOND));
		
		hour = (hour.length()==1?"0":"") + hour;
		minute = (minute.length()==1?"0":"") + minute;
		second = (second.length()==1?"0":"") + second;
		
		return hour+":"+minute+":"+second;
	}
	
	public static void main(String[] args) {
		

	}

}
