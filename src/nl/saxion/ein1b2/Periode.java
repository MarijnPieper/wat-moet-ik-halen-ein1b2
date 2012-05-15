package nl.saxion.ein1b2;

public class Periode {
	private int id;
	private String naam;
	private CustomDate startdatum;
	private CustomDate einddatum;
	
	public Periode(String naam, CustomDate startDatum, CustomDate eindDatum){
		this.setNaam(naam);
		this.setStartdatum(startDatum);
		this.setEinddatum(eindDatum);
	}
	
	// Alleen gebruiken i.c.m. Database
	public Periode(int id, String naam, String startDatum, String eindDatum) {
		this.id = id;
		this.naam = naam;
		this.startdatum = convertStringtoDate(startDatum);
		this.einddatum = convertStringtoDate(eindDatum);
	}
	
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
	
	protected void setID(int ID) {
		this.id = ID;
	}
	
	protected int getID() {
		return id;
	}
}
