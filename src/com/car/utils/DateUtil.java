package com.car.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Date parse(String dateStr) {
		Date date = null;
		try {
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return date;
	}

	
	public static Integer getYear(Date date) {
		Integer year = 0;
		try {
			Calendar c = Calendar.getInstance();
		    c.setTime(date);
		    year = c.get(Calendar.YEAR);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	    return year;
	}
	public static String date2String(Date date) {
		String dateStr = "";   
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        try {   
            dateStr = sdf.format(date);     
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
        return dateStr;
		
	}
}
