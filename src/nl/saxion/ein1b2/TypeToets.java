package nl.saxion.ein1b2;

public class TypeToets {
	private String naam;
	private int toetsID;
	int som;
	
	public TypeToets(int id, String naam, int som) {
		setNaam(naam);
		setToetsID(id);
		setSom(som);
	}

	public int getSom() {
		return som;
	}

	public void setSom(int som) {
		this.som = som;
	}

	@Override
	public String toString(){
		return naam;
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
