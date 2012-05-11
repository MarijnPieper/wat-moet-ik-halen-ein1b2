package nl.saxion.ein1b2;

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
	
	public String toString(){
		String result = this.get(GregorianCalendar.DAY_OF_MONTH) + "-" + this.get(GregorianCalendar.MONTH) + "-" + this.get(GregorianCalendar.YEAR);
		return result;
	}
	
	public String toStringForDB(){
		String result = this.get(GregorianCalendar.YEAR)  
				+ "-" + this.get(GregorianCalendar.MONTH) 
				+ "-" + this.get(GregorianCalendar.DAY_OF_MONTH)
				+ " " + this.get(GregorianCalendar.HOUR)
				+ ":" + this.get(GregorianCalendar.MINUTE);
		return result;
	}

}
