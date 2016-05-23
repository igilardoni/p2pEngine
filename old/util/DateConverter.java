/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
	 * @param stringDate well formatted (yyyy-MM-dd)
	 * @return
	 */
	public static long getLongBefore(String stringDate){
		return getLong(stringDate) - getCurrentTime();
	}
	
	/**
	 * get string format date (yyyy-MM-dd)
	 * @param longDate
	 * @return
	 */
	public static String getString(long longDate){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getDefault());
		Date date = new Date(longDate);
		return formatter.format(date).toString();
	}
	
	/**
	 * get current time
	 * @return
	 */
	public static long getCurrentTime(){
		return Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis();
	}
	
	public static void main(String[] args){
		System.out.println(getString(getLong("2015-06-02")));
		System.out.println(getString(System.currentTimeMillis()));
	}
}
