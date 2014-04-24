package com.dreaming.drilling.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.dreaming.drilling.R;
import com.dreaming.drilling.utils.GlobalConstants;

public class LoginActivity extends Activity implements OnClickListener{
	SharedPreferences sharedPrefs;
	
	// 初期先写这样，后期提供界面来设置
	private String server_address = "1.192.121.159";
	private String server_port = "5000";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPrefs = getApplicationContext().getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		
		// 如果server_address和server_port没有设定
		if (!"".equals(sharedPrefs.getString("server_address", "")) && !"".equals(sharedPrefs.getString("server_port", ""))) { 
			SharedPreferences.Editor localEditor = this.sharedPrefs.edit();
			localEditor.putString("server_address", this.server_address);
			localEditor.putString("server_port",this.server_port);
			localEditor.commit();
		}
		
		
		
		
		
		setContentView(R.layout.activity_login);
		
		initView();
		
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			login_system();
			break;
		case R.id.btnReset:
			reset_login();
			break;
		}
	}
	
		
		
	/**
	 * 初始化登录界面
	 * */
	public void initView()
	{

		findViewById(R.id.btnLogin).setOnClickListener(this);
		findViewById(R.id.btnReset).setOnClickListener(this);
	}
	
	
	/**
	 * 登录方法
	 * 点击登录按钮的事件
	 * */
	private void login_system()
	{
		EditText ed_username = (EditText) findViewById(R.id.txtUsername);
		EditText ed_password = (EditText) findViewById(R.id.txtPassword);
		
		String username = ed_username.getText().toString();
		String password = ed_password.getText().toString();
		
		if(username!=null && password!=null)
		{
			Toast.makeText(this, username +"," + password, Toast.LENGTH_SHORT).show();
			
		}
		
		
	}
	
	/**
	 * 重置按钮事件
	 * */
	private void reset_login()
	{
		EditText ed_username = (EditText) findViewById(R.id.txtUsername);
		EditText ed_password = (EditText) findViewById(R.id.txtPassword);
		
		ed_username.getText().clear();
		ed_password.getText().clear();
		
		
	}
	
}
