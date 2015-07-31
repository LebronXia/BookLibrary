package com.atom.android.booklist.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date time;
	
	public DateUtils(String datetime){
		try {
			time = df.parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean getTimeLimted(){
		
		Date nowTime = new Date();
		try
		{
		    
		    long diff = nowTime.getTime() - time.getTime();
		    long days = diff / (1000 * 60 * 60 * 24);
		    if(days > 7)
		    	return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	
	@SuppressWarnings("deprecation")
	public String getDateAndMoth(){
		String DayAndMoth;
		DayAndMoth = time.getMonth() + "." +time.getDay();
		return DayAndMoth;
	}
	
	public static int getYear(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime = null;
		try{
		datetime = df.parse(date);
		}catch(Exception e){
			
		}
		return datetime.getYear();
	}
	
}


