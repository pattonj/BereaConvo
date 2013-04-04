package com.jacob.patton.bereaconvo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.actionbarsherlock.app.SherlockListFragment;

// This uses SherlockListFragment to implement the list. 
public class MenuFragment extends SherlockListFragment{

	
	// setting the interface variable. 
	onArticleSelected mainActivityCall;
	 
	
	SimpleAdapter myList2;
	List<HashMap<String, String>> convodata;
	
	
	
	// MainActivity class must implement this interface so the frag can deliver the message/subroutine
	public interface onArticleSelected{
		// this subroutine must be in the MainActivity class. 
		public void displayArticleData(int position);
	}
	
	// This is for the list
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
				
		convodata = new ArrayList<HashMap<String, String>>();
		
		String[] from = new String[] {"title", "date", "time"};
		int[] to = new int[] {R.id.lvTitle,R.id.lvDate,R.id.lvTime};
		myList2 = new SimpleAdapter(getActivity(),convodata,R.layout.listview_row,from, to);
		setListAdapter(myList2);
		
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


	
	
	public void updateMenu(List<String[]> data){
		 
		 convodata.clear();
		 
	        for(int i = 0; i < data.size(); i++){
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("title", data.get(i)[4]);
	            map.put("date", data.get(i)[3]);
	            map.put("time", data.get(i)[2]);
	            convodata.add(map);
	        }
		 
		
			myList2.notifyDataSetChanged();
		}
		
}
