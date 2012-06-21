package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class VakOverzichtActivity extends Activity implements OnItemClickListener  {
	private ArrayList<Vak> vakken;	
	private DbAdapter db;
	private VakOverzichtAdapter adapter;
	private ArrayList<Periode> Periodes;
	private int nID = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	setContentView(R.layout.vakken);
    	ImageButton btnTerug = (ImageButton) findViewById(R.id.buttonTerugPeriode);
    	btnTerug.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startPeriodeOverzicht();
			}
		});
    	
    	db = new DbAdapter(this);
    	
    	Bundle b = getIntent().getExtras();
    	if (b != null) {
    		nID = b.getInt("ID");
    		Log.w("pID", Integer.toString(nID));
    	}
    	
    	checkFirstTime();
    	
    	ImageButton btnVakToevoegen = (ImageButton)findViewById(R.id.btnVoegVakToe);
    	btnVakToevoegen.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
					AlertDialog.Builder alert = new AlertDialog.Builder(VakOverzichtActivity.this);
					
					alert.setTitle("Vak toevoegen");
					alert.setMessage("Voer hieronder de naam van het vak:");
					final EditText input = new EditText(VakOverzichtActivity.this);
					alert.setView(input);
					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							String vaknaam = input.getText().toString();
							vakToevoegen(vaknaam);
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
    	ImageButton voegToetsToe = (ImageButton)findViewById(R.id.buttonVoegToetsToe);
		voegToetsToe.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startToetsToevoegen();
			}
		});
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	initVakOverzicht();
    }
 
    // Check of er al een Periode bestaat, zo niet Wizard starten.
 	private void checkFirstTime() {
 		db.open();
 		Periodes = new ArrayList<Periode>();
 		Periodes = db.selectVakkenpakketten();
 		db.close();
 			
		if (Periodes.isEmpty()) {
			Intent i = new Intent(this, WizardActivity.class); 
			startActivity(i);
		} 
		else {
			CustomDate curDate = new CustomDate();
			
			if (this.nID == 0){
	 			for (Periode periode : Periodes) {
	 				if (curDate.after(periode.getStartDatum()) && curDate.before(periode.getEindDatum())
	 						|| curDate.equals(periode.getStartDatum()) 
							|| curDate.equals(periode.getEindDatum()) ) {
	 					this.nID = periode.getID();
	 					break;
	 				}
	 			}
			}
 			
 			if (nID == 0) {
 				Intent i = new Intent(this, PeriodeActivity.class);
 				startActivity(i);
 			}
 			else {
 				initVakOverzicht();
 			}
		}
 	}
 	
 	private void initVakOverzicht() {
 		vakken = new ArrayList<Vak>();
 		db.open();
    	vakken = db.selectVakken(nID);
    	db.close();
    	
    	ListView lvVakken = (ListView) findViewById(R.id.lvVakken);
       	adapter = new VakOverzichtAdapter(this, R.layout.vakadapter, vakken);
       	lvVakken.setOnItemClickListener(this);
       	lvVakken.setAdapter(adapter);
       	
       	
 	}

	private void vakToevoegen(String vaknaam){
		if (vaknaam != null && !vaknaam.equals("")){
			Vak vak = new Vak(vaknaam, true);
			db.open();
	    	db.insertVak(nID, vak);
	    	db.close();
	    	initVakOverzicht();
		}
		return;	
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Vak vak = (Vak) parent.getItemAtPosition(position);
		Intent i = new Intent(this, ToetsenOverzichtActivity.class);
		i.putExtra("vakid", vak.getVakID());
		i.putExtra("periodeid", nID);
		startActivity(i);
	}

	private void startToetsToevoegen() {
		Intent i = new Intent(this, ToetsToevoegenActivity.class);
		i.putExtra("periodeid", nID);
		startActivity(i);
	}
	
	private void startPeriodeOverzicht() {
		Intent i = new Intent(this, PeriodeActivity.class);
		startActivity(i);
	}

	
}
