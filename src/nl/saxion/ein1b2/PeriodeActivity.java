package nl.saxion.ein1b2;

import java.util.ArrayList;

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
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * @author Tom
 *
 */
public class PeriodeActivity extends Activity implements OnItemLongClickListener {
   private PeriodesAdapter adapter;
   private DbAdapter dbHelper;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.periodes);
         
        ImageButton Plus = (ImageButton) findViewById(R.id.PeriodeAddVak);

        Plus.setOnClickListener(new ClickListener());
                
        dbHelper = new DbAdapter(this);
        
        ListView l = (ListView) findViewById(R.id.listViewPeriodes);
        l.setOnItemClickListener(new ItemClickListener());
        l.setOnItemLongClickListener(this);
        
        FillPeriodes();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	FillPeriodes();
    }
    
    private void FillPeriodes() {
    	ArrayList<Periode> periodes = new ArrayList<Periode>();
    	
    	ListView l = (ListView) findViewById(R.id.listViewPeriodes);
    	
    	periodes = new ArrayList<Periode>();
    	
        dbHelper.open();
        periodes = dbHelper.selectVakkenpakketten();
        dbHelper.close();
        
        adapter = new PeriodesAdapter(this, R.layout.periodeadapter, periodes);
        
        l.setAdapter(adapter);
    }
    
    private void startPeriodeToevoegen() {
    	Intent i = new Intent(this,  ActivityVakkenpakketToevoegen.class);
		startActivity(i);
    }
    
    private void startVakOverzicht(int pID) {
    	Intent i = new Intent(this, VakOverzichtActivity.class);
    	i.putExtra("ID", pID);
    	startActivity(i);
    }
    
    class ClickListener implements OnClickListener {
    	
		public void onClick(View v) {
			ImageButton btnToevoegen = (ImageButton) findViewById(R.id.PeriodeAddVak);
			
			if (v.getId() == btnToevoegen.getId()) {
		    	startPeriodeToevoegen();
			}
		}
    }
    
    class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Periode periode = (Periode)parent.getAdapter().getItem(position);
			startVakOverzicht(periode.getID());
		}	
    }

	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Periode verwijderen");
		alert.setMessage("Weet u zeker dat u deze periode wil verwijderen?");
		
		final Periode periode = (Periode) parent.getAdapter().getItem(position);

		alert.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dbHelper.open();
				dbHelper.deleteVakkenpakket(periode);
				dbHelper.close();
				
				FillPeriodes();
				return;						
			}
		});
		alert.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				return;						
			}
		});
		alert.show();
		
		return false;
		
	}
}