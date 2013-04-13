package com.jacob.patton.bereaconvo;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.actionbarsherlock.app.SherlockFragment;

public class ArticleFragment extends SherlockFragment{
	
	// Setting Variable names. 
	TextView convoTitle;
	TextView convoDate;
	TextView convoTime;
	TextView convoDescription;
	TextView convoSpeaker;
	String[] toDisplay;
	ImageView logo;
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set content view using the xml layout. 
				 return inflater.inflate(R.layout.fragment_article, container, false);
	}

	
	
	/**
	 * Updates the text inside the article display. - 
	 * [ID,semester,date,time,title,description] - setup of array
	 * @param data - Array with Data. 
	 */

	public void showBlankArticle(){
		convoTitle = (TextView) getView().findViewById(R.id.ConvoTitle);
		convoTitle.setText("");
		
		// find and set convo data
		convoDate = (TextView) getView().findViewById(R.id.ConvoDate);
			convoDate.setText("");
			
		// find and set convo time. 	
		convoTime = (TextView) getView().findViewById(R.id.ConvoTime);
			convoTime.setText("");

		// find and set convo speaker. 	
		convoSpeaker = (TextView) getView().findViewById(R.id.ConvoSpeaker);
			convoSpeaker.setText("");		
			
		// find and set description. 	
		convoDescription = (TextView) getView().findViewById(R.id.ConvoDescription);
			convoDescription.setText("");
		logo = (ImageView) getView().findViewById(R.id.BereLogo);	
			logo.setVisibility(View.VISIBLE);
	
	}
	
	public void updateArticle(String[] data){
	
		// [ID,semester,date,time,title,description] - setup of array.
		
		// find and set convo title text
		convoTitle = (TextView) getView().findViewById(R.id.ConvoTitle);
		convoTitle.setText(data[4]);
		
		// find and set convo data
		convoDate = (TextView) getView().findViewById(R.id.ConvoDate);
			convoDate.setText(data[2]);
			
		// find and set convo time. 	
		convoTime = (TextView) getView().findViewById(R.id.ConvoTime);
			convoTime.setText(data[3]);

		// find and set convo speaker. 	
		convoSpeaker = (TextView) getView().findViewById(R.id.ConvoSpeaker);
			convoSpeaker.setText(data[5]);		
			
		// find and set description. 	
		convoDescription = (TextView) getView().findViewById(R.id.ConvoDescription);
			convoDescription.setText(data[6]);
			
		logo = (ImageView) getView().findViewById(R.id.BereLogo);	
			logo.setVisibility(View.GONE);
	}
	
}
