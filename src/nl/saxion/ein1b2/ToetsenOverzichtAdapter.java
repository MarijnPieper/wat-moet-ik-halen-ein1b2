package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ToetsenOverzichtAdapter extends ArrayAdapter<Toets>{
	private LayoutInflater inflater;
	private DbAdapter db;
	private final double MININALECIJFER = 5.5;	
	private ArrayList<Toets> tebehalenToetsen;
	
	
	public ToetsenOverzichtAdapter(Context context,	int textViewResourceId, List<Toets> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		db = new DbAdapter(context);
		
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
			
			db.open();
			TypeToets typetoets = db.selectTypeToets(toets.getToetstype_id());
			db.close();
			
			soort.setText(typetoets.getNaam());

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
					Double teBehalen = db.selectMinCijferVak(toets, MININALECIJFER, new ArrayList<Toets>());
					db.close();
					if (teBehalen.isNaN() || teBehalen.isInfinite()) doelcijfer.setText("Minimaal: N.V.T.");
					else if (teBehalen <= 0) {
						doelcijfer.setText("Minimaal: 0");
						tebehalenToetsen = new ArrayList<Toets>();
						toets.setTebehalencijfer(0);
						tebehalenToetsen.add(toets);
					}
					else if (teBehalen <= 10) doelcijfer.setText("Minimaal:" + teBehalen);
					else if (teBehalen > 10){
						doelcijfer.setText("Minimaal: 10");
						tebehalenToetsen = new ArrayList<Toets>();
						toets.setTebehalencijfer(10);
						tebehalenToetsen.add(toets);
					}
				} else {
					db.open();
					Double teBehalen = db.selectMinCijferVak(toets, MININALECIJFER, tebehalenToetsen);
					db.close();
					if (teBehalen <= 0) {
						toets.setTebehalencijfer(0);
						doelcijfer.setText("Minimaal: 0");
					}
					else if (teBehalen <= 10) {
						toets.setTebehalencijfer(teBehalen);
						doelcijfer.setText("Minimaal: " + teBehalen);
					}
					else { //(teBehalen > 10)
						doelcijfer.setText("Minimaal: 10");
						toets.setTebehalencijfer(10);
					}
					tebehalenToetsen.add(toets);
					//doelcijfer.setText("Minimaal:" + teBehalen);
				}
			}
			
			datumtijd.setText(toets.getDatumtijd().toStringDatumTijd());
			beschrijving.setText(toets.getBeschrijving());
			
			
		}
		
		return convertView;
	}
}