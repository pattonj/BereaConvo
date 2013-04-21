package com.jacob.patton.bereaconvo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class SwagActivity extends SherlockActivity {

	private Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swag_activity);
		// Finds the button and sets the action. 
		btn = (Button)findViewById(R.id.SignUp);
		btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// launches the swagbucks page with referral link. 
            	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.swagbucks.com/refer/jpatton"));
            	startActivity(browserIntent);
           	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Shows the custom icon in the action bar. 
		getSupportActionBar().setIcon(R.drawable.swagbucks_icon);
		return true;
	}

}
