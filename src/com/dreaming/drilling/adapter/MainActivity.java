package com.dreaming.drilling.adapter;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.dreaming.drilling.utils.GlobalConstants;
import com.dreaming.drilling.R;

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


/**
 * �౨��Ľ��棬Ҳ��������
 * */
public class MainActivity extends Activity {
	
	private String title_name = "�౨¼��";
	
	protected SharedPreferences sharedPrefs;
	
	//��ȡһ����������  
    private Calendar thedate = Calendar.getInstance(Locale.CHINA); 
    private Calendar starttime = Calendar.getInstance();
    private Calendar endtime = Calendar.getInstance();

    private java.text.DateFormat date_fmt = DateFormat.getDateInstance(); 
    private DateFormat time_fmt = DateFormat.getTimeInstance();

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
		
		// �׺�
		TextView textview_holenumber = (TextView) findViewById(R.id.tourreport_holenumber_value);
		String holenumber = this.sharedPrefs.getString("holenumber", "zk001");
		Log.d("holenumber", holenumber);
		// ����������ʽ
		SpannableString msp = new SpannableString(holenumber);
        msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, holenumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //����
        msp.setSpan(new UnderlineSpan(), 0,holenumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview_holenumber.setText(msp);
		
		// ������
		TextView textview_rigmachine_number = (TextView) findViewById(R.id.tourreport_rigmachine_number_value);

		String rignumber = this.sharedPrefs.getString("rignumber", "rigmachine001");
		// ����������ʽ
		SpannableString msp_rig = new SpannableString(rignumber);
		msp_rig.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, rignumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //����
		msp_rig.setSpan(new UnderlineSpan(), 0,rignumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview_rigmachine_number.setText(msp_rig);
		
		
		// ����౨�����ں�ʱ��
		TextView tv_date = (TextView) findViewById(R.id.tourreport_date_value);
		TextView tv_time = (TextView) findViewById(R.id.tourreport_time_value);
		//tv_date.setText(date_fmt.format(thedate));
		//tv_time.setText(time_fmt.format(starttime));
		tv_date.setOnClickListener(dp_click);
		tv_time.setOnClickListener(tp_click);
		
		
	}
	
    private TimePickerDialog.OnTimeSetListener tp_listener = new TimePickerDialog.OnTimeSetListener() 
    {  
         @Override 
         public void onTimeSet(TimePicker view, int hourOfDay, int minute) {  
             starttime.set(Calendar.HOUR_OF_DAY, hourOfDay);  
             starttime.set(Calendar.MINUTE, minute);  
             updateLabel();  
         }
     };  

     
     //�����DatePickerDialog�ؼ������ð�ťʱ�����ø÷���  
     private DatePickerDialog.OnDateSetListener dp_listener = new DatePickerDialog.OnDateSetListener()  
     {  
    	 @Override 
    	 public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) 
    	 {  
    		 //�޸������ؼ����꣬�£���  
    		 //�����year,monthOfYear,dayOfMonth��ֵ��DatePickerDialog�ؼ����õ�����ֵһ��  
    		 thedate.set(Calendar.YEAR, year);  
    		 thedate.set(Calendar.MONTH, monthOfYear);  
    		 thedate.set(Calendar.DAY_OF_MONTH, dayOfMonth);     
    		 //��ҳ��TextView����ʾ����Ϊ����ʱ��  
    		 updateLabel();             
    	 }
     };  

     private View.OnClickListener dp_click = new View.OnClickListener() {  
    	 @Override 
    	 public void onClick(View v) 
    	 {  
    		 //����һ��DatePickerDialog���󣬲���ʾ����ʾ��DatePickerDialog�ؼ�����ѡ�������գ�������  
    		 new DatePickerDialog(
    				 MainActivity.this,dp_listener,
    				 thedate.get(Calendar.YEAR),
    				 thedate.get(Calendar.MONTH),
    				 thedate.get(Calendar.DAY_OF_MONTH)).show();
    	}  
    };

     private View.OnClickListener tp_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			new TimePickerDialog(MainActivity.this,tp_listener,
					starttime.get(Calendar.HOUR_OF_DAY),
					starttime.get(Calendar.MINUTE),true).show();  
		}
	};
     
     
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
	 * ϵͳ�Ļ�������
	 * */
	public void buildingPreference()
	{
		this.sharedPrefs = this.getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		
		if (!this.sharedPrefs.getBoolean("first2", true)) return;
		
		SharedPreferences.Editor localEditor = this.sharedPrefs.edit();
		
		// ϵͳ����Ļ�������
		localEditor.putBoolean("first2", false);
		
		localEditor.putString("holenumber", "zk002");
		localEditor.putString("rignumber", "rigmachine002");
		
		localEditor.commit();
		
	}
	
	private void updateLabel() {
		
	}  

	
	

}
