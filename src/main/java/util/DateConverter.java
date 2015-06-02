package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {
	/**
	 * convert string date to a long based on epoch date 
	 * @param stringDate
	 * @return
	 */
	public static long getLong(String stringDate){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getDefault());
		Date date;
		try {
			date = (Date)formatter.parse(stringDate);
			return date.getTime();
		} catch (ParseException e) {
			System.err.println(DateConverter.class.getName()+".getLong : "+e.getMessage());
			return 0L;
		}
	}
	
	/**
	 * get long time (based on epoch date) before this date. Negative if date is passed.
	 * @param stringDate
	 * @return
	 */
	public static long getLongBefore(String stringDate){
		return getLong(stringDate) - getCurrentTime();
	}
	
	/**
	 * get current time
	 * @return
	 */
	public static long getCurrentTime(){
		return Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis();
	}
	
	public static void main(String[] args){
		System.out.println(getLong("03/01/2012"));
		System.out.println(getLong("3/1/2012"));
	}
}
