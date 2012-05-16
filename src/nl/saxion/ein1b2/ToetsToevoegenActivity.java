package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ToetsToevoegenActivity extends Activity {

	private ArrayAdapter<Vak> vakAdapter;
	private ArrayAdapter<TypeToets> typeToetsAdapter;	
	private int currentItem=-1;
	private DbAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toets_toevoegen);
		
		adapter = new DbAdapter(this);
		
		adapter.open();
		ArrayList<TypeToets> types = adapter.selectTypeToetsen();
		ArrayList<Vak> vakken = adapter.selectVakken(1);
		adapter.close();
		
		Spinner vakSpinner = (Spinner)findViewById(R.id.spinnerVakNaam);
		Spinner typeSpinner = (Spinner)findViewById(R.id.spinnerToetsType);
		
		vakAdapter = new ArrayAdapter<Vak>(this, android.R.layout.simple_dropdown_item_1line, vakken);
		typeSpinner.setAdapter(vakAdapter);
		
		typeToetsAdapter = new ArrayAdapter<TypeToets>(this, android.R.layout.simple_dropdown_item_1line, types);
		vakSpinner.setAdapter(typeToetsAdapter);
	}

	class MySpinnerListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			currentItem = arg2;
			Vak vak = vakAdapter.getItem(currentItem);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	}
}
