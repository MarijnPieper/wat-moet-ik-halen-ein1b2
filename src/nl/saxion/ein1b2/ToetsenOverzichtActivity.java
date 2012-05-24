package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ToetsenOverzichtActivity extends Activity {
	private DbAdapter dbHelper;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.toetsenoverzicht);
		 Bundle extras = getIntent().getExtras();
		 int vakid = extras.getInt("vakid");
		 int periodeid = extras.getInt("periodeid");
		 
		 //Database connectie
		 dbHelper = new DbAdapter(this);
         dbHelper.open();
         ArrayList<Vak> vakken = dbHelper.selectVakken(periodeid);
         ArrayList<Toets> toetsen = dbHelper.selectToetsen(vakid);
         dbHelper.close();
		 
         //Spinner Vakken
		 Spinner sprVakken = (Spinner) findViewById(R.id.sprVakken);
		 ArrayAdapter<Vak> vakAdapter = new ArrayAdapter<Vak>(this, android.R.layout.simple_spinner_item, vakken);
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
		 
		 //Listview Toetsen
		 ListView lvwToetsen = (ListView) findViewById(R.id.lvwToetsen);
		 ToetsenOverzichtAdapter toetsAdapter = new ToetsenOverzichtAdapter(this, R.layout.toetsenoverzicht, toetsen, vak.getNaam());
		 lvwToetsen.setAdapter(toetsAdapter);
	 }
}
