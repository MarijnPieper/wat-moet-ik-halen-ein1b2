package nl.saxion.ein1b2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CustomDate extends GregorianCalendar {

	public CustomDate(){
		super();
	}
	
	public CustomDate(int day, int month, int year){
		super(year, month - 1, day);
	}
	
	public CustomDate(int day, int month, int year, int hours, int minuts){
		super(year, month - 1, day, hours, minuts);
	}
	
	
	public CustomDate(String Date) {
		super();
		String[] splitDate = Date.split("-");
		String[] splitDay = splitDate[2].split(" ");
		String[] splitTime = splitDay[1].split(":");
		set(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]) -1, Integer.parseInt(splitDay[0]), Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
		
	}
	
	public void setTime(int hour, int minute){
		this.set(GregorianCalendar.HOUR_OF_DAY, hour);
		this.set(GregorianCalendar.MINUTE, minute);
	}
	
	public String toString(){
		return Integer.toString(this.get(Calendar.DAY_OF_MONTH)) + "-" + Integer.toString(this.get(Calendar.MONTH) + 1) + "-" + Integer.toString(this.get(Calendar.YEAR));
	}
	
	public String toStringDatumTijd(){
		return Integer.toString(this.get(Calendar.DAY_OF_MONTH)) + "-" + Integer.toString(this.get(Calendar.MONTH) + 1) + "-" + Integer.toString(this.get(Calendar.YEAR)) + " " + Integer.toString(this.get(Calendar.HOUR)) + ":" + Integer.toString(this.get(Calendar.MINUTE));
	 }

	public String toStringForDB(){
		return Integer.toString(this.get(Calendar.YEAR)) + "-" + Integer.toString(this.get(Calendar.MONTH) + 1) + "-" + Integer.toString(this.get(Calendar.DAY_OF_MONTH)) + " " + Integer.toString(this.get(Calendar.HOUR)) + ":" + Integer.toString(this.get(Calendar.MINUTE));
//		String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
//	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//		return sdf.format(this.getTime());
		// Oude code voor format.
	}
	
	public String getTimeAsString(){
		String DATE_FORMAT = "HH:mm";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(this.getTime());
	}

}
