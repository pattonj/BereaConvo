package com.jacob.patton.bereaconvo;


import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;


import com.actionbarsherlock.app.SherlockFragment;

public class ArticleFragment extends SherlockFragment{
	
	TextView convoTitle;
	TextView convoDate;
	TextView convoTime;
	TextView convoDescription;
	List<String[]> dbDisplay;
	//MainActivity main = new MainActivity();
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set content view
		 return inflater.inflate(R.layout.fragment_article, container, false);
	}

	

	public void updateArticle(List dbDisplay, int ID){
		//dbDisplay = new ArrayList<String[]>();
		//dbDisplay = main.getMenuData();// ID,semester,date,time,title,description.
		convoTitle = (TextView) getView().findViewById(R.id.ConvoTitle);
		convoTitle.setText("success");
		//convoTitle.setText(dbDisplay.get(id)[4]);
		/*convoDate = (TextView) getView().findViewById(R.id.ConvoDate);
			convoDate.setText(dbDisplay.get(id)[2]);
		convoTime = (TextView) getView().findViewById(R.id.ConvoTime);
			convoTime.setText(dbDisplay.get(id)[3]);
		convoDescription = (TextView) getView().findViewById(R.id.ConvoDescription);
			convoDescription.setText(dbDisplay.get(id)[5]);*/
	}
	
}
