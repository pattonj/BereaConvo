package com.jacob.patton.bereaconvo;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;

/*
 * Change this to a fragment class using article fragment and title fragment
 * 
 */


public class MainActivity extends SherlockFragmentActivity {
	
	
	 
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
			// set content view
			setContentView(R.layout.fragment_activity_main);
		
	        // configure the SlidingMenu
	        SlidingMenu menu = new SlidingMenu(this);
	        menu.setMode(SlidingMenu.LEFT);
	        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	        menu.setShadowWidthRes(R.dimen.shadow_width);
	        menu.setShadowDrawable(R.drawable.shadow);
	        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
	        menu.setFadeDegree(0.35f);
	        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
	        menu.setMenu(R.layout.sidemenu);
	        
	        
	        //inserting fragments
	        
	    }	

	/*
	 * EXAMPLE SUBROUTINES
	 * 
	 * Determine if Master arraylists exist. 
	 * 		If not (create master list){
	 *         	inside master list(){
	 * 				Semester
	 * 				Date				
	 * 				time 				
	 * 				Description
	 *      }
	 * 			}
	 * 	
	 * 
	 * 
	 * Create Semester Array list (int semester){
	 * 			if 1 = fall
	 * 			if 2 = spring
	 * 		create a copy of master with only ones that are fall or spring
	 * }
	 * 
	 * Create Time Array List(int timestamp){ // time as in morning, afternoon, or evening
	 * 		create a copy of semester arraylist with only matching timestamp
	 * 			if (timestamp = 1)
	 * 				copy all
	 * }
	 * 
	 * Update arraylist display {
	 * 		info list fragment (update - create the buttons using info from arraylist).
	 * 			on press display convo information fragment passing the number it is (ie 2)  
	 *  
	 *}
	 *
	 * 
	 * 
	 * 
	 */
    

}
