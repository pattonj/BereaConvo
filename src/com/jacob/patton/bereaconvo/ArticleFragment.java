package com.jacob.patton.bereaconvo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;


import com.actionbarsherlock.app.SherlockFragment;

public class ArticleFragment extends SherlockFragment{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set content view
		 return inflater.inflate(R.layout.fragment_article, container, false);
	}

	

}
