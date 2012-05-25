package nl.saxion.ein1b2;

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
	private Context context;
	private String vaknaam;

	public ToetsenOverzichtAdapter(Context context,	int textViewResourceId, List<Toets> objects, String vaknaam) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.vaknaam = vaknaam;
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
			
			vak.setText(toets.getVaknaam());
			db = new DbAdapter(context);
			db.open();
			String typetoetsNaam = db.selectTypeToets(toets.getToetstype_id());
			db.close();
			soort.setText(typetoetsNaam);
			cijfer.setText(Double.toString(toets.getCijfer()));
			datumtijd.setText(toets.getDatumtijd().toString());
			beschrijving.setText(toets.getBeschrijving());
			
		}
		
		return convertView;
	}
}
