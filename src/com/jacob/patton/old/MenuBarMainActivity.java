package com.jacob.patton.old;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jacob.patton.bereaconvo.R;


//import com.actionbarsherlock.app.SherlockActivity;



import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.widget.TextView;



public class MenuBarMainActivity extends SherlockActivity {
	 
	public boolean onCreateOptionsMenu(Menu menu) {
		
        menu.add("Fall")
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add("Spring")
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
        menu.add("Settings")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        menu.add("About")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        return true;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_article);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
	}

	
	
    	
    	
    

}
