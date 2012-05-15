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

	private ArrayList<Vak> vakkenlijst = new ArrayList<Vak>();
	private ArrayAdapter<Vak> vakAdapter;	
	private Vak vak;
	private int currentItem=-1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toets_toevoegen);

		Spinner vakSpinner = (Spinner)findViewById(R.id.spinnerVakNaam);
		Spinner typeSpinner = (Spinner)findViewById(R.id.spinnerToetsType);
		Spinner reminderSpinner = (Spinner)findViewById(R.id.spinnerReminder);

		vakAdapter = new ArrayAdapter<Vak>(this, android.R.layout.simple_dropdown_item_1line, vakkenlijst);
		vakSpinner.setAdapter(vakAdapter);
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
