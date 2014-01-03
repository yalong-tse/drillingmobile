package com.dreaming.drilling.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.dreaming.drilling.R;
import com.dreaming.drilling.adapter.SpinAdapter;
import com.dreaming.drilling.bean.SpinnerData;
import com.dreaming.drilling.bean.Takeovercontent;
import com.dreaming.drilling.utils.GlobalConstants;

public class TakeoverActivity extends Activity implements OnClickListener{

	protected SharedPreferences sharedPrefs;
	private String title_name = "填写交接说明";
	private Spinner spinner;
	private SpinAdapter adapter; 
	
	private String tourleaderid;
	private String tourleadername;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takeover);
		
		initView();
		buildingcontent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.takeover, menu);
		return true;
	}
	
	
	private void initView() {
		TextView textView_title = (TextView) findViewById(R.id.title);
		textView_title.setText(title_name);
		spinner = (Spinner) findViewById(R.id.tourleader_id);
		
		
		findViewById(R.id.takeover_btn_save).setOnClickListener(this);
		findViewById(R.id.takeover_btn_cancel).setOnClickListener(this);
		
		List<SpinnerData> result = populateTourLeader();
		
		adapter = new SpinAdapter(TakeoverActivity.this, android.R.layout.simple_spinner_item, result);
		
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(tourleader_listener);
	}
	
	private List<SpinnerData> populateTourLeader() {
		
		this.sharedPrefs = this.getSharedPreferences(
				GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		
		List<SpinnerData> result = new ArrayList<SpinnerData>();
		String tourleader1_id = this.sharedPrefs.getString("tourleader1_id", "");
		if(!tourleader1_id.equals("")) {
			String tourleader1_name = this.sharedPrefs.getString("tourleader1_name", "");
			result.add(new SpinnerData(tourleader1_id, tourleader1_name));
		}
		
		String tourleader2_id = this.sharedPrefs.getString("tourleader2_id", "");
		if(!tourleader2_id.equals("")) {
			String tourleader2_name = this.sharedPrefs.getString("tourleader2_name", "");
			result.add(new SpinnerData(tourleader2_id, tourleader2_name));
		}
		
		String tourleader3_id = this.sharedPrefs.getString("tourleader3_id", "");
		if(!tourleader3_id.equals("")) {
			String tourleader3_name = this.sharedPrefs.getString("tourleader3_name", "");
			result.add(new SpinnerData(tourleader3_id, tourleader3_name));
		}
		
		return result;
	}

	private void buildingcontent()
	{
	
		Takeovercontent to = GlobalConstants.takeover;
		TextView tv_takeoverdesc = (TextView) findViewById(R.id.takeover_desc);
		tv_takeoverdesc.setText(to.getTakeover_desc());
		
		TextView tv_fangxie = (TextView) findViewById(R.id.faxie_value);
		tv_fangxie.setText(to.getFangxie());
		
		TextView tv_fuzheng = (TextView) findViewById(R.id.fuzheng_value);
		tv_fuzheng.setText(to.getFuzheng());
		
		TextView tv_tools = (TextView) findViewById(R.id.takeover_tools_value);
		tv_tools.setText(to.getTakeovertools());
		
		TextView tv_onduty = (TextView) findViewById(R.id.onduty_value);
		tv_onduty.setText(to.getOnduty());
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.takeover_btn_save:
			btn_save();
			break;
		case R.id.takeover_btn_cancel:
			btn_cancel();
			break;
		}
	}
	
	
	private void btn_cancel()
	{
		Intent mIntent = new Intent(this,MainActivity.class);
		startActivity(mIntent);
	}
	
	
	private void btn_save()
	{
		Takeovercontent takeover =  GlobalConstants.takeover;
		TextView tv_takeoverdesc = (TextView) findViewById(R.id.takeover_desc);
		takeover.setTakeover_desc(tv_takeoverdesc.getText().toString());
		
		TextView tv_fangxie = (TextView) findViewById(R.id.faxie_value);
		takeover.setFangxie(tv_fangxie.getText().toString());
		
		TextView tv_fuzheng = (TextView) findViewById(R.id.fuzheng_value);
		takeover.setFuzheng(tv_fuzheng.getText().toString());
		
		TextView tv_tools = (TextView) findViewById(R.id.takeover_tools_value);
		takeover.setTakeovertools(tv_tools.getText().toString());
		
		TextView tv_onduty = (TextView) findViewById(R.id.onduty_value);
		takeover.setOnduty(tv_onduty.getText().toString());
		
		spinner = (Spinner) findViewById(R.id.tourleader_id);
		SpinnerData s = (SpinnerData)spinner.getSelectedItem();
		takeover.setTourleader_id(s.getId());
		takeover.setTourleader_name(s.getName());
		
		Intent mIntent = new Intent(this,MainActivity.class);
		startActivity(mIntent);
	}

	
	private OnItemSelectedListener tourleader_listener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			SpinnerData data1 = adapter.getItem(position);
			/*tourleaderid = data1.getId();
			tourleadername = data1.getName();*/
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	};
}
