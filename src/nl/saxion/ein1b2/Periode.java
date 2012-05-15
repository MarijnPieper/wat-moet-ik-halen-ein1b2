package nl.saxion.ein1b2;


public class Periode {
	private String naam;
	private CustomDate startDatum;
	private CustomDate eindDatum;
	
	public Periode(String naam, CustomDate startDatum, CustomDate eindDatum){
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
	
	
}
