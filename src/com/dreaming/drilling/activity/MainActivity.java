package com.dreaming.drilling.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.dreaming.drilling.bean.EntityTourreport;
import com.dreaming.drilling.bean.Takeovercontent;
import com.dreaming.drilling.bean.Workcontent;
import com.dreaming.drilling.db.TourreportDBHelper;
import com.dreaming.drilling.db.WorkcontentDBHelper;
import com.dreaming.drilling.utils.BizUtils;
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
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * 班报填报的界面，也是主界面
 * */
public class MainActivity extends Activity implements OnClickListener {

	private String title_name = "班报填写";

	protected SharedPreferences sharedPrefs;

	// 获取一个日历对象
	//private Calendar starttime = Calendar.getInstance(Locale.CHINA);
	//private Calendar endtime = Calendar.getInstance(Locale.CHINA);

	private SimpleDateFormat date_fmt = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat time_fmt = new SimpleDateFormat("HH:mm");

	private TextView tv_date;
	private Spinner spinner_starttime;
	private Spinner spinner_endtime;

	String[] tourtime = {"00:00","8:00","16:00","12:00"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buildingPreference();
		initView();
		buildingWorkcontent();
		building_takeover_content();
		computelength();
		try
		{
			computetime();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
		spinner_starttime = (Spinner) findViewById(R.id.tourreport_starttime_value);
		spinner_endtime = (Spinner) findViewById(R.id.tourreport_endtime_value);

		updateDate();
		
		ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tourtime);
		timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_starttime.setAdapter(timeAdapter);
		spinner_endtime.setAdapter(timeAdapter);
		
		updateStarttime();
		updateEndtime();

		spinner_starttime.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 String value=parent.getItemAtPosition(position).toString();
				 GlobalConstants.tour_starttime = value;
				 if(position==tourtime.length-1)
				 {
					 String end_value = parent.getItemAtPosition(0).toString();
					 spinner_endtime.setSelection(0);
				 }
				 else
				 {
					 String end_value = parent.getItemAtPosition(position+1).toString();
					 spinner_endtime.setSelection(position+1);
				 }
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		spinner_endtime.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 String value=parent.getItemAtPosition(position).toString();
				 GlobalConstants.tour_endtime = value;
				 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
			
		});		
		
		tv_date.setOnClickListener(dp_click);
		//tv_starttime.setOnClickListener(tp_starttime_click);
		//tv_endtime.setOnClickListener(tp_endtime_click);

		findViewById(R.id.tourreport_add_takeover).setOnClickListener(this);
		findViewById(R.id.tourreport_add_workcontent).setOnClickListener(this);
		findViewById(R.id.tourreport_btn_submit).setOnClickListener(this);

		findViewById(R.id.menu_add_tourreport).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_list).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_report).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_setting).setOnClickListener(this);
		

	}

	// 动态增加控件的方法
	private void buildingWorkcontent() {
		// Workcontent wc =
		// getIntent().getParcelableExtra(GlobalConstants.WORKCONTENT);
		LinearLayout linelayout = (LinearLayout) findViewById(R.id.main_workcontent_container);
		for (Workcontent wc : GlobalConstants.list_workcontents)
			if (wc != null) {
				// 列表中添加
				// GlobalConstants.list_workcontents.add(wc);
				TableLayout tl = new TableLayout(this);
				tl.setBackgroundResource(R.drawable.bg_layerlist);
				tl.setStretchAllColumns(true);
				tl.setShrinkAllColumns(true);

				// 第一行
				TableRow tr1 = new TableRow(this);
				// tr1.setLayoutParams(new
				// LayoutParams(LayoutParams.MATCH_PARENT,80));
				// Button btn_del = new Button(this);
				TextView tv_del = new TextView(this);
				// btn_del.setText("删除");
				// image.setLayoutParams(new
				// LayoutParams(LayoutParams.WRAP_CONTENT,
				// LayoutParams.WRAP_CONTENT));
				// btn_del.setTag(wc);
				tv_del.setTag(wc);
				tv_del.setText("删除");
				tv_del.setTextSize(15);
				// tv_del.setLayoutParams(new
				// LayoutParams(LayoutParams.WRAP_CONTENT,
				// LayoutParams.WRAP_CONTENT));
				tv_del.setPadding(15, 10, 0, 10);
				Drawable drawable_left = this.getResources().getDrawable(R.drawable.delete);
				drawable_left.setBounds(0, 0, drawable_left.getMinimumWidth(), drawable_left.getMinimumHeight());//必须设置图片大小，否则不显示
				tv_del.setCompoundDrawables(drawable_left, null, null, null);
				tv_del.setTextColor(Color.RED);
				tv_del.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						TextView btn = (TextView) view;
						Workcontent obj = (Workcontent) btn.getTag();
						((TableLayout) view.getParent().getParent())
								.removeAllViews();
						GlobalConstants.remove_workcontent(obj);
					}
				});

				tr1.addView(tv_del);

				// 编辑按钮的操作
				TextView tv_modify = new TextView(this);
				tv_modify.setTag(wc);
				tv_modify.setText("编辑");
				tv_modify.setTextSize(15);
				tv_modify.setTextColor(Color.RED);
				Drawable drawable_left2 = this.getResources().getDrawable(R.drawable.edit);
				drawable_left2.setBounds(0, 0, drawable_left.getMinimumWidth(), drawable_left2.getMinimumHeight());//必须设置图片大小，否则不显示
				tv_modify.setCompoundDrawables(drawable_left2, null, null, null);
				tv_modify.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) view;
						Workcontent obj = (Workcontent) tv.getTag();
						GlobalConstants.the_workcontent = obj;
						Intent intent = new Intent(MainActivity.this, WorkcontentActivity.class);
						startActivity(intent);
					}
				});
				
				tr1.addView(tv_modify);
				tl.addView(tr1, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50));
				
				
				// 第二行
				TableRow tr2 = new TableRow(this);
				
				TextView tv_time = new TextView(this);
				tv_time.setPadding(15, 0, 0, 10);
				tv_time.setTextColor(Color.BLACK);
				tv_time.setText("时间:" + wc.getStarttime() + "至"
						+ wc.getEndtime());
				tv_time.setTextSize(15);
				tr2.addView(tv_time);

				TextView tv_content = new TextView(this);
				tv_content.setTextColor(Color.BLACK);
				tv_content.setTextSize(15);
				tv_content.setText("内容:" + wc.getType());
				//tv_content.
				tr2.addView(tv_content);
				tl.addView(tr2, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50));
				
				

				// 第三行
				TableRow tr3 = new TableRow(this);
				if (wc.getUpleft() != 0) {
					TextView tv_upleft = new TextView(this);
					tv_upleft.setText("上余:" + wc.getUpleft());
					tv_upleft.setPadding(15, 0, 0, 10);
					tv_upleft.setTextColor(Color.BLACK);
					tr3.addView(tv_upleft);
				}

				if (wc.getDrillinglength() != 0) {
					TextView tv_drillinglength = new TextView(this);
					tv_drillinglength.setTextColor(Color.BLACK);
					tv_drillinglength.setText("进尺:" + wc.getDrillinglength());
					tr3.addView(tv_drillinglength);
				}

				if (wc.getHoledeep() != 0) {
					TextView tv_holedeep = new TextView(this);
					tv_holedeep.setTextColor(Color.BLACK);
					tv_holedeep.setText("孔深:" + wc.getHoledeep());
					tr3.addView(tv_holedeep);
				}

				if (wc.getCorelength() != 0) {
					TextView tv_core = new TextView(this);
					tv_core.setTextColor(Color.BLACK);
					tv_core.setText("岩心长度:" + wc.getCorelength());
					tr3.addView(tv_core);
				}
				tl.addView(tr3, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50));

				
				// 第四行
				TableRow tr4 = new TableRow(this);
				
				if (wc.getPressure() != 0) {
					TextView tv_pressure = new TextView(this);
					tv_pressure.setText("钻压:" + wc.getPressure());
					tv_pressure.setPadding(15, 0, 0, 10);
					tr4.addView(tv_pressure);
				}

				if (wc.getRotatespeed() != 0) {
					TextView tv_speed = new TextView(this);
					tv_speed.setText("转速:" + wc.getRotatespeed());
					tr4.addView(tv_speed);
				}

				if (wc.getPump() != 0) {
					TextView tv_pump = new TextView(this);
					tv_pump.setText("泵量:" + wc.getPump());
					tr4.addView(tv_pump);
				}
				tr4.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,50));
				tl.addView(tr4);
				linelayout.addView(tl);
			}

	}

	private void building_takeover_content() {
		if (GlobalConstants.takeover != null) {
			((TextView) findViewById(R.id.tourreport_takeover_value))
					.setText(GlobalConstants.takeover.getTakeover_desc());
			((TextView) findViewById(R.id.tourreport_fangxie_value))
					.setText(GlobalConstants.takeover.getFangxie());
			((TextView) findViewById(R.id.tourreport_fuzheng_value))
					.setText(GlobalConstants.takeover.getFuzheng());
			((TextView) findViewById(R.id.tourreport_takeover_tools_value))
					.setText(GlobalConstants.takeover.getTakeovertools());
			((TextView) findViewById(R.id.tourreport_onduty_value))
					.setText(GlobalConstants.takeover.getOnduty());
			((TextView) findViewById(R.id.tourreport_tourleader_value))
					.setText(GlobalConstants.takeover.getTourleader_name());
		}
	}

	/**
	 * 计算长度的方法
	 * */
	private void computelength() {
		float sum_drilling = 0;
		float sum_core = 0;
		float sum_holedeep = 0;
		for (Workcontent wc : GlobalConstants.list_workcontents) {
			if (wc.getHoledeep() > sum_holedeep) {
				sum_holedeep = wc.getHoledeep();
			}
			sum_drilling += wc.getDrillinglength();
			sum_core += wc.getCorelength();
		}

		((TextView) findViewById(R.id.tourreport_core_length_value))
				.setText(sum_core + "");
		((TextView) findViewById(R.id.tourreport_drillinglength_value))
				.setText(sum_drilling + "");
		((TextView) findViewById(R.id.tourreport_holedeep_value))
				.setText(sum_holedeep + "");

	}

	/**
	 * 计算几个时长的方法
	 * */
	private void computetime() throws ParseException {
		long drilltime = 0;
		long auxtime = 0;
		long holeinner = 0;
		long devicetime = 0;
		long othertime = 0;
		long totaltime = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		for (Workcontent obj : GlobalConstants.list_workcontents) {
			String start_str = obj.getStarttime();
			String end_str = obj.getEndtime();

			Date start_date = sdf.parse(start_str);
			Date end_date = sdf.parse(end_str);
			long computedtime = ((end_date.getTime() - start_date.getTime()) / (60 * 1000));
			totaltime += computedtime;
			
			if(obj.getType().equalsIgnoreCase("钻进"))
			{
				drilltime += computedtime; 
			}
			else if(obj.getType().equalsIgnoreCase("起下钻取心") || obj.getType().equalsIgnoreCase("起钻取心")
					|| obj.getType().equalsIgnoreCase("起钻")
					|| obj.getType().equalsIgnoreCase("下钻")
					|| obj.getType().equalsIgnoreCase("取心"))
			{
				auxtime +=computedtime; 
			}
			else if(obj.getType().equalsIgnoreCase("孔内事故"))
			{
				holeinner += computedtime; 
			}
			else if(obj.getType().equalsIgnoreCase("设备事故"))
			{
				devicetime += computedtime;
			}
			else
			{
				othertime += computedtime;
			}
		}

		((TextView)findViewById(R.id.tourreport_drilling_time_value)).setText(BizUtils.formatTimespan(drilltime));
		((TextView)findViewById(R.id.tourreport_auxiliary_time_value)).setText(BizUtils.formatTimespan(auxtime));
		((TextView)findViewById(R.id.tourreport_holeinner_time_value)).setText(BizUtils.formatTimespan(holeinner));
		((TextView)findViewById(R.id.tourreport_device_repair_value)).setText(BizUtils.formatTimespan(devicetime));
		((TextView)findViewById(R.id.tourreport_other_time_value)).setText(BizUtils.formatTimespan(othertime));
		((TextView)findViewById(R.id.tourreport_summary_time_value)).setText(BizUtils.formatTimespan(totaltime));
		
		
	}

