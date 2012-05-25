package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	private int nID;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.vakken);
    	
    	db = new DbAdapter(this);	
    }
    
    // Check of er al een Periode bestaat, zo niet Wizard starten.
 	private void checkFirstTime() {
 		db = new DbAdapter(this);
 		db.open();
 		Periodes = new ArrayList<Periode>();
 		Periodes = db.selectVakkenpakketten();
 		db.close();
 		
 		if (!Periodes.isEmpty()){
 			GregorianCalendar curDate = new GregorianCalendar();
 			
 			for (Periode periode : Periodes) {
 				if (curDate.after(periode.getStartDatum()) && curDate.before(periode.getEindDatum())) {
 					this.nID = periode.getID();
 					initVakOverzicht();
 				}
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
		voegToetsToe.setOnClickListener(new ToetsToevoegenClickListener());
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

	public class ToetsToevoegenClickListener implements OnClickListener {

		public void onClick(View v) {
			startToetsToevoegen();
		}
	}
}
