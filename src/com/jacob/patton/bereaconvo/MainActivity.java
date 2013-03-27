package com.jacob.patton.bereaconvo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.slidingmenu.lib.SlidingMenu;

/*
 * Change this to a fragment class using article fragment and title fragment
 * 
 */


public class MainActivity extends SherlockFragmentActivity 
		implements MenuFragment.onArticleSelected{
	
	//Master database
	public List<String[]> database;
	// needs to be a final public string that changes when the tab is pushed. 
	public String semester = "fall"; 
	
	//the database to be displayed. 
	public List<String[]> dbDisplay;
	
	int id =0;
	

	// creates the side sliding menu. 
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
			setContentView(R.layout.main_fragment_activity);
		
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
	        // creating master database. 
	       createMaster();
	    }	

	/**
	 * This creates the master list (database) of convocations in list that contains Arrays. 
	 * [ID,semester, date,time, Title, description]
	 */
	private void createMaster(){
		// creates master database list 
		// Currently this is random, later this will be where the parse goes. 
		// This is used instead of two separate semester lists as to make parsing data easier down the road.
		// The array is as follows [ID,semester, date,time, Title, description]
		 database = new ArrayList<String[]>();
			for(int i=0; i<5; i++){
				database.add(new String[]{""+i,"Fall",i+"/8/12","3:00pm","fall convo "+i ,"This is a description of the data that will be used"});
				database.add(new String[]{""+i+5,"Spring",i+"/2/13","3:00pm","spring convo "+i, "This is a description of the data that will be used"});
			}
			for(int i=0; i<2; i++){
				database.add(new String[]{""+i+10,"Fall",i+"/2/13","8:00pm","fall convo "+i,"This is a description of the data that will be used"});
				database.add(new String[]{""+i+12,"Spring",i+"/2/14","8:00pm","Spring convo "+i,"This is a description of the data that will be used"});
			}
			for(int i=0; i<2; i++){
				database.add(new String[]{""+i+14,"Fall",i+"/2/13","6:00pm","fall convo "+i,"This is a description of the data that will be used"});
				database.add(new String[]{""+i+16,"Spring",i+"/2/14","6:00pm","spring convo "+i,"This is a description of the data that will be used"});
			}
	}
	
	/**
	 * This sorts the data depending on the number sent from sliding menu.  
	 * Used the semester variable to decide which ones to add.     
	 *0=all, 1=afternoon, 2=evening, 3=special
	 *
	 */	
	public void sortData(int number){
		
		
		// overwrites the old display every time it runs. 
		dbDisplay = new ArrayList<String[]>();
		
		// if 0, copy the current semester to dbDisplay
		if(number == 0){
			//run through the database add it to the display.  
			for(int i = 0; i< database.size(); i++){
				//if(database.get(i)[0].equals(semester)){
					dbDisplay.add(database.get(i));
				//}
			}
		}
		
		// if 1, check for afternoon convos
		else if(number == 1){
			//run through the database 
			for(int i = 0; i< database.size(); i++){
				//get the first character from the time slot. 
				char time = database.get(i)[2].charAt(0);
				// if that is equal to 3, add it to the display list. 
				if((time == '3')&& database.get(i)[0].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
		}
		
		// if 2, check for evening convos
		else if(number == 2){
			//run through the database 
			for(int i = 0; i< database.size(); i++){
				//get the first character from the time slot. 
				char time = database.get(i)[2].charAt(0);
				// if that is equal to 8, add it to the display list. 
				if((time == '8')&& database.get(i)[0].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
		}
		
		// if 3, check for special convos
		else if(number == 3){
			//run through the database 
			for(int i = 0; i< database.size(); i++){
				//get the first character from the time slot. 
				char time = database.get(i)[2].charAt(0);
				// if that is not equal to 3 or 8, add it to the display list. 
				if((time !='3')&&(time !='8')&& database.get(i)[0].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
		}
		
		
		
	}
	/**
	 * This is used to pass the proper array over to
	 * the Article fragment which then updates the text.
	 * @return
	 */
	public void displayArticleData(){
		
		// this find the ArticleFragment frame and give it a variable name. 
		ArticleFragment articleFrag = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_frame);
		// currently used for testing to send different convos to the ArticleFramgent. 
		id++;
		// used to activate the updateArticle class from the ArticleFragment Class
		
		
		createMaster();
		sortData(0);
		articleFrag.updateArticle(dbDisplay.get(id));
		//articleFrag.updateArticle();
		return ;
		// Later add if == null, then we are in the phone view. 
		
	}
	
	
	
	/*
	 * To Do:
	 * 
	 * Main Activity - 
	 * Need to set a check to see if it's the first time or if the database does not exist. 
	 * If it doesn't, it needs to create it. 
	 * 
	 *  Also, Final values need to be set (for permenant storage)  
	 *  method for setting the semester.   
	 * 
	 * Later!
	 * Add the ability to mark convos as attended, this means creating an ID # (DONE) and checkmark value to the array. 
	 * (This would mean shifting the location for the arrays for certain things.)
	 * (Also, when updated with completely new convos it would need to reset). 
	 * (if only partial update, it would need to keep the convos attended).  
	 * 
	 */
	
}
