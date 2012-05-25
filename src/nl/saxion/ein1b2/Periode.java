package nl.saxion.ein1b2;

import java.util.ArrayList;

public class Periode {
	private int id;
	private String naam;
	private CustomDate startDatum;
	private CustomDate eindDatum;
	private ArrayList<Vak> vakken;
	

	public Periode(String naam, CustomDate startDatum, CustomDate eindDatum, ArrayList<Vak> vakken){
		this.setNaam(naam);
		this.setStartDatum(startDatum);
		this.setEindDatum(eindDatum);
		this.setVakken(vakken);
	}
	
	// Alleen gebruiken i.c.m. Database
	public Periode(int id, String naam, String startDatum, String eindDatum) {
		this.setID(id);
		this.setNaam(naam);
		this.setStartDatum(convertStringtoDate(startDatum));
		this.setEindDatum(convertStringtoDate(eindDatum));
	}
	
	//TODO: zit in custom date
	// Zet string om naar CustomDate. Zet alleen Datum om, niet de tijd!
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

	public CustomDate getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(CustomDate startDatum) {
		this.startDatum = startDatum;
	}

	public CustomDate getEindDatum() {
		return eindDatum;
	}

	public void setEindDatum(CustomDate eindDatum) {
		this.eindDatum = eindDatum;
	}
	
	protected void setID(int ID) {
		this.id = ID;
	}
	
	protected int getID() {
		return id;
	}
	
	public ArrayList<Vak> getVakken() {
		return vakken;
	}

	public void setVakken(ArrayList<Vak> vakken) {
		this.vakken = vakken;
	}

}
