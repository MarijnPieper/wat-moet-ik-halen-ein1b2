package nl.saxion.ein1b2;

public class Vak {
	private String vakNaam;
	private double cijfer;
	private double doelCijfer;
	
	public Vak(String vakNaam, double cijfer, double doelCijfer) {
		this.setVakNaam(vakNaam);
		this.setCijfer(cijfer);
		this.setDoelCijfer(doelCijfer);
	}
	
	public String getVakNaam() {
		return vakNaam;
	}
	public void setVakNaam(String vakNaam) {
		this.vakNaam = vakNaam;
	}
	
	public double getCijfer() {
		return cijfer;
	}
	public void setCijfer(double cijfer) {
		this.cijfer = cijfer;
	}
	
	public double getDoelCijfer() {
		return doelCijfer;
	}
	public void setDoelCijfer(double doelCijfer) {
		this.doelCijfer = doelCijfer;
	}
}
