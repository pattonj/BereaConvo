package com.jacob.patton.bereaconvo;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MenuFragment extends SherlockFragment{
	TextView text;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState);
	// set content view
	
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



	 return myLayout;
	 
		 
}

// used to update the fragment text. 
public void update(){
	text = (TextView) getView().findViewById(R.id.TextBoxMenu);
	text.setText("working");	
}
	

}
