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
import android.view.Menu;
import android.widget.TextView;

/**
 * 
 * 班报查看的方法
 * 
 * */
public class TourreportViewActivity extends Activity {

	private String title_name = "班报查看";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourreport_view);
		
		initview();
		
		
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
		tv_onduty.setText(tourreport.getAdministrator() + "," + tourreport.getProjectmanager() +"," + tourreport.getRecorder());
		
		
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tourreport_view, menu);
		return true;
	}

}
