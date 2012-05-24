package nl.saxion.ein1b2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VakOverzichtAdapter extends ArrayAdapter<Vak>{
	private LayoutInflater inflater;
	private DbAdapter db;
	private Context context;

	public VakOverzichtAdapter(Context context,	int textViewResourceId, List<Vak> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.vakadapter, null);
		}
		Vak vak = getItem(position);
		if (vak != null) {
			TextView vaknaam = (TextView) convertView.findViewById(R.id.VakNaam);
			vaknaam.setText(vak.getNaam());
			
			TextView cijfer = (TextView) convertView.findViewById(R.id.VakCijfer);
			
			db = new DbAdapter(context);
			db.open();
			Double huidigCijfer = db.selectGemCijferVak(vak.getVakID());
			db.close();
			
			if (huidigCijfer.equals(Double.NaN)){
				cijfer.setText("-");
			}
			else {
				cijfer.setText(Double.toString(huidigCijfer));
			}
		}
		
		return convertView;
	}
}
