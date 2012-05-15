package nl.saxion.ein1b2;

public class Periode {
	private int id;
	private String naam;
	private CustomDate startDatum;
	private CustomDate eindDatum;
	
	public Periode(String naam, CustomDate startDatum, CustomDate eindDatum){
		this.setNaam(naam);
		this.setStartDatum(startDatum);
		this.setEindDatum(eindDatum);
	}
	
	// Alleen gebruiken i.c.m. Database
	public Periode(int id, String naam, String startDatum, String eindDatum) {
		this.setID(id);
		this.setNaam(naam);
		this.setStartDatum(convertStringtoDate(startDatum));
		this.setEindDatum(convertStringtoDate(eindDatum));
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
}
