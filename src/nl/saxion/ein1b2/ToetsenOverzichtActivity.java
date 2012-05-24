package nl.saxion.ein1b2;

import android.app.Activity;
import android.os.Bundle;

public class ToetsenOverzichtActivity extends Activity {

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.toetsenoverzicht);
		 Bundle extras = getIntent().getExtras();
		 int vakid = extras.getInt("vakid");
	 }
}
