package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import nl.saxion.ein1b2.ActivityVakkenpakketToevoegen.setDatumListener;
import nl.saxion.ein1b2.ActivityVakkenpakketToevoegen.showOnClickDatum;
import nl.saxion.ein1b2.ActivityVakkenpakketToevoegen.showOnFocusDatum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
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
	static final int STARTTIJD_DIALOG_ID = 0;
	private CustomDate startDatum;
	private Time startTijd;
	private EditText txtStartDatum;
	private EditText txtStartTijd;
	private long viewIdDialog = 0;
	
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
		voegToetsToeButton.setOnClickListener(new VoegToetsToeOnClickListener());
		vakAdapter = new ArrayAdapter<Vak>(this, android.R.layout.simple_dropdown_item_1line, vakken);
		typeSpinner.setAdapter(vakAdapter);
		typeToetsAdapter = new ArrayAdapter<TypeToets>(this, android.R.layout.simple_dropdown_item_1line, types);
		vakSpinner.setAdapter(typeToetsAdapter);

		//Datum
		txtStartDatum = (EditText) findViewById(R.id.txtStartDatum);
		startDatum = new CustomDate();
		txtStartDatum.setText(startDatum.toString());
		txtStartDatum.setOnFocusChangeListener(new showOnFocusDatum());
		txtStartDatum.setOnClickListener(new showOnClickDatum());
		
		//Tijd
		
//		txtStartTijd = (EditText)findViewById(R.id.txtStartTijd);
		
				
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




	class VoegToetsToeOnClickListener implements OnClickListener {
		String datum = txtStartDatum.getText().toString();
		public void onClick(View v) {
			ArrayList<TypeToets> types = adapter.selectTypeToetsen();
			Spinner vakSpinner = (Spinner)findViewById(R.id.spinnerVakNaam);
			Spinner typeSpinner = (Spinner)findViewById(R.id.spinnerToetsType);
			
			Vak vak = (Vak)vakSpinner.getAdapter().getItem(vakSpinner.getSelectedItemPosition());
			TypeToets typetoets = (TypeToets)typeSpinner.getAdapter().getItem(typeSpinner.getSelectedItemPosition()); 
//			Toets toets = new Toets(vak.getVakID(), typetoets.getToetsID(), convertStringtoDate(datum));
			adapter.open();
//			adapter.insertToetsToevoegen(toets);
			adapter.close();	
			finish();	
		}	
	}

	//Datum methodes

	private CustomDate convertStringtoDate(String Date ) {
		

		String[] splitDate = Date.split("-");
		String[] splitDay = splitDate[2].split(" ");
		CustomDate newDate = new CustomDate(Integer.parseInt(splitDay[0]), Integer.parseInt(splitDate[1]),Integer.parseInt(splitDate[0]));
		return newDate;
	}

	protected Dialog onCreateDialog(int id) {  
		switch (id) {  
		case STARTDATUM_DIALOG_ID:  
			return new DatePickerDialog(this, new setDatumListener(), startDatum.get(GregorianCalendar.YEAR), startDatum.get(GregorianCalendar.MONTH), startDatum.get(GregorianCalendar.DAY_OF_MONTH));
		}; return null;  
		
	}
		class setDatumListener implements OnDateSetListener{

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				CustomDate tmpDate = new CustomDate(dayOfMonth, monthOfYear, year);

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
		
		protected Dialog onCreateDialogTijd(int id) {  
			switch (id) {  
			case STARTTIJD_DIALOG_ID:  

			}; return null;  
			
		}
		
		class setTimeListener implements OnTimeSetListener{

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				CustomDate tmpDate = new CustomDate(dayOfMonth, monthOfYear, year);

				if (viewIdDialog == txtStartTijd.getId()){				
					
					txtStartDatum.setText(startDatum.toString());			
				}
			}

			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				
			}
		}
		
		class showOnClickTime implements OnClickListener{

			public void onClick(View view) {
				viewIdDialog = view.getId();
				if (view.getId() == txtStartTijd.getId()){
					showDialog(STARTTIJD_DIALOG_ID);
				}
			}
		}
}

