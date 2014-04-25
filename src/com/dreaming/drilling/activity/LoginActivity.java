package com.dreaming.drilling.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreaming.drilling.R;
import com.dreaming.drilling.bean.HoleDetail;
import com.dreaming.drilling.bean.SpinnerData;
import com.dreaming.drilling.utils.GlobalConstants;
import com.dreaming.drilling.utils.RestClient;

public class LoginActivity extends FragmentActivity implements ServerDialogFragment.ServerDialogListener ,OnClickListener{
	SharedPreferences sharedPrefs;
	
	// 初期先写这样，后期提供界面来设置
	private String server_address = "1.192.121.159:5000";
	
	private String validate_user_url = "/mobile/validateuser.json?";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//sharedPrefs = getApplicationContext().getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		

		setContentView(R.layout.activity_login);
		initView();
		
		
		// 设置控件上的值
		EditText serverip = (EditText) findViewById(R.id.txtServerIpAndPort);
		this.sharedPrefs = this.getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		String text_pref = this.sharedPrefs.getString("serverip",server_address);
		
		//Toast.makeText(this, text_pref, Toast.LENGTH_SHORT).show();
		
		if(text_pref!=null)
			serverip.append(text_pref);
		
		
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
		
		LinearLayout server_ip_layout = (LinearLayout)findViewById(R.id.lr_serveripandport);
		server_ip_layout.setOnClickListener(server_ip_listener);
		
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
			//Toast.makeText(this, username +"," + password, Toast.LENGTH_SHORT).show();
			// 验证用户
			String full_url = "http://"+server_address+validate_user_url+"?account="+username + "&password=" + password;
			new ValidateUser().execute(full_url);
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
	
	
	private class ValidateUser extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... urls) {

			String result = RestClient.validate_user(urls[0]);
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result!=null && !result.equalsIgnoreCase("false"))
			{
				// 登录成功了
				Intent intent = new Intent(LoginActivity.this, DrillSettingsActivity.class);
				startActivity(intent);
			}
			else
			{
				Toast.makeText(LoginActivity.this, "用户名密码错误，请重新输入", Toast.LENGTH_SHORT).show();
			}
			
		}
		
		
	}


	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String editText) {
		// TODO Auto-generated method stub
		EditText server_ip = (EditText) findViewById(R.id.txtServerIpAndPort);
		server_ip.setText(editText);
		server_address = editText;
		// 如果server_address和server_port没有设定
		//if (!"".equals(sharedPrefs.getString("server_address", ""))) {
		SharedPreferences.Editor localEditor = getPreference();
		localEditor.putString("serverip", this.server_address);
		localEditor.commit();
		//}
		
	}


	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
	
	
	public SharedPreferences.Editor getPreference() {
		this.sharedPrefs = this.getSharedPreferences(
				GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		return this.sharedPrefs.edit();
	}
	
	
	private OnClickListener server_ip_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			showAlertDialog();
		}
	};
	
	
	private void showAlertDialog() {
		
		//if(ip==null)
		//	ip = (TextView) findViewById(R.id.text_server_ip_value);
		EditText serverip = (EditText) findViewById(R.id.txtServerIpAndPort);
		
		DialogFragment dialog = ServerDialogFragment.newInstance(serverip.getText().toString());
		
		//InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		
		dialog.show(getSupportFragmentManager(), "server ip");
	}
}
