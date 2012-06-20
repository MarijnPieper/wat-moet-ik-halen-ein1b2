package nl.saxion.ein1b2;


public class Toets {
					
	private int id;
	private int toetstype_id;
	private String beschrijving;
	private CustomDate datumtijd;
	private double cijfer;
	private String vaknaam;
	private String toetstypenaam;
	private double tebehalencijfer;
	
	protected void setTebehalencijfer(double tebehalencijfer) {
		this.tebehalencijfer = tebehalencijfer;
	}
	
	protected double getTebehalencijfer() {
		return tebehalencijfer;
	}

	public Toets(String toetstypenaam, String beschrijving, CustomDate datum) {		
		setToetstypenaam(toetstypenaam);
		setBeschrijving(beschrijving);
		setDatumtijd(datum);
	}
	
	public Toets(int toetstype_id, String beschrijving, CustomDate datum) {
		setToetstype_id(toetstype_id);
		setBeschrijving(beschrijving);
		setDatumtijd(datum);
	}


	public Toets(int toetstype_id, String beschrijving, CustomDate datum, double cijfer) {		
		setToetstype_id(toetstype_id);
		setBeschrijving(beschrijving);
		setDatumtijd(datum);
		setCijfer(cijfer);
	}
	
	public Toets(int id, int toetstype_id, String beschrijving, CustomDate datum, double cijfer, String vaknaam) {		
		setId(id);
		setToetstype_id(toetstype_id);
		setBeschrijving(beschrijving);
		setDatumtijd(datum);
		setCijfer(cijfer);
		setVaknaam(vaknaam);
	}
	
	public Toets(CustomDate datum, String vakNaam, String toetsType) {
		setDatumtijd(datum);
		setVaknaam(vakNaam);
		setToetstypenaam(toetsType);
	}
	
	protected String getVaknaam() {
		return vaknaam;
	}

	protected void setVaknaam(String vaknaam) {
		this.vaknaam = vaknaam;
	}
	
	protected Toets(String beschrijving) {
		this.beschrijving = beschrijving;
	}

	protected int getToetstype_id() {
		return toetstype_id;
	}
	

	protected void setToetstype_id(int toetstype_id) {
		this.toetstype_id = toetstype_id;
	}

	protected String getBeschrijving() {
		return beschrijving;
	}

	protected int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected CustomDate getDatumtijd() {
		return datumtijd;
	}

	protected void setDatumtijd(CustomDate datumtijd) {
		this.datumtijd = datumtijd;
	}

	protected double getCijfer() {
		return cijfer;
	}

	protected void setCijfer(double cijfer) {
		this.cijfer = cijfer;
	}

	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}
	
	protected String getToetstypenaam() {
		return toetstypenaam;
	}


	protected void setToetstypenaam(String toetstypenaam) {
		this.toetstypenaam = toetstypenaam;
	}
}
