package com.jacob.patton.bereaconvo;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
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
	public String semester = "Fall"; 
	
	//needs to be a final string that keeps track of how to sort the data
	public int sortID = 0;
	
	//the database to be displayed. 
	public List<String[]> dbDisplay;
	
	public SlidingMenu menu;
	//sets the buttons variable for the side buttons. 
	public Button allButton ;
    public Button afternoonButton;
    public Button eveningButton ;
    public Button specialButton ;
    
    //a variable to keep track of if the side menu should be allowed to show. 
    public boolean showmenu = true;
    public boolean smallLayout = false;
    
	// creates the side sliding menu. 
	public boolean onCreateOptionsMenu(Menu menu) {

		//This enables the back/up arrow in the top left corner, which lets the user know there is a menu. 
		// The display arrow drawable is set via style. (See the slidingmenu list for an example.) 
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		//sets the semester. 
		menu.add(semester)
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
			
			// if we find the id for small layout
			// note, this takes a second  to find. - maybe improve in the future.
		if(findViewById(R.id.article_frame_small)!= null){
				// find article fragment
				ArticleFragment articleFrag = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_frame_small);
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				smallLayout = true;
				//hide fragment and commit the change.  
				ft.hide(articleFrag);
				ft.commit(); 
				
			}
			
	        // configure the SlidingMenu
			menu = new SlidingMenu(this);
	        menu.setMode(SlidingMenu.LEFT);
	        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	        menu.setShadowWidthRes(R.dimen.shadow_width);
	        menu.setShadowDrawable(R.drawable.shadow);
	        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
	        menu.setFadeDegree(0.35f);
	        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
	        menu.setMenu(R.layout.sidemenu);
	        
	        // Creates the data
	        createMaster();
		     //sorts the data. 
		     sortData();
		       
	        // creates the side buttons. 
	         allButton = (Button) menu.findViewById(R.id.ALL);
	         afternoonButton = (Button) menu.findViewById(R.id.AFTERNOON);
	         eveningButton = (Button) menu.findViewById(R.id.EVENING);
	         specialButton = (Button) menu.findViewById(R.id.SPECIAL);
	         
	         // sets the side button actions. 
	         allButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 sortID = 0;
	            	 sortData();
	            	 
	            	 
	             }
	         });
	        
	         
	         
	         afternoonButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 sortID = 1;
	            	 sortData();
	            	 
	            	 
	            	 
	             }
	         });
	         
	         eveningButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 sortID = 2;
	            	 sortData();
	            	 //Should it automatically close the sliding menu? 
	            	 menu.showContent();
	            	 
	            	 
	             }
	         });
	         
	         specialButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 sortID = 3;
	            	 sortData();
	            	 menu.showContent();
	            	 
	            	 
	             }
	         });
	         
	        
	        
	        

	              
	    }	


	/**
	 * This creates the master list (database) of convocations in list that contains Arrays. 
	 * [ID,semester, date,time, Title, description]
	 */
	private void createMaster(){
		// creates master database list 
		// Currently this is random, later this will be where the parse goes. 
		// this could be set via a class as well. 
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
			for(int i=0; i<3; i++){
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
	public void sortData(){
		
		
		// overwrites the old display every time it runs. 
		dbDisplay = new ArrayList<String[]>();
		
		switch (sortID){
		
		case 0:// if 0, copy the current semester to dbDisplay
			//run through the database add it to the display.  
			for(int i = 0; i< database.size(); i++){
				if(database.get(i)[1].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
			break;
			
		case 1:// if 1, check for afternoon convos
			//run through the database 
			for(int i = 0; i< database.size(); i++){
				//get the first character from the time slot. 
				char time = database.get(i)[3].charAt(0);
				// if that is equal to 3, add it to the display list. 
				if((time == '3')&& database.get(i)[1].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
			break;
		case 2:	// if 2, check for evening convos
			//run through the database 
			for(int i = 0; i< database.size(); i++){
				//get the first character from the time slot. 
				char time = database.get(i)[3].charAt(0);
				// if that is equal to 8, add it to the display list. 
				if((time == '8')&& database.get(i)[1].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
			break;
			
		case 3: // if 3, check for special convos
			//run through the database 
			for(int i = 0; i< database.size(); i++){
				//get the first character from the time slot. 
				char time = database.get(i)[3].charAt(0);
				// if that is not equal to 3 or 8, add it to the display list. 
				if((time !='3')&&(time !='8')&& database.get(i)[1].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
			break;
		}
		
		
		
		
		
		
		//if we found the small layout
		if(smallLayout == true){
			// get the menu frame and pass the data. 
			MenuFragment menuFrag = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_frame_small);
			menuFrag.updateMenu(dbDisplay);
		}
		else{
			// this find the ArticleFragment frame and give it a variable name. 
			MenuFragment menuFrag = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_frame);
			// pass the position from the selected menu to the article. 
			// this will be the dbDisplay later. 
			menuFrag.updateMenu(dbDisplay);
		}
	}
	/**
	 * This is used to pass the proper array over to
	 * the Article fragment which then updates the text.
	 * @return
	 */
	public void displayArticleData(int position){
		
		
		// look to see if we are in small mode. 
		if(smallLayout == true){
			// if so find both of the fragments and start transaction. 
			ArticleFragment articleFrag = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_frame_small);
			MenuFragment menuFrag = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_frame_small);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();  
			// hide menu fragment
			ft.hide(menuFrag);
			// show article fragment.
			ft.show(articleFrag);
			//add it to the stack to enable using back button. 
			ft.addToBackStack(null);
			// pass the article data. 
			articleFrag.updateArticle(dbDisplay.get(position));
			// disable the semester (fall/spring) toggle and change how the back button works. 
			showmenu = false;
			// Do not show the sliding menu
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			//commit the change. 
			ft.commit(); 
			
		}
		else{

			// this find the ArticleFragment frame and give it a variable name.
			ArticleFragment articleFrag = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_frame);
			// pass the position from the selected menu to the article. 
			articleFrag.updateArticle(dbDisplay.get(position));
			// Later add if == null, then we are in the phone view. 
		}
	}
	

	
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//This is where the menu button actions are set. 
		
		if(item.getTitle().equals("Fall")&& showmenu == true){
			semester = "Spring";
			item.setTitle("Spring");
			sortData();
			
			
		}
		else if(item.getTitle().equals("Spring")&& showmenu == true){
			semester = "Fall";
			item.setTitle("Fall");
			sortData();
		     
		}
		else if(item.getTitle().equals("Settings")){
			
		}
		else if(item.getTitle().equals("About")){
			new AlertDialog.Builder(this)
		    .setTitle("About")
		    .setMessage(R.string.about)
		     .show();
		}
		
		// this is what happens if you press the home button. 
		else if(item.getItemId()==android.R.id.home){
			if(showmenu == true){
			menu.showMenu();
			}
			else if(showmenu== false){
				showmenu=true;
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				super.onBackPressed();
			}
		}
		
	   
    	return true;
	}
	
	//this is what happens when the back button is pressed
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else if(showmenu== false){
				showmenu=true;
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				super.onBackPressed();
		}
		else{
			super.onBackPressed();
		}
	}
	
	
	/*
	 * To Do:
	 * 
	 * custom adapter -= to show time and date in the list menu. 
	 * Also, Final values need to be set (for permanent storage)
	 * We need to input real data or setup parse.
	 * 			- Know when to update the parse database.  
	 * We need to set a theme.
	 * Implement settings.  
	 * Add the ability to mark convos as attended, this means from the passed position, finding the ID and marking it.  
	 * 
	 * 
	 */
	
}
