package com.jacob.patton.bereaconvo;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;




public class About extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		// I did this so that it could use bold print with HTML.  
		TextView res = (TextView)findViewById(R.id.AboutResourcesInfo);
		res.setText(Html.fromHtml(getString(R.string.resources_info)));
		TextView help = (TextView)findViewById(R.id.AboutHelpInfo);
		help.setText(Html.fromHtml(getString(R.string.help_info)));
		
	}

	

}
