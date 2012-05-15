
package nl.saxion.ein1b2;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WatmoetikhalenActivity extends Activity {
    /** Called when the activity is first created. */
	private DbAdapter dbHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Toets overzicht layout, geen vakkenpakket
        //setContentView(R.layout.main);
        startVakkenpakketToevoegenActivity();
        
        dbHelper = new DbAdapter(this);
        dbHelper.open();
        dbHelper.close();
    }
    
    private void startVakkenpakketToevoegenActivity(){
    	Intent i = new Intent(this, ActivityVakkenpakketToevoegen.class);
		startActivity(i);
    }
    
    

}