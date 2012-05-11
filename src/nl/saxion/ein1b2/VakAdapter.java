package nl.saxion.ein1b2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class VakAdapter extends ArrayAdapter<Vak>{
	private LayoutInflater inflater;

	public VakAdapter(Context context,	int textViewResourceId, List<Vak> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.vakkenpakketadapter, null);
		}
		Vak vak = getItem(position);
		if (vak != null) {
			EditText vaknaam = (EditText) convertView.findViewById(R.id.txtVaknaam);
			vaknaam.setText(vak.getVak());			
		}
		
		return convertView;
	}

}