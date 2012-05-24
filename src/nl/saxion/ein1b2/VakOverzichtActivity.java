package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VakOverzichtActivity extends Activity implements OnItemClickListener  {
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
       	lvVakken.setOnItemClickListener(this);
       	lvVakken.setAdapter(adapter);
    }

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Vak vak = (Vak) parent.getItemAtPosition(position);
		Intent i = new Intent(this, ToetsenOverzichtActivity.class);
		i.putExtra("vakid", vak.getVakID());
		startActivity(i);
	}

}
