package com.dreaming.drilling.adapter;


import com.dreaming.drilling.utils.GlobalConstants;
import com.example.drilling.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private String title_name = "班报录入";
	
	protected SharedPreferences sharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		buildingPreference();
		initView();
		

	}

	
	private void initView()
	{
		TextView textView_title = (TextView)findViewById(R.id.title);
		textView_title.setText(title_name);
		
		// 孔号
		TextView textview_holenumber = (TextView) findViewById(R.id.tourreport_holenumber_value);
		String holenumber = this.sharedPrefs.getString("holenumber", "zk001");
		Log.d("holenumber", holenumber);
		// 设置字体样式
		SpannableString msp = new SpannableString(holenumber);
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, holenumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
        msp.setSpan(new UnderlineSpan(), 0,holenumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview_holenumber.setText(msp);
		
		// 钻机编号
		TextView textview_rigmachine_number = (TextView) findViewById(R.id.tourreport_rigmachine_number_value);

		String rignumber = this.sharedPrefs.getString("rignumber", "rigmachine001");
		// 设置字体样式
		SpannableString msp_rig = new SpannableString(rignumber);
		msp_rig.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, rignumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
		msp_rig.setSpan(new UnderlineSpan(), 0,rignumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview_rigmachine_number.setText(msp_rig);
		
		
		
		
		
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
		
		// 系统所需的基本配置
		localEditor.putBoolean("first2", false);
		
		localEditor.putString("holenumber", "zk002");
		localEditor.putString("rignumber", "rigmachine002");
		
		localEditor.commit();
		
	}

}
