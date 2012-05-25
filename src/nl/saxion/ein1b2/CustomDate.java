package nl.saxion.ein1b2;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class CustomDate extends GregorianCalendar {

	public CustomDate(){
		super();
	}
	
	public CustomDate(int day, int month, int year){
		super(year, month, day);
	}
	
	public CustomDate(int day, int month, int year, int hours, int minuts){
		super(year, month, day, hours, minuts);
	}
	
	
	public CustomDate(String Date) {
		super();
		String[] splitDate = Date.split("-");
		String[] splitDay = splitDate[2].split(" ");
		String[] splitTime = splitDay[1].split(":");
		set(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDay[0]), Integer.parseInt(splitTime[1]), Integer.parseInt(splitTime[0]));
		
	}
	
	
	
	public void setTime(int hour, int minute){
		this.set(GregorianCalendar.HOUR, hour);
		this.set(GregorianCalendar.MINUTE, minute);
	}
	
	public String toString(){
		String DATE_FORMAT = "dd-MM-yyyy";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    return sdf.format(this.getTime());
	}
	public String toStringDatumTijd(){
		String DATE_FORMAT = "dd-MM-yyyy h:mm";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    return sdf.format(this.getTime());
	}
	
	
	public String toStringForDB(){
		String result = this.get(GregorianCalendar.YEAR)  
				+ "-" + this.get(GregorianCalendar.MONTH) 
				+ "-" + this.get(GregorianCalendar.DAY_OF_MONTH)
				+ " " + this.get(GregorianCalendar.HOUR)
				+ ":" + this.get(GregorianCalendar.MINUTE);
		return result;
	}
	
	public String getTimeAsString(){
		String result = this.get(GregorianCalendar.HOUR) + ":" + this.get(GregorianCalendar.MINUTE);
		return result;
	}

}
