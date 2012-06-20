package nl.saxion.ein1b2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
				if (type.getToetsID() == toets.getToetstype_id()) {
					typetoets = type;
					break;
				}
			}
			typetoetsNaam = typetoets.getNaam();
			soort.setText(typetoetsNaam);
			
			CustomDate tmpDate = new CustomDate();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        	Date date1 = null;
//        	Date date2 = null;
//			try {
//				date1 = sdf.parse(toets.getDatumtijd().toStringForDB());
//				date2 = sdf.parse(tmpDate.toStringForDB());
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        	
//        	Calendar cal1 = Calendar.getInstance();
//        	Calendar cal2 = Calendar.getInstance();
//        	cal1.setTime(date1);
//        	cal2.setTime(date2);
			
			
			
			//Als de toets verleden is:
			//TODO data's vergelijking controleren
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
					else if (teBehalen <= 10) doelcijfer.setText("Minimaal:" + teBehalen);
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