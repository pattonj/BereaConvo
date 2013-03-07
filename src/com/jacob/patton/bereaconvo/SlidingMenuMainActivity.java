package com.jacob.patton.bereaconvo;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import android.os.Bundle;


public class SlidingMenuMainActivity extends SherlockActivity {
	
	
	 
	public boolean onCreateOptionsMenu(Menu menu) {
		
        menu.add("Fall")
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add("Spring")
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
        menu.add("Settings")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        menu.add("About")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        return true;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// set content view
			setContentView(R.layout.activity_main);
		
	        // configure the SlidingMenu
	        SlidingMenu menu = new SlidingMenu(this);
	        menu.setMode(SlidingMenu.LEFT);
	        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	        menu.setShadowWidthRes(R.dimen.shadow_width);
	        menu.setShadowDrawable(R.drawable.shadow);
	        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
	        menu.setFadeDegree(0.35f);
	        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
	        menu.setMenu(R.layout.sidemenu);
	        
	    }	

		
    

}
