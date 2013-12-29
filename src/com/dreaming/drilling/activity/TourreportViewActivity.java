package com.dreaming.drilling.activity;

import java.util.List;

import com.dreaming.drilling.R;
import com.dreaming.drilling.R.layout;
import com.dreaming.drilling.R.menu;
import com.dreaming.drilling.bean.EntityTourreport;
import com.dreaming.drilling.bean.Workcontent;
import com.dreaming.drilling.db.TourreportDBHelper;
import com.dreaming.drilling.db.WorkcontentDBHelper;
import com.dreaming.drilling.utils.GlobalConstants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 
 * 班报查看的方法
 * 
 * */
public class TourreportViewActivity extends Activity implements OnClickListener  {

	private String title_name = "班报详情";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourreport_view);
		
		initview();
		
		bottomMenuClickBinding();
	}

	/**
	 * 初始化视图
	 * */
	private void initview()
	{
		
		TextView textView_title = (TextView) findViewById(R.id.title);
		textView_title.setText(title_name);
		
		
		Bundle bundle = this.getIntent().getBundleExtra(GlobalConstants.TOURREPORTID);
		String tourreportid = bundle.getString("tourreportid");
		
		
		TourreportDBHelper db_tourreportHelper = new TourreportDBHelper(this);
		WorkcontentDBHelper db_workcontentHelper = new WorkcontentDBHelper(this);
		
		EntityTourreport tourreport = db_tourreportHelper.getTourreportById(tourreportid);
		List<Workcontent> workcontents = db_workcontentHelper.getWorkcontentByTourreportId(Long.parseLong(tourreportid));

		if(tourreport!=null)
		{
			// 矿区
			TextView tv_minearea = (TextView) findViewById(R.id.view_tourreportminearea_value);
			//tv_minearea.setText(tourreport.getMinearea());
			
			// 班报日期
			TextView tv_date = (TextView) findViewById(R.id.view_tourreport_date_value);
			tv_date.setText(tourreport.getTourdate());
			
			// 开始时间
			TextView tv_starttime = (TextView) findViewById(R.id.view_tourreport_starttime_value);
			tv_starttime.setText(tourreport.getStarttime());
			
			// 结束时间
			TextView tv_endtime = (TextView) findViewById(R.id.view_tourreport_endtime_value);
			tv_endtime.setText(tourreport.getEndtime());
	
			// 钻孔编号
			TextView tv_holenumber = (TextView) findViewById(R.id.view_tourreport_holenumber_value);
			tv_holenumber.setText(tourreport.getHolenumber());
			
			// 钻机编号
			TextView tv_rigmahine_number = (TextView) findViewById(R.id.view_tourreport_rigmachine_number_value);
			tv_rigmahine_number.setText("");
			
			
			
			TextView tv_takeover = (TextView) findViewById(R.id.view_tourreport_takeover_value);
			tv_takeover.setText(tourreport.getTakeoverremark());
			
			TextView tv_fangxie = (TextView) findViewById(R.id.view_tourreport_fangxie_value);
			tv_takeover.setText(tourreport.getAntideviation());
	
			TextView tv_fuzheng = (TextView) findViewById(R.id.view_tourreport_fuzheng_value);
			tv_fuzheng.setText(tourreport.getCentralizer());
			
			
			TextView tv_takeover_tools = (TextView) findViewById(R.id.view_tourreport_takeover_tools_value);
			tv_takeover_tools.setText(tourreport.getInstrumenttakeover());
			
			
			TextView tv_onduty = (TextView) findViewById(R.id.view_tourreport_onduty_value);
			tv_onduty.setText(tourreport.getAdministrator() + "," + tourreport.getProjectmanager() +"," + tourreport.getRecorder() +","+ tourreport.getTourleader());
			
			
			TextView tv_drillingtime = (TextView) findViewById(R.id.view_tourreport_drilling_time_value);
			tv_drillingtime.setText(tourreport.getTourdrillingtime());
			
			TextView tv_auxtime = (TextView) findViewById(R.id.view_tourreport_auxiliary_time_value);
			tv_auxtime.setText(tourreport.getTourauxiliarytime());
			
			TextView tv_holetime = (TextView) findViewById(R.id.view_tourreport_holeinner_time_value);
			tv_holetime.setText(tourreport.getHoleaccidenttime());
			
			TextView tv_devicetime = (TextView) findViewById(R.id.view_tourreport_device_repair_value);
			tv_devicetime.setText(tourreport.getDeviceaccidenttime());
			
			TextView tv_othertime = (TextView) findViewById(R.id.view_tourreport_other_time_value);
			tv_othertime.setText(tourreport.getOthertime());
			
			
			TextView tv_totaltime = (TextView) findViewById(R.id.view_tourreport_summary_time_value);
			tv_totaltime.setText(tourreport.getTotaltime());
			
			TextView tv_drilllength = (TextView) findViewById(R.id.view_tourreport_drillinglength_value);
			tv_drilllength.setText(tourreport.getTourshift() +"");
			
			
			TextView tv_core = (TextView) findViewById(R.id.view_tourreport_core_length_value);
			tv_core.setText(tourreport.getTourcore() +"");
			
			
			TextView tv_lastdeep = (TextView) findViewById(R.id.view_tourreport_lastholedeep_value);
			tv_lastdeep.setText(tourreport.getLastdeep() +"");
			
			
			TextView tv_currentdeep = (TextView) findViewById(R.id.view_tourreport_currentholedeep_value);
			tv_currentdeep.setText(tourreport.getCurrentdeep() +"");
		}
		
		// 遍历工作内容，自动生成工作内容的方法
		buildingWorkcontent(workcontents);
		
	}
	
	
	/**
	 * 自动生成工作内容
	 * */
	private void buildingWorkcontent(List<Workcontent> workcontents)
	{
		LinearLayout linelayout = (LinearLayout) findViewById(R.id.view_main_workcontent_container);
		for (Workcontent wc : workcontents)
			if (wc != null) {
				// 列表中添加
				// GlobalConstants.list_workcontents.add(wc);
				TableLayout tl = new TableLayout(this);
				tl.setBackgroundResource(R.drawable.bg_layerlist);
				tl.setStretchAllColumns(true);
				tl.setShrinkAllColumns(true);

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
	
	private void bottomMenuClickBinding()
	{
		findViewById(R.id.menu_add_tourreport).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_list).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_report).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_setting).setOnClickListener(this);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tourreport_view, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
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
	
	
	private void open_add_tourreport_window() {
		Intent intent = new Intent(TourreportViewActivity.this, MainActivity.class);
		startActivity(intent);
	}

	private void open_tourreport_list_window() {
		Intent intent = new Intent(TourreportViewActivity.this,
				WorkcontentListActivity.class);
		startActivity(intent);
	}

	
}
