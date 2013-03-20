package com.jacob.patton.bereaconvo;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArticleFragment extends SherlockFragment{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set content view
		 return inflater.inflate(R.layout.activity_main, container, false);
		 
		 
	}


	
}
