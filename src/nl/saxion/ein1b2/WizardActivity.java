package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WizardActivity extends Activity {
	private DbAdapter db;
	ArrayList<Periode> Periodes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		checkFirstTime();
		
		setContentView(R.layout.wizard1);
		
		Button btnVolgende = (Button) findViewById(R.id.btnWizardVolgende);
		btnVolgende.setOnClickListener(new ClickListener());
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
					Intent i = new Intent(this,VakOverzichtActivity.class);
					i.putExtra("ID", periode.getID());
					startActivity(i);
				}
			}
			
			Intent i = new Intent(this, PeriodeActivity.class);
			startActivity(i);
		}
	}

	private void volgendePagina() {
		Intent i = new Intent(this, ActivityVakkenpakketToevoegen.class);
		startActivity(i);
	}
	
	class ClickListener implements OnClickListener {

		public void onClick(View v) {
			volgendePagina();
		}
	}
}
