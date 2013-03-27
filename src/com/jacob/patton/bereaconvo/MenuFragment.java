package com.jacob.patton.bereaconvo;

import com.actionbarsherlock.app.SherlockFragment;
import android.app.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MenuFragment extends SherlockFragment{
	// setting text - used for testing. 
	TextView text;
	
	// setting the interface variable. 
	onArticleSelected mainActivityCall;
	
	// MainActivity class must implement this interface so the frag can deliver the message/subroutine
	public interface onArticleSelected{
		// this subroutine must be in the MainActivity class. 
		public void displayArticleData();
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState);
	// set content view
	
	// CURERNTLY USED FOR TESTING PURPOSES. 
	//
	// this sets our layout as a variable. 
	 LinearLayout myLayout= (LinearLayout) inflater.inflate(R.layout.fragment_menu, container, false);
	 // then we can access the button inside the layout. 
	 Button mButton = (Button) myLayout.findViewById(R.id.SideButton);
	
	 	mButton.setOnClickListener(new OnClickListener() {
	        @Override
	        // this is the action performed when clicked. 
	        public void onClick(View v) {
	            update();
	        }
	 	});


	 	// this sets the layout. 
	 return myLayout;
	 
		 
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
	
	

// used to update the fragment text - currently for testing purposes.  
public void update(){
	text = (TextView) getView().findViewById(R.id.TextBoxMenu);
	text.setText("working");
	mainActivityCall.displayArticleData();
}
	

}
