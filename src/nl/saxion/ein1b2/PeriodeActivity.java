package nl.saxion.ein1b2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PeriodeActivity extends Activity {
   private PeriodesAdapter adapter;
   private ArrayList<Periode> periodes;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vakkenpakket_layout);
        
//        ListView l = (ListView) findViewById(R.id.listViewPeriodes);
//        periodes = new ArrayList<Periode>();
//        periodes.add(new Periode("Kwartiel 1", new GregorianCalendar(2012,1,1), new GregorianCalendar(2012,4,4)));
//        periodes.add(new Periode("Kwartiel 2", new GregorianCalendar(2012,1,1), new GregorianCalendar(2012,4,4)));
//        adapter = new PeriodesAdapter(this, R.layout.periodeadapter, periodes);
//       
//        l.setAdapter(adapter);
//        l.setOnItemClickListener(new ClickListener());
    }
    
    class ClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
		}	
    }
    
}