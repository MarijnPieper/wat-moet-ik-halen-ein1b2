package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityVakkenpakketToevoegen extends Activity implements OnClickListener {
	private ArrayList<Vak> arrVak;
	private EditText txtNaam;
	private EditText txtStartDatum;
	private EditText txtEindDatum;
	Button btnPakketToevoegen;
	private CustomDate startDatum;
	private CustomDate eindDatum;
	static final int STARTDATUM_DIALOG_ID = 0;  
	static final int EINDDATUM_DIALOG_ID = 1;  
	static final int ERROR_STARTDATUM_KLEINERDAN_EINDDATUM_DIALOG_ID = 2;  
	private long viewIdDialog = 0;
	private DbAdapter dbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vakkenpakket_layout);
        
        //Variable declareren
        arrVak = new ArrayList<Vak>();
        txtNaam = (EditText) findViewById(R.id.txtNaam);
		txtStartDatum = (EditText) findViewById(R.id.txtStartDatum);
		txtEindDatum = (EditText) findViewById(R.id.txtEindDatum);
		setListVakken();
		
        //Vakkenpakket toevoegen
        btnPakketToevoegen = (Button)findViewById(R.id.btnToevoegen);
        btnPakketToevoegen.setOnClickListener(this);
        btnPakketToevoegen.setEnabled(false);
        txtStartDatum.setOnFocusChangeListener(new showOnFocusDatum());
        txtStartDatum.setOnClickListener(new showOnClickDatum());
        txtEindDatum.setOnFocusChangeListener(new showOnFocusDatum());
        txtEindDatum.setOnClickListener(new showOnClickDatum());
        startDatum = new CustomDate();
        eindDatum = new CustomDate();
        eindDatum.add(CustomDate.DAY_OF_MONTH, 1);
        txtStartDatum.setText(startDatum.toString());
        txtEindDatum.setText(eindDatum.toString());
        
        vakToevoegenInstellen();       
        dbHelper = new DbAdapter(this);
	}
	
	public void setListVakken(){
		ListView listview = (ListView) findViewById(R.id.listView1);
		VakOverzichtAdapter adapter = new VakOverzichtAdapter(this, R.layout.vakkenpakketadapter, arrVak);
		listview.setAdapter(adapter);
	}
	

	public void onClick(View view) {		
		String naam = txtNaam.getText().toString();
				
		if (naam != null && !naam.equals("")
				&& startDatum != null
				&& eindDatum != null){
			Periode pakket = new Periode(naam, startDatum, eindDatum, arrVak);
			dbHelper.open();
			dbHelper.insertVakkenpakket(pakket);
			dbHelper.close();	
			this.finish();
		}
		
	}
	
	
	private void vakToevoegenInstellen(){
		//Vak toevoegen
        Button btnAddvak = (Button) findViewById(R.id.btnAddVak);
        btnAddvak.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(ActivityVakkenpakketToevoegen.this);
				alert.setTitle("Vak toevoegen");
				alert.setMessage("Voer hieronder de naam van het vak:");
				final EditText input = new EditText(ActivityVakkenpakketToevoegen.this);
				alert.setView(input);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						String vaknaam = input.getText().toString();
						if (vaknaam != null && !vaknaam.equals("")){
							//TODO iscijfer toevoegen
							Vak vak = new Vak(vaknaam, true);
							arrVak.add(vak);
							btnPakketToevoegen.setEnabled(true);
							setListVakken();
						}
						return;						
					}
				});
				alert.setNegativeButton("Annuleren", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						return;						
					}
				});
				alert.show();
			}
		});
	}
	
	
    protected Dialog onCreateDialog(int id) {  
        switch (id) {  
        case STARTDATUM_DIALOG_ID:  
        	return new DatePickerDialog(this, new setDatumListener(), startDatum.get(GregorianCalendar.YEAR), startDatum.get(GregorianCalendar.MONTH), startDatum.get(GregorianCalendar.DAY_OF_MONTH));
        case EINDDATUM_DIALOG_ID:  
        	return new DatePickerDialog(this, new setDatumListener(), eindDatum.get(GregorianCalendar.YEAR), eindDatum.get(GregorianCalendar.MONTH), eindDatum.get(GregorianCalendar.DAY_OF_MONTH));
        case ERROR_STARTDATUM_KLEINERDAN_EINDDATUM_DIALOG_ID:
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("De eind datum moet later zijn dan de start datum.");
        	builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {				
				public void onClick(DialogInterface dialog, int which) {
					// niets
				}
			});
            builder.setCancelable(false);
        	AlertDialog alert = builder.create();
     		return alert;
        }
        
        return null;  
    }  
	
	class setDatumListener implements OnDateSetListener{

		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			CustomDate tmpDate = new CustomDate(dayOfMonth, monthOfYear, year);
			
			if (viewIdDialog == txtStartDatum.getId()){
				if (tmpDate.before(eindDatum) || tmpDate.equals(eindDatum)) {
					startDatum.set(year, monthOfYear, dayOfMonth);
					txtStartDatum.setText(startDatum.toString());
				} else { showDialog(ERROR_STARTDATUM_KLEINERDAN_EINDDATUM_DIALOG_ID); }				
			}
			else {
				if (startDatum.before(tmpDate) || startDatum.equals(tmpDate)) {
					eindDatum.set(year, monthOfYear, dayOfMonth);
					txtEindDatum.setText(eindDatum.toString());
				} else { showDialog(ERROR_STARTDATUM_KLEINERDAN_EINDDATUM_DIALOG_ID); }
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
				else {
						showDialog(EINDDATUM_DIALOG_ID);
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
			else {
					showDialog(EINDDATUM_DIALOG_ID);
			}
		}
		
	}
}

