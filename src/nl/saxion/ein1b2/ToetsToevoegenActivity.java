package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class ToetsToevoegenActivity extends Activity {

	private ArrayAdapter<Vak> vakAdapter;
	private ArrayAdapter<TypeToets> typeToetsAdapter;		
	private int currentItem=-1;
	private DbAdapter adapter;
	static final int STARTDATUM_DIALOG_ID = 0;
	static final int STARTTIJD_DIALOG_ID = 1;
	private CustomDate startDatum;
	private EditText txtStartDatum;
	private EditText txtStartTijd;
	private long viewIdDialog = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toets_toevoegen);
		Bundle extras = getIntent().getExtras();
		int periodeid = extras.getInt("periodeid");

		adapter = new DbAdapter(this);
		adapter.open();
		ArrayList<TypeToets> types = adapter.selectTypeToetsen();
		ArrayList<Vak> vakken = adapter.selectVakken(periodeid);
		adapter.close();
		Spinner vakSpinner = (Spinner)findViewById(R.id.spinnerVakNaam);
		Spinner typeSpinner = (Spinner)findViewById(R.id.spinnerToetsType);
		Button voegToetsToeButton = (Button)findViewById(R.id.buttonToetsToevoegen);
		voegToetsToeButton.setOnClickListener(new VoegToetsToeOnClickListener());
		vakAdapter = new ArrayAdapter<Vak>(this, android.R.layout.simple_spinner_item, vakken);
		vakSpinner.setAdapter(vakAdapter);
		typeToetsAdapter = new ArrayAdapter<TypeToets>(this, android.R.layout.simple_spinner_item, types);
		typeSpinner.setAdapter(typeToetsAdapter);

		//Datum
		txtStartDatum = (EditText) findViewById(R.id.txtStartDatum);
		startDatum = new CustomDate();
		txtStartDatum.setText(startDatum.toString());
		txtStartDatum.setOnFocusChangeListener(new showOnFocusDatum());
		txtStartDatum.setOnClickListener(new showOnClickDatum());

		//Tijd		
		txtStartTijd = (EditText)findViewById(R.id.txtStartTijd);
		txtStartTijd.setText(startDatum.getTimeAsString());
		txtStartTijd.setOnFocusChangeListener(new showOnFocusTijd());
		txtStartTijd.setOnClickListener(new showOnClickDatum());

	}

	class MySpinnerListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			currentItem = arg2;
			Vak vak = vakAdapter.getItem(currentItem);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}




	class VoegToetsToeOnClickListener implements OnClickListener {
		public void onClick(View v) {
			
			Spinner vakSpinner = (Spinner)findViewById(R.id.spinnerVakNaam);
			Spinner typeSpinner = (Spinner)findViewById(R.id.spinnerToetsType);
			EditText cijfertxt = (EditText)findViewById(R.id.EditTextCijfer);
			double cijfer = 0;
			if (!cijfertxt.getText().toString().equals("")) cijfer = Double.parseDouble(cijfertxt.getText().toString());
			Vak vak = (Vak)vakSpinner.getAdapter().getItem(vakSpinner.getSelectedItemPosition());
			TypeToets typetoets = (TypeToets)typeSpinner.getAdapter().getItem(typeSpinner.getSelectedItemPosition()); 

			//TODO beschrijving toevoegen			
			if (cijfer != 0){
				Toets toets = new Toets(typetoets.getToetsID(), "", startDatum, cijfer);
				adapter.open();
				adapter.insertToetsToevoegen(toets, vak.getVakID());
				adapter.close();	
				finish();
			}
			else {
				Toets toets = new Toets(typetoets.getToetsID(), "", startDatum);
				adapter.open();
				adapter.insertToetsToevoegen(toets, vak.getVakID());
				adapter.close();	
				finish();
			}
			
		}	
	}

	//Datum methodes


	protected Dialog onCreateDialog(int id) {  
		switch (id) {  
		case STARTDATUM_DIALOG_ID:  
			return new DatePickerDialog(this, new setDatumListener(), startDatum.get(GregorianCalendar.YEAR), startDatum.get(GregorianCalendar.MONTH), startDatum.get(GregorianCalendar.DAY_OF_MONTH));
		case STARTTIJD_DIALOG_ID:
			return new TimePickerDialog(this, new setTimeListener(), startDatum.get(GregorianCalendar.HOUR), startDatum.get(GregorianCalendar.MINUTE), true);
		}; return null;  

	}
	class setDatumListener implements OnDateSetListener{

		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			if (viewIdDialog == txtStartDatum.getId()){				
				startDatum.set(year, monthOfYear, dayOfMonth);
				txtStartDatum.setText(startDatum.toString());			
			}
		}
	}

	class showOnFocusDatum implements OnFocusChangeListener{

		public void onFocusChange(View view, boolean hasFocus) {	
			viewIdDialog = view.getId();
			if (hasFocus){
				if (view.getId() == txtStartDatum.getId()){
					showDialog(STARTDATUM_DIALOG_ID);
				}

			}
		}
	}

	class showOnClickDatum implements OnClickListener{

		public void onClick(View view) {
			viewIdDialog = view.getId();
			if (view.getId() == txtStartDatum.getId()){
				showDialog(STARTDATUM_DIALOG_ID);
			}
		}
	}

	//Tijd		
	class showOnFocusTijd implements OnFocusChangeListener{

		public void onFocusChange(View view, boolean hasFocus) {	
			viewIdDialog = view.getId();
			if (hasFocus){
				if (view.getId() == txtStartTijd.getId()){
					showDialog(STARTTIJD_DIALOG_ID);
				}

			}
		}
	}

	class showOnClickTijd implements OnClickListener{

		public void onClick(View view) {
			viewIdDialog = view.getId();
			if (view.getId() == txtStartDatum.getId()){
				showDialog(STARTTIJD_DIALOG_ID);
			}
		}
	}

	class setTimeListener implements OnTimeSetListener{

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			if (viewIdDialog == txtStartTijd.getId()){						
				startDatum.setTime(hourOfDay, minute);
				txtStartTijd.setText(startDatum.getTimeAsString());
			}
		}
	}

}

