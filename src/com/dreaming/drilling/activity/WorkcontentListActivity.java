package com.dreaming.drilling.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dreaming.drilling.R;
import com.dreaming.drilling.R.layout;
import com.dreaming.drilling.R.menu;
import com.dreaming.drilling.adapter.WorkcontentListviewAdapter;
import com.dreaming.drilling.bean.EntityTourreport;
import com.dreaming.drilling.db.TourreportDBHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;



/**
 * 班报列表的控制类
 * */

public class WorkcontentListActivity extends Activity implements OnClickListener{

	private String title_name = "班报列表";
	private ListView listview = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workcontent_list);
		initview();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workcontent_list, menu);
		return true;
	}
	
	
	/**
	 * 
	 * */
	private List<Map<String,Object>> getListItems()
	{
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		TourreportDBHelper tourreportDB = new TourreportDBHelper(this);
		List<EntityTourreport> list = tourreportDB.getAllTourreports();
		
		for(EntityTourreport entity:list)
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tourdate", entity.getTourdate());
			map.put("tourtime", entity.getStarttime() +"-" +entity.getEndtime());
			map.put("holenumber", entity.getHolenumber());
			map.put("drillingtime", entity.getTourdrillingtime());
			map.put("auxtime", entity.getTourauxiliarytime());
			map.put("holetime",entity.getHoleaccidenttime());
			map.put("devicetime", entity.getDeviceaccidenttime());
			map.put("othertime", entity.getOthertime());
			map.put("totaltime", entity.getTotaltime());
			map.put("tourshift", entity.getTourshift());
			map.put("tourcore", entity.getTourcore());
			map.put("currentdeep", entity.getCurrentdeep());
			result.add(map);
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * */
	public void initview()
	{
		TextView textView_title = (TextView)findViewById(R.id.title);
		textView_title.setText(title_name);
		
		
		
		
		
		// 填充 listview 的内容
		listview = (ListView) findViewById(R.id.workcontent_list);
		WorkcontentListviewAdapter adapter = new WorkcontentListviewAdapter(this,getListItems());
		listview.setAdapter(adapter);
		
		findViewById(R.id.menu_add_tourreport).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_list).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_report).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_setting).setOnClickListener(this);
		
		
	}

	
	
	
	private void open_workcontent() {
		Intent intent = new Intent(WorkcontentListActivity.this, WorkcontentActivity.class);
		startActivity(intent);

	}
	
	
	private void open_add_tourreport_window()
	{
		Intent intent = new Intent(WorkcontentListActivity.this,MainActivity.class);
		startActivity(intent);
	}
	
	private void open_tourreport_list_window()
	{
		Intent intent = new Intent(WorkcontentListActivity.this,WorkcontentListActivity.class);
		startActivity(intent);
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
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
	
}
