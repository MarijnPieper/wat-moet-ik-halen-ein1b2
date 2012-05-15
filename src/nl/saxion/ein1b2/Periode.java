package nl.saxion.ein1b2;

import java.util.GregorianCalendar;

public class Periode {
	private String naam;
	private CustomDate startdatum;
	private CustomDate einddatum;
	
	public Periode(String naam, CustomDate startDatum, CustomDate eindDatum){
		this.naam = naam;
		this.startdatum = startDatum;
		this.einddatum = eindDatum;
	}
	
	public Periode(String naam, String startDatum, String eindDatum) {
		this.naam = naam;
		this.startdatum = convertStringtoDate(startDatum);
		this.einddatum = convertStringtoDate(eindDatum);
	}
	
	private CustomDate convertStringtoDate(String Date) {
		String[] splitDate = Date.split("-");
		String[] splitDay = splitDate[2].split(" ");
		CustomDate newDate = new CustomDate(Integer.parseInt(splitDay[0]), Integer.parseInt(splitDate[1]),Integer.parseInt(splitDate[0]));
		return newDate;
	}
	
	protected String getNaam() {
		return naam;
	}

	protected void setNaam(String naam) {
		this.naam = naam;
	}

	protected CustomDate getStartdatum() {
		return startdatum;
	}

	protected void setStartdatum(CustomDate startdatum) {
		this.startdatum = startdatum;
	}

	protected CustomDate getEinddatum() {
		return einddatum;
	}

	protected void setEinddatum(CustomDate einddatum) {
		this.einddatum = einddatum;
	}
	
	
}
