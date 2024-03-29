package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

public class VakToevoegenActivity extends Activity {
	private int nID;
	private ArrayList<Vak> vakken;
	private DbAdapter db;
	private VakOverzichtAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
