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
public class DateTime {

	private Calendar dateTime;
	
	/**
	 * Default constructor initializes a new Calendar instance
	 */
	public DateTime()
	{
		dateTime = Calendar.getInstance();
	}
	
	/**
	 * Gets the index of the next character that is not 'Char' from a string with a given start index
	 * 
	 * @param str the string to search
	 * @param startIndex the first index of a consecutive sequence of a character
	 * @param Char the character of which to compare against (this will return the first index that does not contain 'Char')
	 * @return -1 if input is invalid, the length of the string if 'Char' is consecutive to the last character, or
	 * the index of the last non-consecutive character
	 */
	private int GetLastNonConsecutive(String str, int startIndex,char Char)
	{
		if (startIndex < 0 || startIndex >= str.length())
			return -1;
		
		for (int i = startIndex;i < str.length();i++)
		{
			if (str.charAt(i) != Char)
				return i;
		}
		
		return str.length();
	}
	
	/**
	 * Creates a DateTime Instance from a date and time in the form of a string
	 * in a specified format with values Y (year), M (month), D (day),
	 * h (hour), m (minute), s (second)
	 * 
	 * @param dateTime the date and time as a string
	 * @param format the format of the string using the specified character values
	 */
	public DateTime(String dateTime, String format)
	{
		this.dateTime = Calendar.getInstance();
		int[] year = new int[2];
		int[] month = new int[2];
		int[] day = new int[2];
		int[] hour = new int[2];
		int[] minute = new int[2];
		int[] second = new int[2];
		
		year[0] = format.indexOf('Y');
		year[1] = GetLastNonConsecutive(format,year[0],'Y');
		
		month[0] = format.indexOf('M');
		month[1] = GetLastNonConsecutive(format,month[0],'M');
		
		day[0] = format.indexOf('D');
		day[1] = GetLastNonConsecutive(format,day[0],'D');
		
		hour[0] = format.indexOf('h');
		hour[1] = GetLastNonConsecutive(format,hour[0],'h');
		
		minute[0] = format.indexOf('m');
		minute[1] = GetLastNonConsecutive(format,minute[0],'m');
		
		second[0] = format.indexOf('s');
		second[1] = GetLastNonConsecutive(format,second[0],'s');
		
		int Y = (year[0]==-1?0:Integer.parseInt(dateTime.substring(year[0], year[1])));
		int M = (month[0]==-1?0:Integer.parseInt(dateTime.substring(month[0], month[1])));
		int D = (day[0]==-1?0:Integer.parseInt(dateTime.substring(day[0], day[1])));
		int h = (hour[0]==-1?0:Integer.parseInt(dateTime.substring(hour[0], hour[1])));
		int m = (minute[0]==-1?0:Integer.parseInt(dateTime.substring(minute[0], minute[1])));
		int s = (second[0]==-1?0:Integer.parseInt(dateTime.substring(second[0], second[1])));
		
		this.Set(D, M, Y, h, m, s);
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
	 * Sets the Calendar date and time from a string
	 * in a specified format with values Y (year), M (month), D (day),
	 * h (hour), m (minute), s (second)
	 * 
	 * @param dateTime the date and time as a string
	 * @param format the format of the string using the specified character values
	 */
	public void Set(String dateTime, String format)
	{
		int[] year = new int[2];
		int[] month = new int[2];
		int[] day = new int[2];
		int[] hour = new int[2];
		int[] minute = new int[2];
		int[] second = new int[2];
		
		year[0] = format.indexOf('Y');
		year[1] = GetLastNonConsecutive(format,year[0],'Y');
		
		month[0] = format.indexOf('M');
		month[1] = GetLastNonConsecutive(format,month[0],'M');
		
		day[0] = format.indexOf('D');
		day[1] = GetLastNonConsecutive(format,day[0],'D');
		
		hour[0] = format.indexOf('h');
		hour[1] = GetLastNonConsecutive(format,hour[0],'h');
		
		minute[0] = format.indexOf('m');
		minute[1] = GetLastNonConsecutive(format,minute[0],'m');
		
		second[0] = format.indexOf('s');
		second[1] = GetLastNonConsecutive(format,second[0],'s');
		
		int Y = (year[0]==-1?0:Integer.parseInt(dateTime.substring(year[0], year[1])));
		int M = (month[0]==-1?0:Integer.parseInt(dateTime.substring(month[0], month[1])));
		int D = (day[0]==-1?0:Integer.parseInt(dateTime.substring(day[0], day[1])));
		int h = (hour[0]==-1?0:Integer.parseInt(dateTime.substring(hour[0], hour[1])));
		int m = (minute[0]==-1?0:Integer.parseInt(dateTime.substring(minute[0], minute[1])));
		int s = (second[0]==-1?0:Integer.parseInt(dateTime.substring(second[0], second[1])));
		
		this.Set(D, M, Y, h, m, s);
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
