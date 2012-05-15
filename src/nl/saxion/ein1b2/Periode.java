package nl.saxion.ein1b2;

import java.util.GregorianCalendar;

public class Periode {
	private String naam;
	private GregorianCalendar startDatum;
	private GregorianCalendar eindDatum;
	
	public Periode(String naam, GregorianCalendar startDatum, GregorianCalendar eindDatum){
		this.setNaam(naam);
		this.setStartDatum(startDatum);
		this.setEindDatum(eindDatum);
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public GregorianCalendar getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(GregorianCalendar startDatum) {
		this.startDatum = startDatum;
	}

	public GregorianCalendar getEindDatum() {
		return eindDatum;
	}

	public void setEindDatum(GregorianCalendar eindDatum) {
		this.eindDatum = eindDatum;
	}
	
	
}
