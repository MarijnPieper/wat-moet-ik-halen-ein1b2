package nl.saxion.ein1b2;

import android.app.Activity;
import android.os.Bundle;

public class WatmoetikhalenActivity extends Activity {
    /** Called when the activity is first created. */
	private DbAdapter dbHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dbHelper = new DbAdapter(this);
        dbHelper.open();
    }
}