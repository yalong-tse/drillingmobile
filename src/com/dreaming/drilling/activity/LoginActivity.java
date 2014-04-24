package com.dreaming.drilling.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.dreaming.drilling.R;
import com.dreaming.drilling.utils.GlobalConstants;

public class LoginActivity extends Activity implements OnClickListener{
	SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPrefs = getApplicationContext().getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		
		// 如果server_address和server_port没有设定
		if (!"".equals(sharedPrefs.getString("server_address", "")) && !"".equals(sharedPrefs.getString("server_port", ""))) { 
			
		}
		
		
		
		setContentView(R.layout.activity_login);
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		
	}
	
}
