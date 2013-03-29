package com.jacob.patton.bereaconvo;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

// This uses SherlockListFragment to implement the list. 
public class MenuFragment extends SherlockListFragment{
	// setting text - used for testing. 
	TextView text;
	
	// setting the interface variable. 
	onArticleSelected mainActivityCall;
	
	//Test Data
	List<String[]> dbDisplay;
	String[] titles;

	
	// MainActivity class must implement this interface so the frag can deliver the message/subroutine
	public interface onArticleSelected{
		// this subroutine must be in the MainActivity class. 
		public void displayArticleData(int position);
	}
	
	// This is for the list
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// create the test data. 
		testData();
		// use the list adapter to create a new list from the titles string. 
		// it uses the current activity (whcih is the getActivity)
		// then it needs the simpl_list_item_1, which i'm not sure why. 
		ListAdapter myList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,titles);
		// then it sets the list adapter. 
		setListAdapter(myList);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 	// this sets the layout. 
		return inflater.inflate(R.layout.fragment_menu, container, false);
	 
		 
}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		 // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
		try{
			mainActivityCall = (onArticleSelected) activity;
			
		} catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
	}
	
	

	

	public void onListItemClick(ListView l, View v, int position, long id) {
	  // on click, whichever title you selected, it passes the position to the main activity which passes the correct array. 
		
	mainActivityCall.displayArticleData(position);
	 }



	// This is the same test data that the main class has. 
	// Later we will have to pass the data or access it. 
	public void testData(){
		 dbDisplay = new ArrayList<String[]>();
			for(int i=0; i<5; i++){
				dbDisplay.add(new String[]{""+i,"Fall",i+"/8/12","3:00pm","fall convo "+i ,"This is a description of the data that will be used"});
				dbDisplay.add(new String[]{""+i+5,"Spring",i+"/2/13","3:00pm","spring convo "+i+5, "This is a description of the data that will be used"});
			}
			for(int i=0; i<2; i++){
				dbDisplay.add(new String[]{""+i+10,"Fall",i+"/2/13","8:00pm","fall convo "+i+10,"This is a description of the data that will be used"});
				dbDisplay.add(new String[]{""+i+12,"Spring",i+"/2/14","8:00pm","Spring convo "+i+12,"This is a description of the data that will be used"});
			}
			for(int i=0; i<2; i++){
				dbDisplay.add(new String[]{""+i+14,"Fall",i+"/2/13","6:00pm","fall convo "+i+14,"This is a description of the data that will be used"});
				dbDisplay.add(new String[]{""+i+16,"Spring",i+"/2/14","6:00pm","spring convo "+i+16,"This is a description of the data that will be used"});
			}
		
		
		titles = new String[dbDisplay.size()];
		for(int i=0 ; i <dbDisplay.size();i++){
			titles[i]= dbDisplay.get(i)[4];
		}
		
	}
	
}
