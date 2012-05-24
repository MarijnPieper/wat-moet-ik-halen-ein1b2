package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class VakOverzichtActivity extends Activity {
	private ArrayList<Vak> vakken;	
	private DbAdapter db;
	private VakOverzichtAdapter adapter;
	private ArrayList<Periode> Periodes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vakken);
		checkFirstTime();
	}
	
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
					//Intent i = new Intent(this,VakOverzichtActivity.class);
					//i.putExtra("ID", periode.getID());
					//startActivity(i);
				}
			}
			
			Intent i = new Intent(this, PeriodeActivity.class);
			startActivity(i);
		}
	}
	
	private void initOverzicht(int nID) {
		vakken = new ArrayList<Vak>();

		db.open();
		vakken = db.selectVakken(nID);
		db.close();

		ListView lvVakken = (ListView) findViewById(R.id.lvVakken);
		adapter = new VakOverzichtAdapter(this, R.layout.vakadapter, vakken);
		lvVakken.setAdapter(adapter);

		Button voegToetsToe = (Button)findViewById(R.id.buttonVoegToetsToe);
		voegToetsToe.setOnClickListener(new ToetsToevoegenClickListener());
	}

	private void startToetsToevoegen() {
		Intent i = new Intent(this, ToetsToevoegenActivity.class);
		startActivity(i);
	}

	public class ToetsToevoegenClickListener implements OnClickListener {

		public void onClick(View v) {
			startToetsToevoegen();
		}
	}
}