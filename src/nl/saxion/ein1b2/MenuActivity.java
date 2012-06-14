package nl.saxion.ein1b2;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ImageButton;



public class MenuActivity extends Activity {


	private DbAdapter db;
	private ArrayList<Periode> periodes;
	private Toets toets;
	private int nID = 0;
	private TextView vakNaam;
	private TextView toetsType;
	private TextView datumTijd;
	private ImageButton periodeButton;
	private ImageButton vakOverzichtButton;
	private ImageButton toetsOverzichtButton;




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu);

// 		Geeft de buttons in het menu de juiste onclickListeners

		periodeButton = (ImageButton)findViewById(R.id.btnPeriodes);
		periodeButton.setOnClickListener(new StartPeriodenActivity());
		
		vakOverzichtButton = (ImageButton)findViewById(R.id.btnVakken);
		vakOverzichtButton.setOnClickListener(new StartVakkenOverzichtActivity());
		
		toetsOverzichtButton = (ImageButton)findViewById(R.id.btnToetsen);
		toetsOverzichtButton.setOnClickListener(new startToetsenOverzichtActivity());
		
		vulAankomendeToets();
		checkFirstTime();
		
	}

	// Check of er al een Periode bestaat, zo niet Wizard starten.
	private void checkFirstTime() {

		db = new DbAdapter(this);
		db.open();
		periodes = new ArrayList<Periode>();
		periodes = db.selectVakkenpakketten();
		db.close();

		if (periodes.isEmpty()) {
			Intent i = new Intent(this, WizardActivity.class); 
			startActivity(i);
		} 
		else {
			CustomDate curDate = new CustomDate();

			if (this.nID == 0){
				for (Periode periode : periodes) {
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
				// TODO : Scherm vullen met eerste toets
			}
		}
	}
	
	private void vulAankomendeToets() { 
		vakNaam = (TextView)findViewById(R.id.lblMenuVakNaam);
		toetsType = (TextView)findViewById(R.id.lblMenuType);
		datumTijd = (TextView)findViewById(R.id.lblMenuDatum);
		CustomDate curDate = new CustomDate();
		db = new DbAdapter(this);
		db.open();
		periodes = new ArrayList<Periode>();
		periodes = db.selectVakkenpakketten();
		db.close();
		if (this.nID == 0){
			for (Periode periode : periodes) {
				if (curDate.after(periode.getStartDatum()) && curDate.before(periode.getEindDatum())
						|| curDate.equals(periode.getStartDatum()) 
						|| curDate.equals(periode.getEindDatum()) ) {
					this.nID = periode.getID();
					break;
				}
			}
		}
		db.open();
		toets = db.selectAankomendeToets(nID);
		vakNaam.setText(toets.getBeschrijving());
		toetsType.setText(toets.getToetstypenaam());
		datumTijd.setText(toets.getDatumtijd().toStringDatumTijd());
		db.close();

	}
	



	public class StartPeriodenActivity implements OnClickListener {

		public void onClick(View v) {
			Intent i = new Intent(MenuActivity.this, PeriodeActivity.class);
			startActivity(i);
		}	
	}

	public class StartVakkenOverzichtActivity implements OnClickListener {

		public void onClick(View v) {
			Intent i = new Intent(MenuActivity.this, VakOverzichtActivity.class);
			startActivity(i);		
		}	
	}

	public class startToetsenOverzichtActivity implements OnClickListener {

		public void onClick(View v) {
			Intent i = new Intent(MenuActivity.this, ToetsenOverzichtActivity.class);
			i.putExtra("periodeid", nID);
			startActivity(i);	
		}

	}


}


