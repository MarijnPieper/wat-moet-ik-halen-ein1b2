package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
		Button voegToetsToeButton = (Button)findViewById(R.id.buttonToetsToevoegen);
		voegToetsToeButton.setOnClickListener(new MyOnClickListener());
		vakAdapter = new ArrayAdapter<Vak>(this, android.R.layout.simple_dropdown_item_1line, vakken);
		typeSpinner.setAdapter(vakAdapter);
		
		typeToetsAdapter = new ArrayAdapter<TypeToets>(this, android.R.layout.simple_dropdown_item_1line, types);
		vakSpinner.setAdapter(typeToetsAdapter);
		
		
	}
	
	public void finish()	{
		this.finish();
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

//	Wordt nog aan gewerkt, niet werkend...
	class MyOnClickListener implements OnClickListener {
//
		public void onClick(View v) {
//			
//					Toets toets = new Toets(vak_id, toetstype_id, beschrijving, datum);
//					adapter.open();
//					adapter.insertToetsToevoegen(toets);
//					adapter.close();	
//					finish();	
		}	
	}

}
