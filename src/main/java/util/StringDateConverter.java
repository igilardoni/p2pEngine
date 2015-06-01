package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringDateConverter {
	public static long getLong(String stringDate){
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy");
		Date date;
		try {
			date = (Date)formatter.parse(stringDate);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0L;
		}
	}
}
