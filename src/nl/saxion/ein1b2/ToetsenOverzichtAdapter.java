package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ToetsenOverzichtAdapter extends ArrayAdapter<Toets>{
	private LayoutInflater inflater;
	private DbAdapter db;
	private final double MININALECIJFER = 5.5;	
	private ArrayList<TypeToets> typeToetsen;	
	private double bijvolgendCijfer = 0;
	
	
	public ToetsenOverzichtAdapter(Context context,	int textViewResourceId, List<Toets> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		db = new DbAdapter(context);
		
		db.open();
		typeToetsen = db.selectTypeToetsen();
		db.close();	
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.toetsoverzichtadapter, null);
		}
		Toets toets = getItem(position);
		if (toets != null) {
			TextView vak = (TextView) convertView.findViewById(R.id.lblVak);			
			TextView soort = (TextView) convertView.findViewById(R.id.lblSoort);
			TextView cijfer = (TextView) convertView.findViewById(R.id.lblCijfer);
			TextView datumtijd = (TextView) convertView.findViewById(R.id.lblDatumTijd);
			TextView beschrijving = (TextView) convertView.findViewById(R.id.lblBeschrijving);
			TextView doelcijfer = (TextView) convertView.findViewById(R.id.lblDoelcijfer);
			
			vak.setText(toets.getVaknaam());	
				
			String typetoetsNaam = "";
			TypeToets typetoets = new TypeToets(0, "", 0);
			for (TypeToets type : typeToetsen){
				if (type.getToetsID() == toets.getToetstype_id()) typetoets = type;
			}
			typetoetsNaam = typetoets.getNaam();
			soort.setText(typetoetsNaam);
			
			CustomDate tmpDate = new CustomDate();
			if (toets.getDatumtijd().before(tmpDate)) {
				cijfer.setText(Double.toString(toets.getCijfer()));
				doelcijfer.setText("");
			}
			else {
				cijfer.setText("");
				//Wat te halen voor het minimale cijfer
				if (position == 0) {
					db.open();
					Double teBehalen = db.selectMinCijferVak(toets.getId(), MININALECIJFER, typetoets.getSom());
					db.close();
					if (teBehalen.isNaN() || teBehalen.isInfinite()) doelcijfer.setText("Minimaal: N.V.T.");
					else if (teBehalen < 0) doelcijfer.setText("Minimaal:" + Double.toString(0));
					else if (teBehalen > 10){
						//Te laag gemiddelde, waardoor het volgende cijfer hoger moet zijn dan een 10, 
						// dus nu moet het verdeeld worden.			
						double nuBijCijfer = 10 - MININALECIJFER;	
						bijvolgendCijfer = (teBehalen - MININALECIJFER);
						if (!teBehalen.equals(Double.NaN)) {
							doelcijfer.setText("Minimaal:" + Double.toString(teBehalen));
						}
						else {
							doelcijfer.setText("Minimaal: -");
						}
					}
					else {
						doelcijfer.setText("Minimaal: -");
					}
				} else {
					if (bijvolgendCijfer <= (10 - MININALECIJFER)) {
						doelcijfer.setText("Minimaal:" + Double.toString(MININALECIJFER + bijvolgendCijfer));
						bijvolgendCijfer = 0;
					}
					else {
						double nuBijCijfer = 10 - MININALECIJFER;						
						bijvolgendCijfer = bijvolgendCijfer - (10 - MININALECIJFER);
						doelcijfer.setText("Minimaal:" + Double.toString(MININALECIJFER + nuBijCijfer));
					}
				}
			}
			
			datumtijd.setText(toets.getDatumtijd().toStringDatumTijd());
			beschrijving.setText(toets.getBeschrijving());
			
			
		}
		
		return convertView;
	}
}