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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

// This uses SherlockListFragment to implement the list. 
public class MenuFragment extends SherlockListFragment{

	
	// setting the interface variable. 
	onArticleSelected mainActivityCall;
	
	//Test Data
	List<String[]> dbDisplay;
	ArrayList<String> titles;
	ArrayAdapter<String> myList;
	
	
	
	// MainActivity class must implement this interface so the frag can deliver the message/subroutine
	public interface onArticleSelected{
		// this subroutine must be in the MainActivity class. 
		public void displayArticleData(int position);
	}
	
	// This is for the list
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// create the test data. 
		titles = new ArrayList<String>();
		// use the list adapter to create a new list from the titles string. 
		// it uses the current activity (whcih is the getActivity)
		// then it needs the simpl_list_item_1, which i'm not sure why.

		 myList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,titles);
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
	myList.notifyDataSetChanged();
	
	}


	
	
	public void updateMenu(List<String[]> data){
		 dbDisplay = new ArrayList<String[]>();
		 
		 titles.clear();
		
			for(int i=0 ; i <data.size();i++){
				titles.add(data.get(i)[4]);
			}
			myList.notifyDataSetChanged();
		}
		
	
	
	
}