//	private TimePickerDialog.OnTimeSetListener tp_starttime_listener = new TimePickerDialog.OnTimeSetListener() {
//		@Override
//		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//			starttime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//			starttime.set(Calendar.MINUTE, minute);
//			updateStarttime();
//		}
//	};
//
//	private TimePickerDialog.OnTimeSetListener tp_endtime_listener = new TimePickerDialog.OnTimeSetListener() {
//		@Override
//		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//			endtime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//			endtime.set(Calendar.MINUTE, minute);
//			updateEndtime();
//		}
//	};

	// 当点击DatePickerDialog控件的设置按钮时，调用该方法
	private DatePickerDialog.OnDateSetListener dp_listener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// 修改日历控件的年，月，日
			// 这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
			GlobalConstants.tourdate.set(Calendar.YEAR, year);
			GlobalConstants.tourdate.set(Calendar.MONTH, monthOfYear);
			GlobalConstants.tourdate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			// 将页面TextView的显示更新为最新时间
			updateDate();
		}
	};

	private View.OnClickListener dp_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// 生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
			new DatePickerDialog(MainActivity.this, dp_listener,
					GlobalConstants.tourdate.get(Calendar.YEAR), GlobalConstants.tourdate.get(Calendar.MONTH),
					GlobalConstants.tourdate.get(Calendar.DAY_OF_MONTH)).show();
		}
	};

