package com.jacob.patton.bereaconvo;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
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

	// the storable version of attended convos. 
	public String storedAttendance="";
	static final String STORED_CONVOS ="storedConvos";
	
	// this declares the local variable. 
	public String semester = "Fall"; 
	static final String STORED_SEMESTER ="storedSemester";
	
	
	//needs to be a final string that keeps track of how to sort the data
	public int sortID = 0;
	static final String STORED_SORT ="storedSort";
	
	//the database to be displayed. 
	public List<String[]> dbDisplay;

	// sets the sliding menu
	public SlidingMenu menu;
	
	//sets the text/button variable for the side buttons. 
	public TextView allButton ;
    public TextView afternoonButton;
    public TextView eveningButton ;
    public TextView specialButton ;
    
    //a variable to keep track of if the side menu should be allowed to show. 
    public boolean showmenu = true;
    //a variable to keep track of if we are in small or large mode. 
    public boolean smallLayout = false;
    
	// creates menus. 
	public boolean onCreateOptionsMenu(Menu menu) {

		//This enables the back/up arrow in the top left corner, which lets the user know there is a menu. 
		// The display arrow drawable is set via style. (See the slidingmenu list for an example.) 
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		//sets the semester button. 
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
	          allButton = (TextView) menu.findViewById(R.id.ALL);
		     afternoonButton = (TextView) menu.findViewById(R.id.AFTERNOON);
	         eveningButton = (TextView) menu.findViewById(R.id.EVENING);
	         specialButton = (TextView) menu.findViewById(R.id.SPECIAL);
	         
	         // sets the side button actions. 
	         allButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 sortID = 0;
	            	 sortData();
	            	 menu.showContent();
	            	 
	            	 
	             }
	         });
	        
	         
	         
	         afternoonButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 sortID = 1;
	            	 sortData();
	            	 menu.showContent();
	            	 
	            	 
	            	 
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
		
		// The array is as follows [ID,semester, date,time, Title, description]
		
		// Breaks up the stored string
		// used to store the text to make sure 
		
		
		
		
			
		
		//creates a copy of the data class
		DataStored data = new DataStored();
		//creates temporary list for storage and copies the data. 
		List<String[]> temp = new ArrayList<String[]>();
		temp.addAll(data.createdata());
		// creates our new database
		database = new ArrayList<String[]>();
		
		// checks to make sure we have data in the string before trying to add it. 
		if(storedAttendance.equals("")){
			int size = temp.size();
			for(int i =0; i<size; i++){
				storedAttendance += "0";
			
			
			}
		}	
			
		
		
		// this merges our data together
		for(int i=0;i <temp.size();i++){
			database.add(new String[]{temp.get(i)[0],temp.get(i)[1],temp.get(i)[2],temp.get(i)[3],temp.get(i)[4],temp.get(i)[5],temp.get(i)[6],Character.toString(storedAttendance.charAt(i))});
			
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
		
		
		
		
		
		
		//if we found the small layout earlier
		if(smallLayout == true){
			// get the menu frame and pass the data. 
			MenuFragment menuFrag = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_frame_small);
			menuFrag.updateMenu(dbDisplay);
		}
		else{
			// this find the ArticleFragment frame and give it a variable name. 
			MenuFragment menuFrag = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_frame);
			// pass the position from the selected menu to the article.  
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
		
		// if the menu is showing, close it. 
		if (menu.isMenuShowing()) {
			menu.showContent();
			
		} 
		// if in article mode, enable menus again and go back. 
		else if(showmenu== false){
				showmenu=true;
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				super.onBackPressed();
		}
		// otherwise, just go back. 
		else{
			super.onBackPressed();
		}
	}
	
	public void markConvo(int position){
	
		// turns the id int and integer for the list
		String convoID = dbDisplay.get(position)[0];
		// this is creating the list, which coudl be done earlier
	
		// Adding the ID to the list.
		for(int i =0; i<database.size();i++){
			String ID = database.get(i)[0];
			if(convoID == ID ){
				if(database.get(i)[7].equals("0")){
					database.get(i)[7] = "1";
				}
		 		else{
		 			database.get(i)[7] = "0";
		 		}
				sortData();
		return;
			}
		}			
	}
	
	  @Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		  saveFinalAttended();
		  savedInstanceState.putString(STORED_CONVOS, storedAttendance);
		  savedInstanceState.putString(STORED_SEMESTER, semester);
		  savedInstanceState.putInt(STORED_SORT, sortID);
	      // Always call the superclass so it can save the view hierarchy state
		  super.onSaveInstanceState(savedInstanceState);
	        }
	  

	  public void onRestoreInstanceState(Bundle savedInstanceState) {
	    	 super.onRestoreInstanceState(savedInstanceState);
	    	 storedAttendance = savedInstanceState.getString(STORED_CONVOS);
	    	 semester = savedInstanceState.getString(STORED_SEMESTER);
	    	 sortID = savedInstanceState.getInt(STORED_SORT);
	    	 createMaster();
	    	 sortData();
	    	 
	    }
	    
		
		
	public void saveFinalAttended(){	
		
		//adding to the string for saving.
		// Start with blank string
		storedAttendance = "";
		// loop through adding the id to the string
		
		for(int i=0;i< database.size();i++){
			storedAttendance+=database.get(i)[7] ;
		}
		
			
		
		
		
	}
	
	/*
	 * To Do:
	 *  
	 * Also, Final values need to be set for create/destroy. 
	 * We need to set a theme, tweaking layout. 
	 * Add convo adttended button. 
	 * Implement settings. - Change to themes?   
	 * 
	 * 
	 *   
	 * 
	 * 
	 */
	
}
