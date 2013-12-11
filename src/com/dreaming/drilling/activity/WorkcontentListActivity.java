package com.dreaming.drilling.activity;

import com.dreaming.drilling.R;
import com.dreaming.drilling.R.layout;
import com.dreaming.drilling.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class WorkcontentListActivity extends Activity {

	private String title_name = "班报列表";
	private ListView listview = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workcontent_list);
		initview();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workcontent_list, menu);
		return true;
	}
	
	/**
	 * 
	 * */
	public void initview()
	{
		TextView textView_title = (TextView)findViewById(R.id.title);
		textView_title.setText(title_name);
		
		listview = (ListView) findViewById(R.id.workcontent_list);
		
		
		
		
	}

	
	
	
}
