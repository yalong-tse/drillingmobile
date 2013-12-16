package com.dreaming.drilling.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.dreaming.drilling.bean.Workcontent;
import com.dreaming.drilling.utils.GlobalConstants;
import com.dreaming.drilling.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 班报填报的界面，也是主界面
 * */
public class MainActivity extends Activity implements OnClickListener {

	private String title_name = "班报填写";

	protected SharedPreferences sharedPrefs;

	// 获取一个日历对象
	private Calendar thedate = Calendar.getInstance(Locale.CHINA);
	private Calendar starttime = Calendar.getInstance(Locale.CHINA);
	private Calendar endtime = Calendar.getInstance(Locale.CHINA);

	private SimpleDateFormat date_fmt = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat time_fmt = new SimpleDateFormat("HH:mm");

	private TextView tv_date;
	private TextView tv_starttime;
	private TextView tv_endtime;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buildingPreference();
		initView();
		buildingWorkcontent();
		building_takeover_content();
		computelength();
	}

	private void initView() {
		TextView textView_title = (TextView) findViewById(R.id.title);
		textView_title.setText(title_name);

		// 孔号
		TextView textview_holenumber = (TextView) findViewById(R.id.tourreport_holenumber_value);
		String holenumber = this.sharedPrefs.getString("holenumber", "zk001");
		Log.d("holenumber", holenumber);
		// 设置字体样式
		SpannableString msp = new SpannableString(holenumber);
		// msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
		// holenumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
		msp.setSpan(new UnderlineSpan(), 0, holenumber.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview_holenumber.setText(msp);

		// 钻机编号
		TextView textview_rigmachine_number = (TextView) findViewById(R.id.tourreport_rigmachine_number_value);

		String rignumber = this.sharedPrefs.getString("rignumber",
				"rigmachine001");
		// 设置字体样式
		SpannableString msp_rig = new SpannableString(rignumber);
		// msp_rig.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
		// rignumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
		msp_rig.setSpan(new UnderlineSpan(), 0, rignumber.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview_rigmachine_number.setText(msp_rig);

		// 处理班报的日期和时间
		tv_date = (TextView) findViewById(R.id.tourreport_date_value);
		tv_starttime = (TextView) findViewById(R.id.tourreport_starttime_value);
		tv_endtime = (TextView) findViewById(R.id.tourreport_endtime_value);

		updateDate();
		updateStarttime();
		updateEndtime();

		tv_date.setOnClickListener(dp_click);
		tv_starttime.setOnClickListener(tp_starttime_click);
		tv_endtime.setOnClickListener(tp_endtime_click);

		findViewById(R.id.tourreport_add_takeover).setOnClickListener(this);
		findViewById(R.id.tourreport_add_workcontent).setOnClickListener(this);

		findViewById(R.id.menu_add_tourreport).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_list).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_report).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_setting).setOnClickListener(this);

	}
	
	
	// 动态增加控件的方法
	private void buildingWorkcontent()
	{
		//Workcontent wc = getIntent().getParcelableExtra(GlobalConstants.WORKCONTENT);
		LinearLayout linelayout = (LinearLayout) findViewById(R.id.main_workcontent_container);
		for(Workcontent wc: GlobalConstants.list_workcontents)
		if(wc!=null)
		{
			// 列表中添加
			//GlobalConstants.list_workcontents.add(wc);
			
			LayoutParams LP_FW = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			TableLayout tl = new TableLayout(this);
			tl.setBackgroundResource(R.drawable.bg_layerlist);
			tl.setLayoutParams(LP_FW);
			tl.setStretchAllColumns(true); 
			
			// 第一行
			TableRow tr1 = new TableRow(this);
			//tr1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,80));
			//Button btn_del = new Button(this);
			TextView tv_del = new TextView(this);
			//btn_del.setText("删除");
			//image.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//btn_del.setTag(wc);
			tv_del.setTag(wc);
			tv_del.setText("删除");
			tv_del.setTextSize(15);
			//tv_del.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tv_del.setPadding(15, 0, 0, 0);
			tv_del.setTextColor(Color.RED);
			tv_del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					TextView btn = (TextView) view;
					Workcontent obj = (Workcontent) btn.getTag();
					((TableLayout) view.getParent().getParent()).removeAllViews();
					GlobalConstants.remove_workcontent(obj);
				}
			});
			
			tr1.addView(tv_del);
			
			TextView tv_time = new TextView(this);
			tv_time.setTextColor(Color.BLACK);
			tv_time.setText("时间:"+ wc.getStarttime() +"至" + wc.getEndtime());
			tv_time.setTextSize(15);
			tr1.addView(tv_time);
			
			TextView tv_content = new TextView(this);
			tv_content.setTextColor(Color.BLACK);
			tv_content.setTextSize(15);
			tv_content.setText("工作内容:"+wc.getType());
			tr1.addView(tv_content);
			tl.addView(tr1,new LayoutParams(LayoutParams.MATCH_PARENT, 50));
			
			// 第二行
			TableRow tr2 = new TableRow(this);
			tr2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,40));
			if(wc.getUpleft()!=0)
			{
				TextView tv_upleft = new TextView(this);
				tv_upleft.setText("上余:" + wc.getUpleft());
				tv_upleft.setPadding(15, 0, 0, 0);
				tv_upleft.setTextColor(Color.BLACK);
				tr2.addView(tv_upleft);
			}
			
			if(wc.getDrillinglength()!=0)
			{
				TextView tv_drillinglength = new TextView(this);
				tv_drillinglength.setTextColor(Color.BLACK);
				tv_drillinglength.setText("进尺:"+ wc.getDrillinglength());
				tr2.addView(tv_drillinglength);
			}
			
			if(wc.getHoledeep()!=0)
			{
				TextView tv_holedeep = new TextView(this);
				tv_holedeep.setTextColor(Color.BLACK);
				tv_holedeep.setText("孔深:" + wc.getHoledeep());
				tr2.addView(tv_holedeep);
			}
			
			if(wc.getCorelength()!=0)
			{
				TextView tv_core = new TextView(this);
				tv_core.setTextColor(Color.BLACK);
				tv_core.setText("岩心长度:" + wc.getCorelength());
				tr2.addView(tv_core);
			}
			tl.addView(tr2,new LayoutParams(LayoutParams.MATCH_PARENT, 50));

			// 第三行
			TableRow tr3 = new TableRow(this);
			tr3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,40));
			if(wc.getPressure()!=0)
			{
				TextView tv_pressure = new TextView(this);
				tv_pressure.setText("钻压:"+wc.getPressure());
				tv_pressure.setPadding(15, 0, 0, 0);
				tr3.addView(tv_pressure);
			}
			
			if(wc.getRotatespeed()!=0)
			{
				TextView tv_speed = new TextView(this);
				tv_speed.setText("转速:"+ wc.getRotatespeed());
				tr3.addView(tv_speed);
			}
			
			if(wc.getPump()!=0)
			{
				TextView tv_pump = new TextView(this);
				tv_pump.setText("泵量:"+ wc.getPump());
				tr3.addView(tv_pump);
			}
			tl.addView(tr3);
			linelayout.addView(tl);
		}
		
	}

	
	private void building_takeover_content()
	{
		if(GlobalConstants.takeover!=null)
		{
			((TextView)findViewById(R.id.tourreport_takeover_value)).setText(GlobalConstants.takeover.getTakeover_desc());
			((TextView)findViewById(R.id.tourreport_fangxie_value)).setText(GlobalConstants.takeover.getFangxie());
			((TextView)findViewById(R.id.tourreport_fuzheng_value)).setText(GlobalConstants.takeover.getFuzheng());
			((TextView)findViewById(R.id.tourreport_takeover_tools_value)).setText(GlobalConstants.takeover.getTakeovertools());
			((TextView)findViewById(R.id.tourreport_onduty_value)).setText(GlobalConstants.takeover.getOnduty());
		}
	}
	
	private void computelength()
	{
		float sum_drilling=0;
		float sum_core=0;
		float sum_holedeep =0;
		for(Workcontent wc:GlobalConstants.list_workcontents)
		{
			if(wc.getHoledeep()>sum_holedeep)
			{
				sum_holedeep = wc.getHoledeep();
			}
			sum_drilling += wc.getDrillinglength();
			sum_core += wc.getCorelength();
		}
		
		((TextView)findViewById(R.id.tourreport_core_length_value)).setText(sum_core+"");
		((TextView)findViewById(R.id.tourreport_drillinglength_value)).setText(sum_drilling+"");
		((TextView)findViewById(R.id.tourreport_holedeep_value)).setText(sum_holedeep+"");
		
		
	}
	private TimePickerDialog.OnTimeSetListener tp_starttime_listener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			starttime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			starttime.set(Calendar.MINUTE, minute);
			updateStarttime();
		}
	};

	private TimePickerDialog.OnTimeSetListener tp_endtime_listener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			endtime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			endtime.set(Calendar.MINUTE, minute);
			updateEndtime();
		}
	};

	// 当点击DatePickerDialog控件的设置按钮时，调用该方法
	private DatePickerDialog.OnDateSetListener dp_listener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// 修改日历控件的年，月，日
			// 这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
			thedate.set(Calendar.YEAR, year);
			thedate.set(Calendar.MONTH, monthOfYear);
			thedate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			// 将页面TextView的显示更新为最新时间
			updateDate();
		}
	};

	private View.OnClickListener dp_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// 生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
			new DatePickerDialog(MainActivity.this, dp_listener,
					thedate.get(Calendar.YEAR), thedate.get(Calendar.MONTH),
					thedate.get(Calendar.DAY_OF_MONTH)).show();
		}
	};

	private View.OnClickListener tp_starttime_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			new TimePickerDialog(MainActivity.this, tp_starttime_listener,
					starttime.get(Calendar.HOUR_OF_DAY),
					starttime.get(Calendar.MINUTE), true).show();
		}
	};

	private View.OnClickListener tp_endtime_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			new TimePickerDialog(MainActivity.this, tp_endtime_listener,
					endtime.get(Calendar.HOUR_OF_DAY),
					endtime.get(Calendar.MINUTE), true).show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 系统的基本设置
	 * */
	public void buildingPreference() {
		this.sharedPrefs = this.getSharedPreferences(
				GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);

		if (!this.sharedPrefs.getBoolean("first2", true))
			return;

		SharedPreferences.Editor localEditor = this.sharedPrefs.edit();

		// 系统所需的基本配置
		localEditor.putBoolean("first2", false);

		localEditor.putString("holenumber", "zk002");
		localEditor.putString("rignumber", "rigmachine002");

		localEditor.commit();

	}

	private void updateDate() {
		if (tv_date != null) {
			SpannableString msp_date = new SpannableString(
					date_fmt.format(thedate.getTime()));
			msp_date.setSpan(new UnderlineSpan(), 0,
					date_fmt.format(thedate.getTime()).length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_date.setText(msp_date);
		}

	}

	private void updateStarttime() {
		if (tv_starttime != null) {
			SpannableString msp_starttime = new SpannableString(
					time_fmt.format(starttime.getTime()));
			msp_starttime.setSpan(new UnderlineSpan(), 0,
					time_fmt.format(starttime.getTime()).length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_starttime.setText(msp_starttime);

		}
	}

	private void updateEndtime() {
		if (tv_endtime != null) {
			SpannableString msp_endtime = new SpannableString(
					time_fmt.format(endtime.getTime()));
			msp_endtime.setSpan(new UnderlineSpan(), 0,
					time_fmt.format(endtime.getTime()).length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_endtime.setText(msp_endtime);
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tourreport_add_takeover:
			open_takeover();
			break;

		case R.id.tourreport_add_workcontent:
			open_workcontent();
			break;

		case R.id.menu_add_tourreport:
			open_add_tourreport_window();
			break;
		case R.id.menu_tourreport_list:
			open_tourreport_list_window();
			break;
		case R.id.menu_tourreport_report:
			break;
		case R.id.menu_tourreport_setting:
			break;
		}
	}

	private void open_workcontent() {
		Intent intent = new Intent(MainActivity.this, WorkcontentActivity.class);
		startActivity(intent);

	}
	
	
	private void open_takeover()
	{
		Intent intent = new Intent(MainActivity.this, TakeoverActivity.class);
		startActivity(intent);
	}
	
	
	private void open_add_tourreport_window()
	{
		Intent intent = new Intent(MainActivity.this,MainActivity.class);
		startActivity(intent);
	}
	
	private void open_tourreport_list_window()
	{
		Intent intent = new Intent(MainActivity.this,WorkcontentListActivity.class);
		startActivity(intent);
	}

}
