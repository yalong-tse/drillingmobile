package com.dreaming.drilling.activity;

import com.dreaming.drilling.R;
import com.dreaming.drilling.R.layout;
import com.dreaming.drilling.R.menu;
import com.dreaming.drilling.bean.Takeovercontent;
import com.dreaming.drilling.utils.GlobalConstants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TakeoverActivity extends Activity implements OnClickListener{

	private String title_name = "填写交接说明";
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
		
		
		findViewById(R.id.takeover_btn_save).setOnClickListener(this);
		findViewById(R.id.takeover_btn_cancel).setOnClickListener(this);
		
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
		
		Intent mIntent = new Intent(this,MainActivity.class);
		startActivity(mIntent);
	}

}
