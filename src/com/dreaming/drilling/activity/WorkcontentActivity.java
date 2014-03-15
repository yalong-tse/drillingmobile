package com.dreaming.drilling.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.dreaming.drilling.R;
import com.dreaming.drilling.R.layout;
import com.dreaming.drilling.R.menu;
import com.dreaming.drilling.bean.Workcontent;
import com.dreaming.drilling.db.TourreportDBHelper;
import com.dreaming.drilling.utils.BizUtils;
import com.dreaming.drilling.utils.GlobalConstants;

import android.os.Bundle;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class WorkcontentActivity extends Activity implements OnClickListener{

	private Calendar starttime = Calendar.getInstance(Locale.CHINA);
    private Calendar endtime = Calendar.getInstance(Locale.CHINA);

    private SimpleDateFormat time_fmt = new SimpleDateFormat("HH:mm");
    
    private TextView tv_starttime;
    private TextView tv_endtime;
    private Spinner spinner_workcontent;

    private String title_name = "工作内容";
    private String[] workcontent_arr = {"钻进","起下钻取心","起钻取心","起钻","下钻","取心","孔内事故","设备事故","停待","简易水文观测","封孔","其他"};
    private String currentselected;
    
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
		
		
		// 如果是编辑模式的话
		if(GlobalConstants.the_workcontent !=null)
		{
			
			//lasttime = GlobalConstants.the_workcontent.
			try
			{
				starttime.setTime(time_fmt.parse(GlobalConstants.the_workcontent.getStarttime()));
				endtime.setTime(time_fmt.parse(GlobalConstants.the_workcontent.getEndtime()));
				
				// 设置选项的方法
				String type = GlobalConstants.the_workcontent.getType();
				for(int i=0; i<workcontent_arr.length;i++)
				{
					if(workcontent_arr[i].equalsIgnoreCase(type))
					{
						spinner_workcontent.setSelection(i);
						break;
					}
				}
				
				((TextView)findViewById(R.id.sub_workcontent_core_length)).setText(GlobalConstants.the_workcontent.getCorelength()+"");
				((TextView)findViewById(R.id.sub_workcontent_upleft)).setText(GlobalConstants.the_workcontent.getUpleft()+"");
				((TextView)findViewById(R.id.sub_workcontent_holedeep)).setText(GlobalConstants.the_workcontent.getHoledeep()+"");
				((TextView)findViewById(R.id.sub_workcontent_drillinglength)).setText(GlobalConstants.the_workcontent.getDrillinglength()+"");
				((TextView)findViewById(R.id.sub_workcontent_pressure)).setText(GlobalConstants.the_workcontent.getPressure()+"");
				((TextView)findViewById(R.id.sub_workcontent_pump)).setText(GlobalConstants.the_workcontent.getPump()+"");
				((TextView)findViewById(R.id.sub_workcontent_rotatespeed)).setText(GlobalConstants.the_workcontent.getRotatespeed()+"");
				
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			String lasttime = BizUtils.getLastTime();
			try 
			{
				starttime.setTime(time_fmt.parse(lasttime));
				endtime.setTime(time_fmt.parse(lasttime));
				endtime.add(Calendar.HOUR, 1);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		updateStarttime();
		updateEndtime();
		
		tv_starttime.setOnClickListener(tp_starttime_click);
		tv_endtime.setOnClickListener(tp_endtime_click);
		
		ArrayAdapter<String> workcontent_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,workcontent_arr);
		workcontent_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_workcontent.setAdapter(workcontent_adapter);
		
		spinner_workcontent.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id){
				// TODO Auto-generated method stub
				String str=parent.getItemAtPosition(position).toString();
				currentselected = str;
				if(str.equalsIgnoreCase("起钻") || str.equalsIgnoreCase("下钻"))
				{
					findViewById(R.id.linelayout_corelength).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_upleft).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_holedeep).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_mudamount).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_coreleft).setVisibility(View.GONE);
					findViewById(R.id.linelayout_corenumber).setVisibility(View.GONE);
					findViewById(R.id.linelayout_drillinglength).setVisibility(View.GONE);
					findViewById(R.id.linelayout_pressure).setVisibility(View.GONE);
					findViewById(R.id.linelayout_pump).setVisibility(View.GONE);
					findViewById(R.id.linelayout_rotatespeed).setVisibility(View.GONE);
				}
				else if(str.equalsIgnoreCase("钻进"))
				{
					findViewById(R.id.linelayout_corelength).setVisibility(View.GONE);
					findViewById(R.id.linelayout_upleft).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_holedeep).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_drillinglength).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_pressure).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_pump).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_rotatespeed).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_mudamount).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_coreleft).setVisibility(View.GONE);
					findViewById(R.id.linelayout_corenumber).setVisibility(View.GONE);
				}
				else if(str.equalsIgnoreCase("起下钻取心") ||str.equalsIgnoreCase("起钻取心"))
				{
					findViewById(R.id.linelayout_corelength).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_coreleft).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_corenumber).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_upleft).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_holedeep).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_drillinglength).setVisibility(View.GONE);
					findViewById(R.id.linelayout_pressure).setVisibility(View.GONE);
					findViewById(R.id.linelayout_pump).setVisibility(View.GONE);
					findViewById(R.id.linelayout_rotatespeed).setVisibility(View.GONE);
					findViewById(R.id.linelayout_mudamount).setVisibility(View.VISIBLE);
					
					
				}
				else if(str.equalsIgnoreCase("取心"))
				{
					findViewById(R.id.linelayout_corelength).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_coreleft).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_corenumber).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_upleft).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_holedeep).setVisibility(View.VISIBLE);
					findViewById(R.id.linelayout_drillinglength).setVisibility(View.GONE);
					findViewById(R.id.linelayout_pressure).setVisibility(View.GONE);
					findViewById(R.id.linelayout_pump).setVisibility(View.GONE);
					findViewById(R.id.linelayout_rotatespeed).setVisibility(View.GONE);
					findViewById(R.id.linelayout_mudamount).setVisibility(View.VISIBLE);
					
				}
				else if(str.equalsIgnoreCase("孔内事故") || str.equalsIgnoreCase("设备事故") 
						|| str.equalsIgnoreCase("停待") || str.equalsIgnoreCase("简易水文观测") ||str.equalsIgnoreCase("其他"))
				{
					findViewById(R.id.linelayout_corelength).setVisibility(View.GONE);
					findViewById(R.id.linelayout_upleft).setVisibility(View.GONE);
					findViewById(R.id.linelayout_holedeep).setVisibility(View.GONE);
					findViewById(R.id.linelayout_drillinglength).setVisibility(View.GONE);
					findViewById(R.id.linelayout_pressure).setVisibility(View.GONE);
					findViewById(R.id.linelayout_pump).setVisibility(View.GONE);
					findViewById(R.id.linelayout_rotatespeed).setVisibility(View.GONE);
					findViewById(R.id.linelayout_mudamount).setVisibility(View.VISIBLE);
				}
				
				
				//Toast.makeText(WorkcontentActivity.this, "你点击的是:"+str, 2000).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		// 处理最新的孔深
		float holedeep = BizUtils.getLastholedeep();
		if(holedeep ==0 )
		{
			TourreportDBHelper db_tourreportHelper = new TourreportDBHelper(this);
			holedeep = db_tourreportHelper.getLastHoleDeep();
		}

		
		EditText et_deep = ((EditText)findViewById(R.id.sub_workcontent_holedeep));
		if(et_deep!=null) et_deep.setText(holedeep+"");
		findViewById(R.id.workcontent_btn_save).setOnClickListener(this);
		findViewById(R.id.workcontent_btn_cancel).setOnClickListener(this);
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
	
	
	

	/**
	 * 保存按钮把实体对象传递给主窗体
	 * **/
	private void btn_save()
	{
		Workcontent workcontent = null;
		if(GlobalConstants.the_workcontent==null)
		{
			workcontent = new Workcontent();
			updateContent(workcontent);
			GlobalConstants.list_workcontents.add(workcontent);
		}
		else
		{
			workcontent = GlobalConstants.the_workcontent;
			updateContent(workcontent);
		}
		
		GlobalConstants.the_workcontent=null;
		// 传递给主窗体
		Intent mIntent = new Intent(this,MainActivity.class);
		//Bundle mBundle = new Bundle();
		//mBundle.putParcelable(GlobalConstants.WORKCONTENT, workcontent);
		//mIntent.putExtras(mBundle);
		startActivity(mIntent);
		
	}
	private void btn_cancel()
	{
		GlobalConstants.the_workcontent=null;
		Intent mIntent = new Intent(this,MainActivity.class);
		startActivity(mIntent);
		
	}

	/**
	 * 更新workcontent
	 * */
	private void updateContent(Workcontent workcontent)
	{
		workcontent.setStarttime(((TextView) findViewById(R.id.sub_starttime_value)).getText().toString());
		workcontent.setEndtime(((TextView) findViewById(R.id.sub_endtime_value)).getText().toString());
		workcontent.setType(this.currentselected);
		
		String upleft = ((TextView) findViewById(R.id.sub_workcontent_upleft)).getText().toString();
		if(upleft==null || upleft.equalsIgnoreCase(""))
			workcontent.setUpleft("0");
		else
			workcontent.setUpleft(upleft);
		
		String drillinglength = ((TextView) findViewById(R.id.sub_workcontent_drillinglength)).getText().toString();
		if(drillinglength==null || drillinglength.equalsIgnoreCase(""))
			workcontent.setDrillinglength("0");
		else
			workcontent.setDrillinglength(drillinglength);
		
		String holedeep = ((TextView) findViewById(R.id.sub_workcontent_holedeep)).getText().toString();
		if(holedeep==null || holedeep.equalsIgnoreCase(""))
			workcontent.setHoledeep("0");
		else
			workcontent.setHoledeep(holedeep);
		
		String corelength = ((TextView) findViewById(R.id.sub_workcontent_core_length)).getText().toString();
		if(corelength==null || corelength.equalsIgnoreCase(""))
			workcontent.setCorelength("0");
		else
			workcontent.setCorelength(corelength);
				
		String pressure = ((TextView) findViewById(R.id.sub_workcontent_pressure)).getText().toString();
		if(pressure==null || pressure.equalsIgnoreCase(""))
			workcontent.setPressure("0");
		else
			workcontent.setPressure(pressure);
		
		String rotatespeed = ((TextView) findViewById(R.id.sub_workcontent_rotatespeed)).getText().toString();
		if(rotatespeed==null || rotatespeed.equalsIgnoreCase(""))
			workcontent.setRotatespeed("0");
		else
			workcontent.setRotatespeed(rotatespeed);
		
		String pump = ((TextView) findViewById(R.id.sub_workcontent_pump)).getText().toString();
		if(pump==null || pump.equalsIgnoreCase(""))
			workcontent.setPump("0");
		else
			workcontent.setPump(pump);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.workcontent_btn_save:
			btn_save();
			break;
		case R.id.workcontent_btn_cancel:
			btn_cancel();
			break;
		}
		
	}
	
      
}
