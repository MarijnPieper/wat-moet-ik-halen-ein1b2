package nl.saxion.ein1b2;

import java.util.GregorianCalendar;

public class Toets {
	
	private Vak vak;
	private String type;
	private GregorianCalendar datum;
	private int aantalToetsen;

	
	public Toets(Vak vak, String type, GregorianCalendar datum, int aantalToetsen) {
		
		this.vak = vak;
		this.type = type;
		this.datum = datum;
		this.aantalToetsen = aantalToetsen;
	}

	public Vak getVak() {
		return vak;
	}

	public void setVak(Vak vak) {
		this.vak = vak;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GregorianCalendar getDatum() {
		return datum;
	}

	public void setDatum(GregorianCalendar datum) {
		this.datum = datum;
	}
	
	public int getAantalToetsen() {
		return aantalToetsen;
	}
	
	public void setAantalToetsen(int aantalToetsen) {
		this.aantalToetsen = aantalToetsen;
	}
	
	

	
}
