package com.accenture.api.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 *
 * @author marlon.naraja
 */
@Component
public class KusinaDateUtils {

    
    public static final String DATE_FORMAT = "E MMM dd"; // Mon, Jan 01
    
    public long convertStrDateToMillis(String date) {

        return new DateTime(Long.parseLong(date)).getMillis();
    }

  /*  public long getEndTimeToday(String timezoneOffset) {
    	Date date = new Date();
    	long millis = 0;
    	
    	SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	readFormat.setTimeZone(TimeZone.getTimeZone("GMT" + timezoneOffset));
    	String dateStr = readFormat.format(date);
    	SimpleDateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	Date today;
		try {
			today = writeFormat.parse(dateStr);
			today = org.apache.commons.lang3.time.DateUtils.setHours(today, 23);
	        today = org.apache.commons.lang3.time.DateUtils.setMinutes(today, 59);
	        today = org.apache.commons.lang3.time.DateUtils.setSeconds(today, 59);
	        millis = new DateTime(today).getMillis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Date today = new Date();
       
        return millis;

    }*/

/*    public long getStartTimeToday() {
        Date today = new Date();
        today = org.apache.commons.lang3.time.DateUtils.setHours(today, 0);
        today = org.apache.commons.lang3.time.DateUtils.setMinutes(today, 0);
        today = org.apache.commons.lang3.time.DateUtils.setSeconds(today, 0);

        return new DateTime(today).getMillis();
    }*/

/*    public long addMinusMonthToDate(Long epoch_time, int n) {
        Date d = new Date(epoch_time);
        Date d2 = new Date(d.toGMTString().substring(0, 11));
        Date newDate = org.apache.commons.lang3.time.DateUtils.addMonths(d2, n);

        return new DateTime(newDate).getMillis();
    }
*/
    public Long convertStrDateToEpochMilli(String strDate) {

        try {
            return Long.parseLong(strDate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return new DateTime(strDate).getMillis();

    }
    public String formatDate(long date,String timezoneOffset) {
    	String decodedTZ =timezoneOffset;
    	try {
			decodedTZ = java.net.URLDecoder.decode(timezoneOffset, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Date d = new Date(date); 	
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT" + decodedTZ));

        return sdf.format(d);

    }
    public String checkDate(long date,String timezoneOffset) {
    	String decodedTZ =timezoneOffset;
    	try {
			decodedTZ = java.net.URLDecoder.decode(timezoneOffset, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Date d = new Date(date); 	
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT" + decodedTZ));

        return sdf.format(d);

    }
    
    public String checkEpochTime(String date) {
    	if(date.length() > 10) {
    		return date + "L";
    	}else {
    		return date + "000L";
    	}	
    }
    
    public String convertEpochToDate(String date) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    	
    	return sdf.format(new Date(Long.parseLong(date)));
    }
    
    public long getNowDateToMillis() {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	String dateString = sdf.format(new Date());
    	
    	Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

    	return date.getTime();
    }
    
}
