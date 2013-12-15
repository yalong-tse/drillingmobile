package com.dreaming.drilling.activity;

import com.dreaming.drilling.R;
import com.dreaming.drilling.R.layout;
import com.dreaming.drilling.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TakeoverActivity extends Activity implements OnClickListener{

	private String title_name = "填写交接说明";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takeover);
		
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.takeover, menu);
		return true;
	}
	
	
	private void initView() {
		TextView textView_title = (TextView) findViewById(R.id.title);
		textView_title.setText(title_name);
		
		
		findViewById(R.id.takeover_btn_cancel).setOnClickListener(this);
		findViewById(R.id.takeover_btn_save).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.takeover_btn_save:
			btn_save();
			break;
		case R.id.takeover_btn_cancel:
			btn_cancel();
			break;
		}
	}
	
	
	private void btn_cancel()
	{
		Intent mIntent = new Intent(this,MainActivity.class);
		startActivity(mIntent);
	}
	
	
	private void btn_save()
	{
		Intent mIntent = new Intent(this,MainActivity.class);
		startActivity(mIntent);
	}
	
	
	
	

}
