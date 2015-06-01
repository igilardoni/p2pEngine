package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringDateConverter {
	public static long getLong(String stringDate){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		Date date;
		try {
			date = (Date)formatter.parse(stringDate);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	public static void main(String[] args){
		System.out.println(getLong("01/01/1970"));
	}
}
