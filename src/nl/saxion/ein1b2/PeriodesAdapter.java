package nl.saxion.ein1b2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PeriodesAdapter extends ArrayAdapter<Periode>  {
	private LayoutInflater inflater;
	private DbAdapter db;
	
	public PeriodesAdapter(Context context, int textViewResourceId,
			List<Periode> items) {
		super(context, textViewResourceId, items);
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.periodeadapter, null);
		}
		Periode p = getItem(position);
		if (p != null) {
			TextView periode = (TextView) convertView.findViewById(R.id.Periode);
			periode.setText(p.getNaam());
		}
		
		return convertView;
	}
}
