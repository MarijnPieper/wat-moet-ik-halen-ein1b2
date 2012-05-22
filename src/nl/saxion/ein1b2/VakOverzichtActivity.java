package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class VakOverzichtActivity extends Activity {
	private int nID;
	private ArrayList<Vak> vakken;	
	private DbAdapter db;
	private VakOverzichtAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.vakken);
    	
    	db = new DbAdapter(this);
    	
    	vakken = new ArrayList<Vak>();
    	Bundle b = getIntent().getExtras();
    	nID = b.getInt("ID");
    	
    	db.open();
    	vakken = db.selectVakken(nID);
    	db.close();
    	
    	ListView lvVakken = (ListView) findViewById(R.id.lvVakken);
       	adapter = new VakOverzichtAdapter(this, R.layout.vakadapter, vakken);
       	lvVakken.setAdapter(adapter);
    }
}
