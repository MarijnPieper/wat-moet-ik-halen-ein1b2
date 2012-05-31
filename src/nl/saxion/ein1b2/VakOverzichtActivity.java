package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
    	setContentView(R.layout.vakken);
    	Button btnTerug = (Button) findViewById(R.id.buttonTerugPeriode);
    	btnTerug.setOnClickListener(new ClickListener());
    	
    	db = new DbAdapter(this);
    	
    	Bundle b = getIntent().getExtras();
    	if (b != null) {
    		nID = b.getInt("ID");
    		Log.w("pID", Integer.toString(nID));
    	}
    	
    	checkFirstTime();
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
			
 			for (Periode periode : Periodes) {
 				if (curDate.after(periode.getStartDatum()) && curDate.before(periode.getEindDatum())
 						|| curDate.equals(periode.getStartDatum()) 
						|| curDate.equals(periode.getEindDatum()) ) {
 					this.nID = periode.getID();
 					break;
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
       	
       	Button voegToetsToe = (Button)findViewById(R.id.buttonVoegToetsToe);
		voegToetsToe.setOnClickListener(new ClickListener());
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

	public class ClickListener implements OnClickListener {

		public void onClick(View v) {
			if (v.getId() == R.id.buttonToetsToevoegen) {
				startToetsToevoegen();
			}
			else {
				startPeriodeOverzicht();
			}
		}
	}
}
