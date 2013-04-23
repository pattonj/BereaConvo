package com.jacob.patton.bereaconvo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;



public class MainActivity extends SherlockFragmentActivity 
		implements MenuFragment.onArticleSelected{
	
	//Master "database"
	private List<String[]> database;

	// String variable for attended convos. 
	private String storedAttendance="";
	private static final String STORED_CONVOS ="storedConvos";
	
	// String variable for the semester. 
	private String semester = "Fall"; 
	private static final String STORED_SEMESTER ="storedSemester";
	
	
	//Integer that controls how the data is sorted. 
	private int sortID = 0;
	private static final String STORED_SORT ="storedSort";
	
	//Integer for the article that is being displayed
	private int displayedArticle=0;
	private static final String STORED_POSITION ="storedPosition";
	
	//Used to determine if an article is showing
	// (Always shows article when rotating from large land to portrait). 
	private  boolean displayingArticle = false;
	static final String DISPLAYING_ARTICLE ="DisplayingArticle";
	
	//the "database" to be displayed. 
	private List<String[]> dbDisplay;

	// sets the sliding menu
	private SlidingMenu menu;
	
	//sets the text/button variable for the side buttons. 
	private TextView allButton ;
    private TextView afternoonButton;
    private TextView eveningButton ;
    private TextView specialButton ;
    
    //a variable to keep track of if the side menu/toggle should work/show. 
    private boolean showmenu = true;
    
    // a variable to decide if it should update due to pressing a  button. 
    private boolean menuButtonPressed = false;
    private boolean longPressed = false;
    
    //a variable to keep track of if we are in small or large mode. 
    private boolean smallLayout = false;
    
	// creates menus. 
	public boolean onCreateOptionsMenu(Menu menu) {

		//This enables the back/up arrow in the top left corner, which lets the user know there is a menu. 
		// The display arrow drawable cane be set via style. (See the slidingmenu list for an example.) 
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		//sets the menu buttons. 
		menu.add(semester)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
        
		menu.add("About")
    	.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
		menu.add("Attendance")
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        menu.add("Free Prizes")
    	.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        return true;
    }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			// set content view
			setContentView(R.layout.main_fragment_activity);
			
			
			
			// Look for the id for small layout
		if(findViewById(R.id.article_frame_small)!= null){
				// find article fragment
				ArticleFragment articleFrag = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_frame_small);
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				// tell the program we are in the small layout. 
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
	        // This gets the current semester by month. 
	        Calendar cal = Calendar.getInstance();
	        int month = cal.get(Calendar.MONTH);
	        // sets the semester variable depending on the month. 
	        if(month > 5 ){
	        	semester = "Fall";
	        }
	        else{
	        	semester ="Spring";
	        }
	        	
	        	
	        // Creates the data
	        createMaster();
		     //sorts the data. 
		     sortData();

		     // if we are in landscape/large layout, show a blank article. 
		     if(!smallLayout){
		    	 displayBlankArticleData();
		     }
		     
	        // creates the side buttons. 
	         allButton = (TextView) menu.findViewById(R.id.ALL);
		     afternoonButton = (TextView) menu.findViewById(R.id.AFTERNOON);
	         eveningButton = (TextView) menu.findViewById(R.id.EVENING);
	         specialButton = (TextView) menu.findViewById(R.id.SPECIAL);
	         
	         // sets the side button action, which sorts the data.  
	         allButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 // tell it that we did press the button, and to sort the menu. 
	            	 menuButtonPressed = true;
	            	 // Passes the variable on how to sort the data. 
	            	 sortDataClick(0);
	            	
	             }
	         });
	        
	         
	         
	         afternoonButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 menuButtonPressed = true;
	            	 sortDataClick(1);
	            	
	             }
	         });
	         
	         eveningButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 menuButtonPressed = true;
	            	 sortDataClick(2);
	            	
	            	 
	             }
	         });
	         
	         specialButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 menuButtonPressed = true;
	            	 sortDataClick(3);
	                
	            }
	         });
	              
	    }	


	/**
	 * This creates the master list (database) of convocations in list that contains Arrays. 
	 * [ID,semester, date,time, Title, description]
	 */
	private void createMaster(){
		
		// The final array is as follows [ID,semester, date,time, Title,speaker, description, attended]
			
		//creates a copy of the data class
		DataStored data = new DataStored();
		
		//creates temporary list for storage and copies the data. 
		List<String[]> temp = new ArrayList<String[]>();
		temp.addAll(data.createdata());
		
		// creates our new database
		database = new ArrayList<String[]>();
		
		// checks to make sure we have attendance data in the string before trying to add it. 
		if(storedAttendance.equals("")){
			
			// if empty create zero's to possibly merge with final array. 
			int size = temp.size();
			String storedAttendanceblank = "";
			for(int i =0; i<size; i++){
				storedAttendanceblank += "0";
			}
			
			// look for the shared preference, if it exists load it, else load the blank string. 
			// This could be made faster by making blank data a submethod. 
			SharedPreferences settings = getPreferences(0);
		    storedAttendance = settings.getString("AttendedConvos", storedAttendanceblank);
		     
		    
		       
		}
		
				
		// this merges our data together
		// Gets the character from the attended string, which requires no splitting since it's only 1,0. 
		for(int i=0;i <temp.size();i++){
			database.add(new String[]{temp.get(i)[0],temp.get(i)[1],temp.get(i)[2],temp.get(i)[3],temp.get(i)[4],temp.get(i)[5],temp.get(i)[6],Character.toString(storedAttendance.charAt(i))});
			
		}
		
		
	}
	
	/** 
	 * Used by the side menu. 
	 * @param ID
	 */
	private void sortDataClick(int option){
		//This sets the sort ID, sorts the data and closes the side menu. 
		sortID = option;
		sortData();
   	 	menu.showContent();
	}
	
	
	/**
	 * This sorts the data depending on the number sent from sliding menu.  
	 * Used the semester variable to decide which ones to add.     
	 *0=all, 1=afternoon, 2=evening, 3=special
	 *
	 */	
	private void sortData(){
		
		
		// overwrites the old display list every time it runs. 
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
			// needed as to keep what it displays straight. 
			menuButtonPressed = false;
		}
		else{
			// this find the ArticleFragment frame and give it a variable name. 
			MenuFragment menuFrag = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_frame);
			// pass the position from the selected menu to the article.  
			menuFrag.updateMenu(dbDisplay);
			//if the data is not empty and it's updating due to a menu press, show the first article. 
			if((!dbDisplay.isEmpty())&&(menuButtonPressed == true)){
				displayArticleData(0);
				menuButtonPressed = false;
			}
			else if(longPressed == true){
				// this is needed so that it doesn't display a blank article or show the first article. 
				longPressed = false;
			}
			else{
				// all other cases exhausted, show the blank article. 
				displayBlankArticleData();
			}
		}
	}
	
	
	/**
	 * This is used to pass the proper array over to
	 * the Article fragment which then updates the text.
	 * @return
	 */
	public void displayArticleData(int position){
		
		//set the article position and if displaying in case of rotate/pause. 
		displayedArticle = position;
		displayingArticle = true;
		
		
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
	
	private void displayBlankArticleData(){
		ArticleFragment articleFrag = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_frame);
		// tells it to show blank article. 
		articleFrag.showBlankArticle();
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//This is where the menu button actions are set. 
		
		// Toggle between fall and spring. 
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
		
		// counts the attendance in a display. 
		
		else if(item.getTitle().equals("Attendance")){
			int fall = sortAttandance("Fall");
			int spring = sortAttandance("Spring");
			new AlertDialog.Builder(this)
		    .setTitle("Attendance")
		    .setMessage("You have attended\n \nFall convos: "+ fall+"\nSpring Convos: "+spring + "\n\n\n*This is NOT an official way to record convos. Convo cards are still required to be turned in. \nPlease see MyBerea for an official list of your convos.")
		     .show();
					
			
		}
		else if(item.getTitle().equals("About")){
			//starts the about intent. 	
			Intent intent = new Intent(this, About.class);
			startActivity(intent);
		}
		else if(item.getTitle().equals("Free Prizes")){
			// starts the swagbucks/advertising intent. 
			Intent intent = new Intent(this, SwagActivity.class);
			startActivity(intent);
		}
		
		// this is what happens if you press the home button. 
		else if(item.getItemId()==android.R.id.home){
			if(showmenu == true){
				// if the side menu is open, close it. 
				menu.showMenu();
			}
			else if(showmenu== false){
				// if we are showing an article (showmenu= false)
				// Enable the menu and set the display on rotate to false. 
				showmenu=true;
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				displayingArticle = false;
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
				// Don't show the article if rotated. 
				displayingArticle = false;
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
			
		// Adding the ID to the list.
		for(int i =0; i<database.size();i++){
			// look to see if the ID from dbDisplay and the database is the same. 
			String ID = database.get(i)[0];
			if(convoID == ID ){
				// if they match, switch whatever it is and toast the switch.  
				if(database.get(i)[7].equals("0")){
					database.get(i)[7] = "1";
					
					  Toast.makeText(this,
					            "Marked as attended",
					            Toast.LENGTH_SHORT).show();
					        
				}
		 		else{
		 			  Toast.makeText(this,
		 			            "Marked as unattended",
		 			            Toast.LENGTH_SHORT).show();
		 			        
		 			database.get(i)[7] = "0";
		 			
		 		}
				
				// This is needed to update the checkmarks. 
				longPressed = true;
				//update the menu. 
				sortData();
				
				// ends the for statement once found to save resources. 
				return;
			
			}
			
		}			
	}
	
	private int sortAttandance(String sem){
		// set the count at 0
		int count= 0;
		// Find all the attendance positions that are 1 and the semester that was passed. 
		for(int i = 0; i< database.size(); i++){
			if((database.get(i)[1].equals(sem))&&(database.get(i)[7].equals("1"))){
			count++;
			}
		}
		// return the total count. 
		return count;
	}
	
	// when it needs to save the state (rotating, pausing, etc), it does the following. 
	  @Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		  // puts final attendance in a string and saves.  
		  saveFinalAttended();
		  //saved the different variables to temporary storage. 
		  savedInstanceState.putString(STORED_CONVOS, storedAttendance);
		  savedInstanceState.putString(STORED_SEMESTER, semester);
		  savedInstanceState.putInt(STORED_SORT, sortID);
		  savedInstanceState.putInt(STORED_POSITION, displayedArticle);
		  savedInstanceState.putBoolean(DISPLAYING_ARTICLE, displayingArticle);
		  	  
	      // Always call the superclass so it can save the view hierarchy state
		  super.onSaveInstanceState(savedInstanceState);
	        }
	  

	  // when it is recreated it does the following. 
	  public void onRestoreInstanceState(Bundle savedInstanceState) {
	    	 super.onRestoreInstanceState(savedInstanceState);
	    	 // restores the different variables. 
	    	 storedAttendance = savedInstanceState.getString(STORED_CONVOS);
	    	 semester = savedInstanceState.getString(STORED_SEMESTER);
	    	 sortID = savedInstanceState.getInt(STORED_SORT);
	    	 displayedArticle =savedInstanceState.getInt(STORED_POSITION);
	    	 displayingArticle = savedInstanceState.getBoolean(DISPLAYING_ARTICLE);
	    	 createMaster();
	    	 sortData();
	    	 // if we are in large/landscape, show the aritcle. 
	    	 if((smallLayout == false)&&(!dbDisplay.isEmpty())){
	    		 displayArticleData(displayedArticle);
	    	 }
	    	 // else, if we were displaying and article, show it again. 
	       	 else if((displayingArticle == true)&&(!dbDisplay.isEmpty())){
	       		displayArticleData(displayedArticle);
	       	 }
	    	 
	    }
	    
	
	  public void onPause() {
		    super.onPause(); 
		    saveFinalAttended();
		    
		    // This saves the attendance count. 
		    SharedPreferences settings = getPreferences(MODE_PRIVATE);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putString("AttendedConvos", storedAttendance);
		    
		    editor.commit();
	  }
	  
	  
	/**
	 * used to save the attendance to a string. 	
	 */
	private void saveFinalAttended(){	
		
		//adding to the string for saving.
		// Start with blank string
		storedAttendance = "";
		// loop through adding the 1 or 0 to the string
		
		for(int i=0;i< database.size();i++){
			storedAttendance+=database.get(i)[7] ;
		}
		
	}
	
	
	/*
	* To Do in the future:
	*
	*	Tweak the layout (size of text ,location, spacing of menus and text). 
	*	It looks ok,but could probably be optimized to look better with longer convos and such on different devices.  
	*	
	*	We would like to add a Berea College theme. 
	*	Add a change themes button. 
	*	
	*	add a database (parse?),xml, or some way to store data locally instead of in a list. 
	*	This would allow updating the info instead of the entire app. 
	*	Currently with the seperate DataStored class as long as it spits out a list, you could implement a database easily. 
	*	Though it would change how the attended convos are stored. 
	*/
	
}