//	private View.OnClickListener tp_starttime_click = new View.OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			new TimePickerDialog(MainActivity.this, tp_starttime_listener,
//					starttime.get(Calendar.HOUR_OF_DAY),
//					starttime.get(Calendar.MINUTE), true).show();
//		}
//	};
//
//	private View.OnClickListener tp_endtime_click = new View.OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			new TimePickerDialog(MainActivity.this, tp_endtime_listener,
//					endtime.get(Calendar.HOUR_OF_DAY),
//					endtime.get(Calendar.MINUTE), true).show();
//		}
//	};

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
					date_fmt.format(GlobalConstants.tourdate.getTime()));
			msp_date.setSpan(new UnderlineSpan(), 0,
					date_fmt.format(GlobalConstants.tourdate.getTime()).length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_date.setText(msp_date);
		}

	}

	private void updateStarttime() {
		if (spinner_starttime != null) {
			for(int i=0; i<tourtime.length;i++)
			{
				if(tourtime[i].equalsIgnoreCase(GlobalConstants.tour_starttime))
				{
					spinner_starttime.setSelection(i);
					break;
				}
			}
			
			//spinner_starttime.setText(GlobalConstants.tour_starttime);

		}
	}

	private void updateEndtime() {
		if (spinner_endtime != null) {
			for(int i=0; i<tourtime.length;i++)
			{
				if(tourtime[i].equalsIgnoreCase(GlobalConstants.tour_endtime))
				{
					spinner_endtime.setSelection(i);
					break;
				}
			}
			//spinner_endtime.setText(msp_endtime);
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
		case R.id.tourreport_btn_submit:
			save_tourreport();
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
			open_setting_window();
			break;
		case R.id.tourreport_btn_reset:
			reset();
			break;
		}
	}

	/**
	 * 保存班报的方法
	 * 
	 * */
	private void save_tourreport()
	{
		EntityTourreport tr = new EntityTourreport();
		
		tr.setHolenumber(((TextView)findViewById(R.id.tourreport_holenumber_value)).getText().toString());
		tr.setTourdate(((TextView)findViewById(R.id.tourreport_date_value)).getText().toString());
		
		tr.setStarttime(GlobalConstants.tour_starttime);
		tr.setEndtime(GlobalConstants.tour_endtime);
		
		tr.setTakeoverremark(((TextView)findViewById(R.id.tourreport_takeover_value)).getText().toString());
		tr.setAntideviation(((TextView)findViewById(R.id.tourreport_fangxie_value)).getText().toString());
		tr.setCentralizer(((TextView)findViewById(R.id.tourreport_fuzheng_value)).getText().toString());
		tr.setInstrumenttakeover(((TextView)findViewById(R.id.tourreport_takeover_tools_value)).getText().toString());
		
		// 几个时间的统计
		tr.setTourdrillingtime(((TextView)findViewById(R.id.tourreport_drilling_time_value)).getText().toString());
		tr.setTourauxiliarytime(((TextView)findViewById(R.id.tourreport_auxiliary_time_value)).getText().toString());
		tr.setHoleaccidenttime(((TextView)findViewById(R.id.tourreport_holeinner_time_value)).getText().toString());
		tr.setDeviceaccidenttime(((TextView)findViewById(R.id.tourreport_device_repair_value)).getText().toString());
		tr.setOthertime(((TextView)findViewById(R.id.tourreport_other_time_value)).getText().toString());
		tr.setTotaltime(((TextView)findViewById(R.id.tourreport_summary_time_value)).getText().toString());
		
		// 进尺等工作量统计
		tr.setTourshift(Float.parseFloat(((TextView)findViewById(R.id.tourreport_drillinglength_value)).getText().toString()));
		tr.setTourcore(Float.parseFloat(((TextView)findViewById(R.id.tourreport_core_length_value)).getText().toString()));
		tr.setCurrentdeep(Float.parseFloat(((TextView)findViewById(R.id.tourreport_holedeep_value)).getText().toString()));
		
		// 保存项目经理、班长、记录员
		String projectmanager_id = this.sharedPrefs.getString("projectmanager_id", "");
		if(null != projectmanager_id && !"".equals(projectmanager_id)) {
			tr.setProjectmanager(projectmanager_id);
		}
		tr.setRecorder(((TextView)findViewById(R.id.tourreport_onduty_value)).getText().toString());
		if (GlobalConstants.takeover != null) {
			tr.setTourleader(GlobalConstants.takeover.getTourleader_id());
		}

		
		TourreportDBHelper db_tourreportHelper = new TourreportDBHelper(this);
		WorkcontentDBHelper db_workcontentHelper = new WorkcontentDBHelper(this);
		
		if(db_tourreportHelper.findIsExists(tr.getTourdate(),tr.getStarttime(), tr.getEndtime()))
		{
			Toast.makeText(this, "该班报已经存在，请确认?", Toast.LENGTH_LONG).show();
		}
		else
		{
			Long tourreportid = db_tourreportHelper.save(tr);
			
			for(Workcontent wc:GlobalConstants.list_workcontents)
			{
				wc.setTourreportid(tourreportid);
				db_workcontentHelper.save(wc);
			}
			
			Toast.makeText(this, "保存班报成功", Toast.LENGTH_LONG).show();
			open_tourreport_list_window();
		}
		
		
		
	}
	
	/**
	 * 打开工作内容
	 * */
	private void open_workcontent() {
		Intent intent = new Intent(MainActivity.this, WorkcontentActivity.class);
		startActivity(intent);

	}

	private void open_takeover() {
		Intent intent = new Intent(MainActivity.this, TakeoverActivity.class);
		startActivity(intent);
	}

	private void open_add_tourreport_window() {
		Intent intent = new Intent(MainActivity.this, MainActivity.class);
		startActivity(intent);
	}

	private void open_tourreport_list_window() {
		Intent intent = new Intent(MainActivity.this,
				WorkcontentListActivity.class);
		startActivity(intent);
	}

	private void open_setting_window()
	{
		Intent intent = new Intent(MainActivity.this, DrillSettingsActivity.class);
		startActivity(intent);
	}
	
	
	/**
	 * 重置操作的事件
	 * */
	private void reset()
	{
		GlobalConstants.list_workcontents.clear();
		GlobalConstants.takeover = new Takeovercontent();
	}
	
	
}
