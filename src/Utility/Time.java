/**
 * Package contains utility classes to help hold and format
 * misc. types of data
 */
package Utility;
import java.util.Calendar;

/**
 * Class provides minimal functionality to work with dates and times
 * 
 * @author Anthony Botelho
 * @since 2016-03-13
 */
public class Time {

	private Calendar dateTime;
	
	/**
	 * Default constructor initializes a new Calendar instance
	 */
	public Time()
	{
		dateTime = Calendar.getInstance();
	}
	
	/**
	 * Sets the Calendar date
	 * 
	 * @param day numerical day of the month
	 * @param month numerical month of the year
	 * @param year numerical year
	 */
	public void SetDate(int day, int month, int year)
	{
		dateTime.set(year, month, day);
	}
	
	/**
	 * Sets the Time of day
	 * 
	 * @param hour hour of the day (24 hour time)
	 * @param minute minute of the hour
	 * @param second second of the minute
	 */
	public void SetTime(int hour, int minute, int second)
	{
		dateTime.set(Calendar.HOUR_OF_DAY, hour);
		dateTime.set(Calendar.MINUTE, minute);
		dateTime.set(Calendar.SECOND, second);
	}
	
	/**
	 * Sets all date and time values
	 * 
	 * @param day day numerical day of the month
	 * @param month numerical month of the year
	 * @param year numerical year
	 * @param hour hour of the day (24 hour time)
	 * @param minute minute of the hour
	 * @param second second of the minute
	 */
	public void Set(int day, int month, int year, int hour, int minute, int second)
	{
		this.SetDate(day,month,year);
		this.SetTime(hour,minute,second);
	}
	
	/**
	 * Returns a string representing the date and time for purposes of display
	 * 
	 * @return date and time as a string in the format MM/DD/YYYY HH:MM:SS
	 */
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
	
	/**
	 * Returns a string representing the calendar date without time in a
	 * format consistent with that expected in ServerInterface
	 * 
	 * @return date as a string in the format YYYY_MM_DD
	 * @see Server.ServerInterface
	 */
	public String getDateString()
	{
		String month = Integer.toString(dateTime.get(Calendar.MONTH));
		String day = Integer.toString(dateTime.get(Calendar.DATE));
		String year = Integer.toString(dateTime.get(Calendar.YEAR));
		

		month = (month.length()==1?"0":"") + month;
		day = (day.length()==1?"0":"") + day;

		return year+"_"+month+"_"+day;
	}
	
	/**
	 * Returns a string representing the time without the calendar date
	 * @return time as a string in the format HH:MM:SS
	 */
	public String getTimeString()
	{
		String hour = Integer.toString(dateTime.get(Calendar.HOUR_OF_DAY));
		String minute = Integer.toString(dateTime.get(Calendar.MINUTE));
		String second = Integer.toString(dateTime.get(Calendar.SECOND));
		
		hour = (hour.length()==1?"0":"") + hour;
		minute = (minute.length()==1?"0":"") + minute;
		second = (second.length()==1?"0":"") + second;
		
		return hour+":"+minute+":"+second;
	}
}
