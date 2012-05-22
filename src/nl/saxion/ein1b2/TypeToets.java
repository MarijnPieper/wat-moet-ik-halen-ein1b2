package nl.saxion.ein1b2;

public class TypeToets {
	private String naam;
	private int toetsID;
	
	public TypeToets(String naam, int toetsID) {
		this.naam = naam;
		this.setToetsID(toetsID);
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public int getToetsID() {
		return toetsID;
	}

	public void setToetsID(int toetsID) {
		this.toetsID = toetsID;
	}
}
