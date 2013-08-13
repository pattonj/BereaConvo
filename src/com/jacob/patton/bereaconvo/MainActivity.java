package com.jacob.patton.bereaconvo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


//A to-do list can be found at the bottom of the page

public class MainActivity extends SherlockFragmentActivity 
		implements MenuFragment.onArticleSelected{
	
	//Master "database"
	private List<String[]> database;
	//size of "database"
	private int size;

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
	//(Always shows article when rotating from large land to portrait).
	private  boolean displayingArticle = false;
	static final String DISPLAYING_ARTICLE ="DisplayingArticle";
	
	//the "database" to be displayed. 
	private List<String[]> dbDisplay = new ArrayList<String[]>();

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
    
    //Variables for accessing and setting preferences. These can't be declared before starting. 
    private SharedPreferences settings; 
    private SharedPreferences.Editor editor;

    
    // creates top menus. 
	public boolean onCreateOptionsMenu(Menu menu) {

		
		//sets the menu buttons. 
		menu.add(semester)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT); 
        		
		menu.add("Attendance")
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
		menu.add("Theme")
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
		menu.add("About")
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        return true;
    }
	
	
	//Main Method
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
		// sets variable for accessing the preferences and editor. 
		settings = PreferenceManager.getDefaultSharedPreferences(this);
		editor = settings.edit();;
		
		// theme must be set before setting the view. 
		if(settings.getInt("Theme",0) != 0){
			setTheme(R.style.Theme_BereaBlue);
		}
			
		//This would enable the back/up arrow in the top left corner, which lets the user know there is a menu. 
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.menu_icon);
		
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
        
        //sets up the sliding menu
        setSlidingMenu();
        
	    // if we are in landscape/large layout, show a blank article. 
	    if(!smallLayout){
	    	displayBlankArticleData();
	    } 
	    
		 // App updated (not shown on install) and  EULA alerts
		if(settings.getInt("EULA",0) == 0){
			createAlertEULA();
		}
		// if already installed, display what is new with the updated verions
		else if(!settings.getString("version","").equals(getString(R.string.app_version) )){
			createAlertVersion();
		}
		// after a certain number of launches, ask them to rate it. 
		else if( settings.getInt("LaunchNumber",0) == 4){
			rateApp();
		}
		
		
    	editor.putInt("LaunchNumber", settings.getInt("LaunchNumber",0)+1);
	    editor.commit();
		
	}	


	/**
	 * Used to create EULA alert
	 * */
	private void createAlertEULA(){
				
		// User Agreement Message
		new AlertDialog.Builder(this)
		.setTitle("User Agreement")
		.setPositiveButton("Accept",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                    	// put accepted EULA and put app version to not show version alert
                    	editor.putString("version", getString(R.string.app_version)) ;
                    	editor.putInt("EULA", 1);
                	    editor.commit();
                    }
		  })
		.setNegativeButton("Deny",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        System.exit(0);
                    }
		  })

		.setMessage(R.string.EULA)
		.setCancelable(false)
		.show();
	}
	
	/**
	 * Used to create app version alert
	 * */
	private void createAlertVersion(){
				
			// Alert wtih updated info
			new AlertDialog.Builder(this)
			.setTitle(getString(R.string.intro_title))
			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog,int id) {
	                    	// sets version number from strings file
			            	editor.putString("version", getString(R.string.app_version)) ;
			        	    editor.commit();
			        	    dialog.dismiss();
	                    }
			  })
			
			.setMessage(R.string.intro_text)
			.show();		
	}
	
	/**
	 * Used to create "rate app" alerts
	 * */
	private void rateApp(){
		
		// Alert with rate question
		new AlertDialog.Builder(this)
		.setTitle(getString(R.string.Rate_title))
		.setPositiveButton("Rate Now",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                    	//launches the market
                    	launchMarket();
		        	    dialog.dismiss();
                    }
		  })
		  
		    .setNegativeButton("Not now",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, it just dismisses the dialog box. 
                    	dialog.dismiss();
                    }
		  })
		  
		.setMessage(R.string.Rate)
		.show();		
}
	
	//lauches the market for rating the app. 
	private void launchMarket() {
	    Uri uri = Uri.parse("market://details?id=" + getPackageName());
	    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
	    try {
	        startActivity(myAppLinkToMarket);
	    } catch (ActivityNotFoundException e) {
	        Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
	    }
	}
	
	/**
	 * This creates the master list (database) of convocations in list that contains Arrays. 
	 * [ID,semester, date,time, Title, Speaker, description]
	 */
	private void createMaster(){
		
		// The final array is as follows [ID,semester, date,time, Title,speaker, description, attended]
			
		//creates a copy of the data class
		DataStored data = new DataStored();
		
		//creates temporary list for storage and copies the data. 
		List<String[]> temp = new ArrayList<String[]>();
		temp.addAll(data.createdata());
		// The size of the temp will be the size of the full database as well. 
		size = temp.size();
		
		// creates our new database
		database = new ArrayList<String[]>();
		
		// checks to make sure we have attendance data in the string before trying to add it. 
		if(storedAttendance.equals("")||(!settings.getString("version","").equals(getString(R.string.app_version)))){
			
			// if empty create zero's to possibly merge with final array. 
			String storedAttendanceblank = "";
			for(int i =0; i<size; i++){
				storedAttendanceblank += "0";
			}
			// If the versions are different, clear the string. 
			//(This is done first, since it may be blank if the program was not running.  
			if (!settings.getString("version","").equals(getString(R.string.app_version))){
				storedAttendance = storedAttendanceblank;
			}
			else{
				// look for the shared preference, if it exists load it, else load the blank string. 
				// This could be made faster by making blank data a submethod. 
				storedAttendance = settings.getString("AttendedConvos", storedAttendanceblank);
			}
		       
		}
		
		
		
				
		// this merges our data together
		// Gets the character from the attended string, which requires no splitting since it's only 1,0. 
		for(int i=0;i <size;i++){
			database.add(new String[]{temp.get(i)[0],temp.get(i)[1],temp.get(i)[2],temp.get(i)[3],temp.get(i)[4],temp.get(i)[5],temp.get(i)[6],Character.toString(storedAttendance.charAt(i))});
		}
		
		
	}
	
	/**
	 * Used to setup the side menu from onCreate. 
	 * Options can be added or subtracted here to change everything on the side. 
	 */
	private void setSlidingMenu(){
		
		// configure the SlidingMenu
		menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sidemenu); 

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
		
		
     // highlights "All" from the side menu.  
        sideHighlight(0); 
		
		
	}
	
	/** 
	 * Used by the side menu to highlight the selected one. 
	 * @param ID
	 */
	private void sideHighlight(int position){
		//This sets the sort ID, sorts the data and closes the side menu. 
		switch (position){
		
		case 0: 
			allButton.setBackgroundColor(getResources().getColor(R.color.faded_gray));
			afternoonButton.setBackgroundColor(getResources().getColor(R.color.invisible));
		    eveningButton.setBackgroundColor(getResources().getColor(R.color.invisible));
		    specialButton.setBackgroundColor(getResources().getColor(R.color.invisible));
			break;
			
		case 1: 
			allButton.setBackgroundColor(getResources().getColor(R.color.invisible));
			afternoonButton.setBackgroundColor(getResources().getColor(R.color.faded_gray));
		    eveningButton.setBackgroundColor(getResources().getColor(R.color.invisible));
		    specialButton.setBackgroundColor(getResources().getColor(R.color.invisible));
			break;
		case 2: 
			allButton.setBackgroundColor(getResources().getColor(R.color.invisible));
			afternoonButton.setBackgroundColor(getResources().getColor(R.color.invisible));
		    eveningButton.setBackgroundColor(getResources().getColor(R.color.faded_gray));
		    specialButton.setBackgroundColor(getResources().getColor(R.color.invisible));
			break;
		case 3: 
			allButton.setBackgroundColor(getResources().getColor(R.color.invisible));
			afternoonButton.setBackgroundColor(getResources().getColor(R.color.invisible));
		    eveningButton.setBackgroundColor(getResources().getColor(R.color.invisible));
		    specialButton.setBackgroundColor(getResources().getColor(R.color.faded_gray));
			break;
		
		}		
	}
	
	
	/** 
	 * Used by the side menu to sort the data. 
	 * @param ID
	 */
	private void sortDataClick(int option){
		//This sets the sort ID, sorts the data and closes the side menu. 
   	 	sideHighlight(option);
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
		
		//clears the old list
		dbDisplay.clear();
		
		switch (sortID){
		
		case 0:// if 0, copy the current semester to dbDisplay
			//run through the database add it to the display.  
			for(int i = 0; i< size; i++){
				if(database.get(i)[1].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
			break;
			
		case 1:// if 1, check for afternoon convos
			//run through the database 
			for(int i = 0; i< size; i++){
				//get the first character from the time slot. 
				char time = database.get(i)[3].charAt(0);
				// this is to look for special convos that are at a afternoon/evening time. 
				if (time == '*'){
					time = database.get(i)[3].charAt(1);
				}
				// if that is equal to 3, add it to the display list. 
				if((time == '3')&& database.get(i)[1].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
			break;
		case 2:	// if 2, check for evening convos
			//run through the database 
			for(int i = 0; i< size; i++){
				//get the first character from the time slot. 
				char time = database.get(i)[3].charAt(0);
				// this is to look for special convos that are at a afternoon/evening time. 
				if (time == '*'){
					time = database.get(i)[3].charAt(1);
				}
				// if that is equal to 8, add it to the display list. 
				if((time == '8')&& database.get(i)[1].equals(semester)){
					dbDisplay.add(database.get(i));
				}
			}
			break;
			
		case 3: // if 3, check for special convos
			//run through the database 
			for(int i = 0; i< size; i++){
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
			// needed as to help decide which actions to perform or allow. . 
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
			// This is needed so that it doesn't display a blank article or show the first article. 
			// since it runs sort() on long press to update the display. 
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
			getSupportActionBar().setIcon(R.drawable.back_icon);
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
	
	/**
	 * used by side menu to mark a convocation
	 * as attended. 	
	 */
	public void markConvo(int position){
	
		// turns the id int and integer for the list
		String convoID = dbDisplay.get(position)[0];
			
		// Adding the ID to the list.
		for(int i =0; i<size;i++){
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
		 			database.get(i)[7] = "0";
		 			Toast.makeText(this,
		 					"Marked as unattended",
		 			        Toast.LENGTH_SHORT).show();
		 		}
				
				// This is needed to update the checkmarks and not update the displaying article.  
				longPressed = true;
				//update the menu. 
				sortData();
				
				// ends the for statement once found to save resources. 
				return;
			
			}
			
		}			
	}
	
	/**
	 * used to find the number of convos attended per semester.	
	 */
	private int sortAttandance(String sem){
		// set the count at 0
		int count= 0;
		// Find all the attendance positions that are 1 and the semester that was passed. 
		for(int i = 0; i< size; i++){
			if((database.get(i)[1].equals(sem))&&(database.get(i)[7].equals("1"))){
			count++;
			}
		}
		// return the total count. 
		return count;
	}
	
	/**
	 * used to save the attendance to a string. 	
	 */
	private void saveFinalAttended(){	
		
		//adding to the string for saving.
		// Start with blank string
		storedAttendance = "";
		// loop through adding the 1 or 0 to the string
		
		for(int i=0;i< size;i++){
			storedAttendance+=database.get(i)[7] ;
		}
		
	}
	
	/**
	 * Commands for the menu buttons.  	
	 */
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
		
		
		else if(item.getTitle().equals("Theme")){
						
			// if the settings is 1, make it 0 which = sherlock.  
			if(settings.getInt("Theme",0) != 0){
				editor.putInt("Theme", 0);
			}
			// else make it 1 which = berea blue
			else{
				editor.putInt("Theme", 1);
			}
			// commit the edit and then recreate the activity to apply the theme. 
		    editor.commit();
		    // super.recreate() works on 3.0 (API 11) and above.
		    if(Integer.valueOf(android.os.Build.VERSION.SDK_INT) <11){
		    	// this flags it for no animation and then restarts instead of recreating due to API limitations. 

		        Intent intent = getIntent();
		        overridePendingTransition(0, 0);
		        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        finish();

		        overridePendingTransition(0, 0);
		        startActivity(intent);
		    }
		    else{
		    	super.recreate();	
		    }
		    
		}
		
		// this is what happens if you press the home button. 
		else if(item.getItemId()==android.R.id.home){
			if(showmenu == true){
				// if the side menu is open, close it. 
				if (menu.isMenuShowing()) {
					menu.showContent();
					
				}
				else{
					menu.showMenu();

				}
			}
			else if(showmenu== false){
				// if we are showing an article (showmenu= false)
				// Enable the menu and set the display on rotate to false. 
				showmenu=true;
				// show menu icon. 
				getSupportActionBar().setIcon(R.drawable.menu_icon);
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
				// show menu icon. 
				getSupportActionBar().setIcon(R.drawable.menu_icon);
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
	
	//This is what happens when the app is paused. (rotating, pausing, etc)
	public void onPause() {
	    super.onPause(); 
	    saveFinalAttended();
	    
	    // This saves the attendance count. 
	    //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	   // SharedPreferences.Editor editor = settings.edit();
	    editor.putString("AttendedConvos", storedAttendance);
	    editor.commit();
  }
  
	
	// When restoring from rotating, pausing, etc it does the following.  
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
	    	 // sorts the data and highlights the proper row. 
	    	 sortDataClick(sortID);
	    	 // if we are in large/landscape, show the aritcle. 
	    	 if((smallLayout == false)&&(!dbDisplay.isEmpty())){
	    		 displayArticleData(displayedArticle);
	    		 // hide the back icon
	    		 getSupportActionBar().setIcon(R.drawable.menu_icon);
	    		 
	    	 }
	    	 // else, if we were displaying an article, show it again. 
	       	 else if((displayingArticle == true)&&(!dbDisplay.isEmpty())){
	       		displayArticleData(displayedArticle);
	       		// show back icon. 
	       		getSupportActionBar().setIcon(R.drawable.back_icon);
	       	 }
	    	 
	    }
	    
	
	  
	
	
	/*
	* To Do in the future:
	*
	*	Version control - How to handle the string of convocation data when updating the app!
	*					- Currently if "app_version" in the string file is different, it will clear the list
	*						of stored convos. This works well for yearly update, but not incremental updates.  
	*
	*	Download xml file
	*	Parse XML file
	*	Use SQLite database instead of a string, and make the key based on the time/date
	*
	*	
	*/
	
}
