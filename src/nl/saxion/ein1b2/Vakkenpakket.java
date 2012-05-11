package nl.saxion.ein1b2;

import java.util.GregorianCalendar;



public class Vakkenpakket {
	
	private String naam;
	private GregorianCalendar startdatum;
	private GregorianCalendar einddatum;
	
	public Vakkenpakket(){
		
	}
	

	protected String getNaam() {
		return naam;
	}

	protected void setNaam(String naam) {
		this.naam = naam;
	}

	protected GregorianCalendar getStartdatum() {
		return startdatum;
	}

	protected void setStartdatum(GregorianCalendar startdatum) {
		this.startdatum = startdatum;
	}

	protected GregorianCalendar getEinddatum() {
		return einddatum;
	}

	protected void setEinddatum(GregorianCalendar einddatum) {
		this.einddatum = einddatum;
	}
	
	

}
