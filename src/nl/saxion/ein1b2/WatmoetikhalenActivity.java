package nl.saxion.ein1b2;

import android.app.Activity;
import android.os.Bundle;

public class WatmoetikhalenActivity extends Activity {
    /** Called when the activity is first created. */
	private DbAdapter dbHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.vakkenpakket_layout);
        
        
        
        
    
    	
    
=======
        setContentView(R.layout.main);
        
        dbHelper = new DbAdapter(this);
        dbHelper.open();
>>>>>>> 70c927c13df78cd60e14faa11d6f8d4ea0eb3ec0
    }
}