package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.Comparator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class ToetsenOverzichtActivity extends Activity implements OnCheckedChangeListener, OnItemSelectedListener, OnItemClickListener, OnItemLongClickListener {
	private DbAdapter dbHelper;
	RadioButton rbnAankomend;
	RadioButton rbnGeschiedenis;
	ToetsenOverzichtAdapter toetsAdapter;
	int vakid;
	int periodeid;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 setContentView(R.layout.toetsenoverzicht);
		 Bundle extras = getIntent().getExtras();
		 vakid = extras.getInt("vakid");
		 periodeid = extras.getInt("periodeid");
		 
		 
		 //Database connectie
		 dbHelper = new DbAdapter(this);
         dbHelper.open();
         ArrayList<Vak> vakken = dbHelper.selectVakken(periodeid);
         ArrayList<Toets> toetsen = dbHelper.selectToetsen(vakid, periodeid, true, false);
         dbHelper.close();
		 
         //Spinner Vakken
		 Spinner sprVakken = (Spinner) findViewById(R.id.sprVakken);
		 sprVakken.setOnItemSelectedListener(this);
		 ArrayAdapter<Vak> vakAdapter = new ArrayAdapter<Vak>(this, android.R.layout.simple_spinner_item, vakken);
		 Vak vakAlles = new Vak("Alles", true);
		 vakAdapter.add(vakAlles);
		 vakAdapter.sort(new Comparator<Vak>() {
			public int compare(Vak vak1, Vak vak2) {
				if (vak1.getNaam().equals("Alles")){
					return -1;
				} else if (vak2.getNaam().equals("Alles")){
					return 1;
				} else {
					return vak1.getNaam().compareTo(vak2.getNaam());
				}
			}
			 
		});		 
		 Vak vak = null;
		 for (Vak tmpVak : vakken){
			 if (tmpVak.getVakID() == vakid){
				 vak = tmpVak;
				 break;
			 }
		 }
		 int thisVak = vakAdapter.getPosition(vak);
		 sprVakken.setAdapter(vakAdapter);		 
		 sprVakken.setSelection(thisVak);
		 
		 ImageButton btnToetsToevoegen = (ImageButton)findViewById(R.id.btnVoegToetsToe);
		 btnToetsToevoegen.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(ToetsenOverzichtActivity.this, ToetsToevoegenActivity.class);
				i.putExtra("periodeid", periodeid);
				startActivity(i);				
			}
		});
		 
		 //Radiobuttons
		 RadioGroup rgbTijd = (RadioGroup) findViewById(R.id.rgbTijd);
		 rbnAankomend = (RadioButton) findViewById(R.id.rbnAankomend);
		 rbnGeschiedenis = (RadioButton) findViewById(R.id.rbnGeschiedenis);
		 rgbTijd.setOnCheckedChangeListener(this);
		 
		 //Listview Toetsen
		 ListView lvwToetsen = (ListView) findViewById(R.id.lvwToetsen);
		 toetsAdapter = new ToetsenOverzichtAdapter(this, R.layout.toetsenoverzicht, toetsen);
		 lvwToetsen.setAdapter(toetsAdapter);
		 lvwToetsen.setOnItemClickListener(this);		 
	 }
	 

	 //Radiobuttons aankomend en geschiedenis
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		ArrayList<Toets> toetsen;
		dbHelper.open();
		if (checkedId == rbnAankomend.getId()){
			toetsen = dbHelper.selectToetsen(vakid, periodeid, true, false);
		}
		else {
			toetsen = dbHelper.selectToetsen(vakid, periodeid, false, true);
		}
		dbHelper.close();
		toetsAdapter.clear();
		toetsAdapter.addAll(toetsen);		
	}

	// Spinner voor vak
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Vak vak = (Vak) parent.getItemAtPosition(position);
		vakid = vak.getVakID();
		toetsAdapter.clear();
		dbHelper.open();			
		toetsAdapter.addAll(dbHelper.selectToetsen(vakid, periodeid, true, false));
		dbHelper.close();
		rbnAankomend.setChecked(true);
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ListView lvwToetsen = (ListView) findViewById(R.id.lvwToetsen);
		final Toets t = (Toets) lvwToetsen.getAdapter().getItem(position);
		Double curCijfer = t.getCijfer();
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		if (curCijfer.equals(0.0)) {
			alert.setTitle("Cijfer toevoegen");
		}
		else {
			alert.setTitle("Cijfer wijzigen");
		}
		
		alert.setMessage("Voer hieronder het cijfer in:");
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				t.setCijfer(Double.parseDouble(input.getText().toString()));
				dbHelper.open();
				dbHelper.updateCijferToets(t);
				dbHelper.close();
				
				rbnAankomend = (RadioButton) findViewById(R.id.rbnAankomend);
				rbnGeschiedenis = (RadioButton) findViewById(R.id.rbnGeschiedenis);
				ArrayList<Toets> toetsen;
				dbHelper.open();
				if (rbnAankomend.isChecked()){
					toetsen = dbHelper.selectToetsen(vakid, periodeid, true, false);
				}
				else {
					toetsen = dbHelper.selectToetsen(vakid, periodeid, false, true);
				}
				dbHelper.close();
				toetsAdapter.clear();
				toetsAdapter.addAll(toetsen);	
				
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


	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		
		
		
		return false;
	}
}
