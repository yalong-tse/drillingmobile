package com.dreaming.drilling.activity;

import com.dreaming.drilling.R;
import com.dreaming.drilling.R.layout;
import com.dreaming.drilling.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 
 * 班报查看的方法
 * 
 * */
public class TourreportViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourreport_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tourreport_view, menu);
		return true;
	}

}
