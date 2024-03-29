package nl.saxion.ein1b2;

import java.util.ArrayList;

public class Vak {
	private String naam;
	private int vakID;
	private boolean iscijfer;
	private double doelCijfer;
	private ArrayList<Toets> toetsen;
	
	public Vak(String vakNaam, boolean iscijfer) {
		this.setNaam(vakNaam);
		this.setIscijfer(iscijfer);
	}
	
	public Vak(String naam, int vakID) {
		super();
		setNaam(naam);
		setVakID(vakID);
		setToetsen(toetsen);
	}

	protected ArrayList<Toets> getToetsen() {
		return toetsen;
	}

	protected void setToetsen(ArrayList<Toets> toetsen) {
		this.toetsen = toetsen;
	}
	
	protected void setToets(Toets toets){
		if (this.toetsen == null){
			this.toetsen = new ArrayList<Toets>();
		}
		this.toetsen.add(toets);
	}

	public Vak(String vakNaam, int vakID, double doelCijfer) {
		this.setVakID(vakID);
		this.setNaam(vakNaam);
		this.setDoelCijfer(doelCijfer);
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

	public int getVakID() {
		return vakID;
	}

	public void setVakID(int vakID) {
		this.vakID = vakID;
	}
}
