package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class ToetsenOverzichtActivity extends Activity implements OnCheckedChangeListener, OnItemSelectedListener {
	private DbAdapter dbHelper;
	RadioButton rbnAankomend;
	RadioButton rbnGeschiedenis;
	ToetsenOverzichtAdapter toetsAdapter;
	int vakid;
	
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.toetsenoverzicht);
		 Bundle extras = getIntent().getExtras();
		 vakid = extras.getInt("vakid");
		 int periodeid = extras.getInt("periodeid");
		 
		 //Database connectie
		 dbHelper = new DbAdapter(this);
         dbHelper.open();
         ArrayList<Vak> vakken = dbHelper.selectVakken(periodeid);
         ArrayList<Toets> toetsen = dbHelper.selectToetsen(vakid, true, false);
         //Collections.sort(toetsen, new CompareToets());
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
		 
		 //Radiobuttons
		 RadioGroup rgbTijd = (RadioGroup) findViewById(R.id.rgbTijd);
		 rbnAankomend = (RadioButton) findViewById(R.id.rbnAankomend);
		 rbnGeschiedenis = (RadioButton) findViewById(R.id.rbnGeschiedenis);
		 rgbTijd.setOnCheckedChangeListener(this);
		 
		 //Listview Toetsen
		 ListView lvwToetsen = (ListView) findViewById(R.id.lvwToetsen);
		 toetsAdapter = new ToetsenOverzichtAdapter(this, R.layout.toetsenoverzicht, toetsen);
		 lvwToetsen.setAdapter(toetsAdapter);
		 
	 }

	 //Radiobuttons aankomend en geschiedenis
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		ArrayList<Toets> toetsen;
		dbHelper.open();
		if (checkedId == rbnAankomend.getId()){
			toetsen = dbHelper.selectToetsen(vakid, true, false);
		}
		else {
			toetsen = dbHelper.selectToetsen(vakid, false, true);
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
		toetsAdapter.addAll(dbHelper.selectToetsen(vakid, true, false));
		dbHelper.close();
		rbnAankomend.setChecked(true);
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
