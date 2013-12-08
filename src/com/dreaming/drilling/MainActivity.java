package com.dreaming.drilling;


import com.dreaming.drilling.utils.GlobalConstants;
import com.example.drilling.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private String title_name = "°à±¨Â¼Èë";
	
	protected SharedPreferences sharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView textView = (TextView)findViewById(R.id.title);
		textView.setText(title_name);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void buildingPreference()
	{
		this.sharedPrefs = this.getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		
		if (!this.sharedPrefs.getBoolean("first2", true)) return;
		
		SharedPreferences.Editor localEditor = this.sharedPrefs.edit();
		
		
	}

}
