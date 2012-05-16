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
    	setContentView(R.layout.vakken);
    	
    	vakken = new ArrayList<Vak>();
    	Bundle b = getIntent().getBundleExtra("ID");
    	nID = b.getInt("ID");

    	vakken = db.selectVakken(nID);
    	
    	ListView lvVakken = (ListView) findViewById(R.id.lvVakken);
       	adapter = new VakOverzichtAdapter(this, R.layout.vakadapter, vakken);
       	lvVakken.setAdapter(adapter);
    }
}
