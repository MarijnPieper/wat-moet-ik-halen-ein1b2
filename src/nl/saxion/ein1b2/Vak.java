package nl.saxion.ein1b2;

public class Vak {
	private String naam;
	private boolean iscijfer;
	private double doelCijfer;
	
	public Vak(String vakNaam, boolean iscijfer) {
		this.setNaam(vakNaam);
		this.setIscijfer(iscijfer);
	}
	
	public Vak(String vakNaam, double doelCijfer) {
		this.setNaam(vakNaam);
		this.setDoelCijfer(doelCijfer);
	}
	
	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public boolean isIscijfer() {
		return iscijfer;
	}

	public void setIscijfer(boolean iscijfer) {
		this.iscijfer = iscijfer;
	}

	public double getDoelCijfer() {
		return doelCijfer;
	}
	public void setDoelCijfer(double doelCijfer) {
		this.doelCijfer = doelCijfer;
	}
}
