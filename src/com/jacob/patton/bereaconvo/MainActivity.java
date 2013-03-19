package com.jacob.patton.bereaconvo;

import java.util.ArrayList;

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
	
	public ArrayList<String[]> database;
	public ArrayList<String[]> dbFall;
	public ArrayList<String[]> dbSpring;
	 
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

	
	public void createMaster(){
		// creates database list 
		// Currently this is random, later this will be where the parse goes. 
		// This is used instead of two separate semester lists as to make parsing data easier down the road.
		// The array is as follows [semester, date,time, description]
		 database = new ArrayList<String[]>();
			for(int i=0; i<5; i++){
				database.add(new String[]{"Fall","i/8/12","3:00pm","This is a description of the data that will be used"});
				database.add(new String[]{"Spring","i/2/13","3:00pm","This is a description of the data that will be used"});
			}
			for(int i=0; i<2; i++){
				database.add(new String[]{"Fall","i/2/13","8:00pm","This is a description of the data that will be used"});
				database.add(new String[]{"Spring","i/2/14","8:00pm","This is a description of the data that will be used"});
			}
			for(int i=0; i<2; i++){
				database.add(new String[]{"Fall","i/2/13","6:00pm","This is a description of the data that will be used"});
				database.add(new String[]{"Spring","i/2/14","6:00pm","This is a description of the data that will be used"});
			}
	}
	
	
	
	public void createSemesters(){
		// Both semester databases have been declared at the start, so initialize them here.  
		dbFall = new ArrayList<String[]>();
		dbSpring = new ArrayList<String[]>();
		
		// loop it the number of times for the size of the database. 
		for(int i = 0; i< database.size(); i++){
			// if the first array value is fall, add to fall database. 
			if(database.get(i)[0].equals("Fall")){
				dbFall.add(database.get(i));
			}
			// Check to see if it's spring, and add it to spring. 
			else if(database.get(i)[0].equals("Spring")){
				dbSpring.add(database.get(i));
			}
			else{
				// it's misspelled or an error so ignore. 
			}
			
		}
		
	}
	
	
	/*
	 * EXAMPLE SUBROUTINES
	 * 
	 * Determine if Master arraylists exist. -- added  
	 * 		If not (create master list){
	 * 			
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
	 * Create Semester Array list (int semester){ -- added. 
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
