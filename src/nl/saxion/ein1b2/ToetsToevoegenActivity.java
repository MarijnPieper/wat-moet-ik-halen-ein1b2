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
import android.widget.EditText;
import android.widget.Spinner;

public class ToetsToevoegenActivity extends Activity {

	private ArrayAdapter<Vak> vakAdapter;
	private ArrayAdapter<TypeToets> typeToetsAdapter;		
	private int currentItem=-1;
	private DbAdapter adapter;
	static final int STARTDATUM_DIALOG_ID = 0; 
	private CustomDate startDatum;
	private EditText txtStartDatum;
	
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
//		voegToetsToeButton.setOnClickListener(new VoegToetsToeOnClickListener());
		vakAdapter = new ArrayAdapter<Vak>(this, android.R.layout.simple_dropdown_item_1line, vakken);
		typeSpinner.setAdapter(vakAdapter);
		typeToetsAdapter = new ArrayAdapter<TypeToets>(this, android.R.layout.simple_dropdown_item_1line, types);
		vakSpinner.setAdapter(typeToetsAdapter);
		
		//Datum
		txtStartDatum = (EditText) findViewById(R.id.txtStartDatum);
		startDatum = new CustomDate();
		txtStartDatum.setText(startDatum.toString());
		
		
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


	
//	Wordt aan gewerkt!, Datum ed.
//	class VoegToetsToeOnClickListener implements OnClickListener {
//		
//	public void onClick(View v) {
//					ArrayList<TypeToets> types = adapter.selectTypeToetsen();
//					Spinner vakSpinner = (Spinner)findViewById(R.id.spinnerVakNaam);
//					Spinner typeSpinner = (Spinner)findViewById(R.id.spinnerToetsType);
//					Vak vak = (Vak)vakSpinner.getAdapter().getItem(vakSpinner.getSelectedItemPosition());
//					TypeToets typetoets = (TypeToets)typeSpinner.getAdapter().getItem(typeSpinner.getSelectedItemPosition());
//					Toets toets = new Toets(vak.getVakID(), typetoets.getToetsID() );
//					adapter.open();
//					adapter.insertToetsToevoegen(toets);
//					adapter.close();	
//					finish();	
//		}	
//	}

}
