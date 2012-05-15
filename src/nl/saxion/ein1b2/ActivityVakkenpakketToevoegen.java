package nl.saxion.ein1b2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityVakkenpakketToevoegen extends Activity {
	private ArrayList<Vak> arrVak;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vakkenpakket_layout);
        
        
        
        //Vak toevoegen
        Button addvak = (Button) findViewById(R.id.btnAddVak);
        addvak.setOnClickListener(new OnClickListener()  {
			
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(ActivityVakkenpakketToevoegen.this);
				alert.setTitle("");
				alert.setMessage("");
				final EditText input = new EditText(ActivityVakkenpakketToevoegen.this);
				alert.setView(input);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						String value = input.getText().toString();
						if (value != null && value.equals("")){
							//TODO: toevoegen aan vak en aan list
							
						}
						return;						
					}
				});
				alert.setNegativeButton("Annuleren", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						return;						
					}
				});
				alert.show();
			}
		});
	}
	
	
}
