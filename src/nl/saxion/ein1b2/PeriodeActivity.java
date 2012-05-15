package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class PeriodeActivity extends Activity {
   private PeriodesAdapter adapter;
   private DbAdapter dbHelper;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.periodes);
         
        Button Plus = (Button) findViewById(R.id.PeriodeAddVak);
        Plus.setOnClickListener(new ClickListener());
                
        dbHelper = new DbAdapter(this);
        FillPeriodes();
    }
    
    private void FillPeriodes(){
    	ArrayList<Periode> periodes = new ArrayList<Periode>();
    	
    	ListView l = (ListView) findViewById(R.id.listViewPeriodes);
    	
    	periodes = new ArrayList<Periode>();
    	
        dbHelper.open();
        periodes = dbHelper.selectVakkenpakketten();
        dbHelper.close();
        
        adapter = new PeriodesAdapter(this, R.layout.periodeadapter, periodes);
        
        l.setAdapter(adapter);
        l.setOnItemClickListener(new ItemClickListener());
    }
    
    private void startPeriodeToevoegen() {
    	Intent i = new Intent(this, ActivityVakkenpakketToevoegen.class);
		startActivity(i);
    }
    
    class ClickListener implements OnClickListener {
    	
		public void onClick(View v) {
			Button btnToevoegen = (Button) findViewById(R.id.PeriodeAddVak);
			
			if (v.getId() == btnToevoegen.getId()) {
		    	startPeriodeToevoegen();
			}
		}
    	
    }
    
    class ItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
		}	
    }  
}