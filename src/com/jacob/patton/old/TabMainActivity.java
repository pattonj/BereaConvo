package com.jacob.patton.old;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.jacob.patton.bereaconvo.R;
//import com.actionbarsherlock.app.SherlockActivity;



import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;



public class TabMainActivity extends SherlockActivity implements ActionBar.TabListener {
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_article);
		// I think this allows for tabs
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		
		//Create tab 1
		ActionBar.Tab tab1 = getSupportActionBar().newTab();
		tab1.setText("Fall");
		tab1.setTabListener(this);
		getSupportActionBar().addTab(tab1);
		
		//Create tab 2
		ActionBar.Tab tab2 = getSupportActionBar().newTab();
		tab2.setText("Spring");
		tab2.setTabListener(this);
		getSupportActionBar().addTab(tab2);
		
		
		
	}

	
	// The following three are required for the ActionBar.TabListener
	// Just list different possibilities with Java GUI actions. 
 	@Override
    public void onTabReselected(Tab tab, FragmentTransaction transaction) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction transaction) {
    	
    	if(tab.getText()== "Fall"){
    		TextView textbox = (TextView)findViewById(R.id.ConvoDate);
       	 textbox.setText("Fall 2012 Convos!");
    	}
    	
    	if(tab.getText()== "Spring"){
    		TextView textbox = (TextView)findViewById(R.id.ConvoDate);
       	 textbox.setText("Spring 2013 Convos!");
    	}
    	
    	
    }

    @Override
    public void onTabUnselected(Tab tab1, FragmentTransaction transaction) {
    }

}
