package com.dreaming.drilling.adapter;


import java.util.Calendar;
import java.util.Locale;

import com.dreaming.drilling.utils.GlobalConstants;
import com.example.drilling.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {
	
	private String title_name = "班报录入";
	
	protected SharedPreferences sharedPrefs;
	
	//获取一个日历对象  
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);  


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
		
		
		// 处理班报的日期和时间
		TextView tv_date = (TextView) findViewById(R.id.tourreport_date_value);
		TextView tv_time = (TextView) findViewById(R.id.tourreport_time_value);
		tv_date.setOnClickListener(dp_click);
		tv_time.setOnClickListener(tp_click);
		
		
	}
	
    private TimePickerDialog.OnTimeSetListener tp_listener = new TimePickerDialog.OnTimeSetListener() 
    {  
         @Override 
         public void onTimeSet(TimePicker view, int hourOfDay, int minute) {  
             dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);  
             dateAndTime.set(Calendar.MINUTE, minute);  
             updateLabel();  
         }
     };  

     
     //当点击DatePickerDialog控件的设置按钮时，调用该方法  
     private DatePickerDialog.OnDateSetListener dp_listener = new DatePickerDialog.OnDateSetListener()  
     {  
    	 @Override 
    	 public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) 
    	 {  
    		 //修改日历控件的年，月，日  
    		 //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致  
    		 dateAndTime.set(Calendar.YEAR, year);  
    		 dateAndTime.set(Calendar.MONTH, monthOfYear);  
    		 dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);     
    		 //将页面TextView的显示更新为最新时间  
    		 updateLabel();             
    	 }
     };  

     private View.OnClickListener dp_click = new View.OnClickListener() {  
    	 @Override 
    	 public void onClick(View v) 
    	 {  
    		 //生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置  
    		 new DatePickerDialog(
    				 MainActivity.this,dp_listener,
    				 dateAndTime.get(Calendar.YEAR),
    				 dateAndTime.get(Calendar.MONTH),
    				 dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    	}  
    };

     private View.OnClickListener tp_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			new TimePickerDialog(MainActivity.this,tp_listener,
					dateAndTime.get(Calendar.HOUR_OF_DAY),
					dateAndTime.get(Calendar.MINUTE),true).show();  
		}
	};
     
     
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
	
	private void updateLabel() {
		
	}  

	
	

}
