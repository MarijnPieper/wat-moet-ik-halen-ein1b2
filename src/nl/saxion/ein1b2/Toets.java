package nl.saxion.ein1b2;

import java.util.GregorianCalendar;

public class Toets {
						
	private int vak_id;
	private int toetstype_id;
	private String beschrijving;
	private CustomDate datum;
	
	public Toets(int vak_id, int toetstype_id, String beschrijving, CustomDate datum) {
		
		this.vak_id = vak_id;
		this.toetstype_id = toetstype_id;
		this.beschrijving = beschrijving;
		this.datum = datum;
	}
	
	public Toets(String beschrijving) {
		this.beschrijving = beschrijving;
	}

	public int getVak_id() {
		return vak_id;
	}

	public void setVak_id(int vak_id) {
		this.vak_id = vak_id;
	}

	public int getToetstype_id() {
		return toetstype_id;
	}

	public void setToetstype_id(int toetstype_id) {
		this.toetstype_id = toetstype_id;
	}

	public String getBeschrijving() {
		return beschrijving;
	}

	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}

	public CustomDate getDatum() {
		return datum;
	}

	public void setDatum(CustomDate datum) {
		this.datum = datum;
	}

}
