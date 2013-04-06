package com.jacob.patton.bereaconvo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;

// This uses SherlockListFragment to implement the list. 
public class MenuFragment extends SherlockListFragment{

	
	// setting the interface variable. 
	onArticleSelected mainActivityCall;
	 
	
	SimpleAdapter myList2;
	List<HashMap<String, Object>> convodata;
	
	
	
	
	// MainActivity class must implement this interface so the frag can deliver the message/subroutine
	public interface onArticleSelected{
		// this subroutine must be in the MainActivity class. 
		public void displayArticleData(int position);
		
		public void  markConvo(int position);
				
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 	// this sets the layout. 
		
		convodata = new ArrayList<HashMap<String, Object>>();
		
		String[] from = new String[] {"title", "date", "time","checkmark"};
		int[] to = new int[] {R.id.lvTitle,R.id.lvDate,R.id.lvTime,R.id.lvCheckmark};
		myList2 = new SimpleAdapter(getActivity(),convodata,R.layout.listview_row,from, to);
		setListAdapter(myList2);
		
		LinearLayout mylayout = (LinearLayout)		inflater.inflate(R.layout.fragment_menu, container, false);
		
		ListView list = (ListView)mylayout.findViewById(android.R.id.list);
		list.setLongClickable(true);
	    list.setOnItemLongClickListener(new OnItemLongClickListener() {
	    	

	      @Override
	      public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
	       	        
	    	  Toast.makeText(getActivity(),
	            "Item in position " + position + " clicked",
	            Toast.LENGTH_LONG).show();
	        // Return true to consume the click event. In this case the
	        // onListItemClick listener is not called anymore.
	        mainActivityCall.markConvo(position);
	        return true;
	      }
	    });
		
		
		//return inflater.inflate(R.layout.fragment_menu, container, false);
		return mylayout;
		 
		 
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
	            HashMap<String, Object> map = new HashMap<String, Object>();
	            map.put("title", data.get(i)[4]);
	            map.put("date", data.get(i)[3]);
	            map.put("time", data.get(i)[2]);
	            if(data.get(i)[6]=="0"){
	            	map.put("checkmark", null);
	            }
	            else{
	            	map.put("checkmark", R.drawable.checkmark);
	            }
	            convodata.add(map);
	        }
		 
		
			myList2.notifyDataSetChanged();
		}
		
}
