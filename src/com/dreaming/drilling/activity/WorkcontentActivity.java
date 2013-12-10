package com.dreaming.drilling.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.dreaming.drilling.R;
import com.dreaming.drilling.R.layout;
import com.dreaming.drilling.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class WorkcontentActivity extends Activity {

	private Calendar starttime = Calendar.getInstance(Locale.CHINA);
    private Calendar endtime = Calendar.getInstance(Locale.CHINA);

    private SimpleDateFormat time_fmt = new SimpleDateFormat("HH:mm");
    
    private TextView tv_starttime;
    private TextView tv_endtime;
    private Spinner spinner_workcontent;

    private String title_name = "工作内容";
    private String[] workcontent_arr = {"起下钻","钻进","取心","起下钻，取心"};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workcontent);
		initview();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workcontent, menu);
		return true;
	}

	
	private void initview()
	{
		
		TextView textView_title = (TextView)findViewById(R.id.title);
		textView_title.setText(title_name);
		
		
		tv_starttime = (TextView) findViewById(R.id.sub_starttime_value);
		tv_endtime = (TextView) findViewById(R.id.sub_endtime_value);
		spinner_workcontent = (Spinner) findViewById(R.id.sub_workcontent);
		
		updateStarttime();
		updateEndtime();
		
		tv_starttime.setOnClickListener(tp_starttime_click);
		tv_endtime.setOnClickListener(tp_endtime_click);
		
		ArrayAdapter<String> workcontent_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,workcontent_arr);
		workcontent_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_workcontent.setAdapter(workcontent_adapter);
		
		
		
	}
	
	private TimePickerDialog.OnTimeSetListener tp_starttime_listener = new TimePickerDialog.OnTimeSetListener() 
    {  
         @Override 
         public void onTimeSet(TimePicker view, int hourOfDay, int minute) {  
             starttime.set(Calendar.HOUR_OF_DAY, hourOfDay);  
             starttime.set(Calendar.MINUTE, minute);  
             updateStarttime();  
         }
     };  

     private TimePickerDialog.OnTimeSetListener tp_endtime_listener = new TimePickerDialog.OnTimeSetListener() 
     {  
          @Override 
          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {  
              endtime.set(Calendar.HOUR_OF_DAY, hourOfDay);  
              endtime.set(Calendar.MINUTE, minute);  
              updateEndtime();  
          }
      };  
      
      
      
      private View.OnClickListener tp_starttime_click = new View.OnClickListener() {
  		@Override
  		public void onClick(View v) {
  			new TimePickerDialog(WorkcontentActivity.this,tp_starttime_listener,
  					starttime.get(Calendar.HOUR_OF_DAY),
  					starttime.get(Calendar.MINUTE),true).show();  
  		}
  	};
  	
  	private View.OnClickListener tp_endtime_click = new View.OnClickListener() {
  		@Override
  		public void onClick(View v) {
  			new TimePickerDialog(WorkcontentActivity.this,tp_endtime_listener,
  					endtime.get(Calendar.HOUR_OF_DAY),
  					endtime.get(Calendar.MINUTE),true).show();  
  		}
  	};
  	
  	
  	
  	private void updateStarttime()
	{
		if(tv_starttime!=null)
		{
			SpannableString msp_starttime = new SpannableString(time_fmt.format(starttime.getTime()));
			msp_starttime.setSpan(new UnderlineSpan(), 0,time_fmt.format(starttime.getTime()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_starttime.setText(msp_starttime);
			
		}
	}
	
	private void updateEndtime()
	{
		if(tv_endtime!=null)
		{
			SpannableString msp_endtime = new SpannableString(time_fmt.format(endtime.getTime()));
			msp_endtime.setSpan(new UnderlineSpan(), 0,time_fmt.format(endtime.getTime()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_endtime.setText(msp_endtime);
		}
	}
	
	
	
      
}
