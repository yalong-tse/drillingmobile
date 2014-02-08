package com.dreaming.drilling.activity;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dreaming.drilling.R;
import com.dreaming.drilling.adapter.SpinAdapter;
import com.dreaming.drilling.bean.HoleDeployments;
import com.dreaming.drilling.bean.SpinnerData;
import com.dreaming.drilling.utils.GlobalConstants;
import com.dreaming.drilling.utils.RestClient;

public class DrillSettingsActivity extends FragmentActivity implements ServerDialogFragment.ServerDialogListener , OnClickListener{
	
	private static String DEBUG_TAG = "DrillSettingsActivity";
	protected SharedPreferences sharedPrefs;
	private SharedPreferences.Editor editor;
	
	private String server = "http://192.168.1.101:5000";
	private String contracturl = "/mobile/contracts.json";
	private String holeurl = "/mobile/contractholes.json?contractid=";
	private String peopleurl = "/mobile/getdeployments.json?holeid=";
	
	private TextView ip;
	private SpinAdapter adapter_contract;
	private SpinAdapter adapter_hole; 
	private Spinner spinner_contract;  // 合同spinner
	private Spinner spinner_hole;          // 钻孔spinner
	
	private TextView projectmanager;  // 项目经理
	private TextView holeleader;           // 机长
	private TextView tourleader1;         // 班长1
	private TextView tourleader2;         // 班长2
	private TextView tourleader3;         // 班长3
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildingPreference();
		setContentView(R.layout.activity_settings);
		
		TextView textView_title = (TextView) findViewById(R.id.title);
		textView_title.setText("个人设置");
		TextView serverip = (TextView) findViewById(R.id.text_server_ip_value);
//		this.sharedPrefs = this.getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		serverip.setText(this.sharedPrefs.getString("serverip",server));
		
		initview();
		
	}
	
	
	// 
	private void initview()
	{
		
		
		//Log.d("the contractslist size is ",GlobalConstants.contractslist.size() +"");
		//Log.d("the hostslist size is ",GlobalConstants.holelist.size() +"");
		
		//Toast.makeText(this, "111111111", Toast.LENGTH_SHORT).show();
		if(GlobalConstants.contractslist!=null)
		{
			//Toast.makeText(this, "the size is:" + GlobalConstants.contractslist.size(), Toast.LENGTH_SHORT).show();
			spinner_contract = (Spinner)findViewById(R.id.setting_spinner_contract);
			adapter_contract = new SpinAdapter(DrillSettingsActivity.this, R.drawable.drop_list_hover, GlobalConstants.contractslist);
			adapter_contract.setDropDownViewResource(R.drawable.custom_spinner);
			spinner_contract.setAdapter(adapter_contract);
		}
		
		if(GlobalConstants.holelist!=null)
		{
			spinner_hole = (Spinner) findViewById(R.id.setting_spinner_hole);
			adapter_hole = new SpinAdapter(DrillSettingsActivity.this, R.drawable.drop_list_hover, GlobalConstants.holelist);
			adapter_hole.setDropDownViewResource(R.drawable.custom_spinner);
			spinner_hole.setAdapter(adapter_hole);
		}
		
		
		ip = (TextView) findViewById(R.id.text_server_ip_value);
		ip.setText(server);
		
		LinearLayout server_ip_layout = (LinearLayout)findViewById(R.id.layout_server_ip);
		Button btn_getContract = (Button) findViewById(R.id.btn_getcontract);
		server_ip_layout.setOnClickListener(server_ip_listener);
		btn_getContract.setOnClickListener(contract_listener);
		
		findViewById(R.id.menu_add_tourreport).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_list).setOnClickListener(this);
		//findViewById(R.id.menu_tourreport_report).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_setting).setOnClickListener(this);
		
	}
	
	
	private OnClickListener server_ip_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			showAlertDialog();
		}
	};
	
	private OnClickListener contract_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getContract(v);
		}
	};

	
	private void showAlertDialog() {
		
		if(ip==null)
			ip = (TextView) findViewById(R.id.text_server_ip_value);
		
		DialogFragment dialog = ServerDialogFragment.newInstance(ip.getText().toString());
		
//		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		
		dialog.show(getSupportFragmentManager(), "server ip");
	}
	
	/**
	 * 获取合同列表
	 */
	private void getContract(View v) {
		
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()) {
			// fetch data
			new FetchDataTask().execute(server+contracturl);
		} else {
			// display error
			Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
		}
		
	}

	
	// The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the ServerDialogFragment.ServerDialogListener interface
	// 
	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String editText) {
		ip = (TextView) findViewById(R.id.text_server_ip_value);
		ip.setText(editText);
		server = editText;
//		Log.d("-------------------------------------------------", server);
		//GlobalConstants.fetchurl = server;
		
		editor = getPreference();
		editor.putString("serverip", server);
		editor.commit();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// User touched the dialog's negative button
		
	}
	
	private class FetchDataTask extends AsyncTask<String, Void, List<SpinnerData>> {

		@Override
		protected List<SpinnerData> doInBackground(String... urls) {
			ArrayList<SpinnerData> list = RestClient.populate(urls[0], "id", "name");
			return list;
		}
		
		@Override
		protected void onPostExecute(List<SpinnerData> result) {
			
			if(spinner_contract==null)
				spinner_contract = (Spinner)findViewById(R.id.setting_spinner_contract);
			adapter_contract = new SpinAdapter(DrillSettingsActivity.this, R.drawable.drop_list_hover, result);
			
			GlobalConstants.contractslist = result;
			
			// Specify the layout to use when the list of choices appears
			adapter_contract.setDropDownViewResource(R.drawable.custom_spinner);
			
			// Apply the adapter to the spinner
			spinner_contract.setAdapter(adapter_contract);
			
			spinner_contract.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// An item was selected. You can retrieve the selected item using
			        // parent.getItemAtPosition(position)
					
					SpinnerData data = adapter_contract.getItem(position);
					//Log.d(DEBUG_TAG, "合同id："+data.getId()+";合同名称："+data.getName());
					TextView tv = (TextView)view;  
	                tv.setTextColor(getResources().getColor(R.color.black));    //设置颜色  
	                tv.setTextSize(15.0f);    //设置大小  
					editor = getPreference();
					editor.putString("contractid", data.getId());
					editor.putString("contractname", data.getName());
					editor.commit();
					
					
					new FetchHoleDataTask().execute(server+holeurl+data.getId());
					
					/*// 填充钻孔列表
					spinner_hole = (Spinner) findViewById(R.id.setting_spinner_hole);
					ArrayList<SpinnerData> holelist = RestClient.populate(server+holeurl+data.getId(), "id", "holenumber");
					
					adapter_hole = new SpinAdapter(DrillSettingsActivity.this, android.R.layout.simple_spinner_item, holelist);
					
					// Specify the layout to use when the list of choices appears
					adapter_hole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					
					// Apply the adapter to the spinner
					spinner_hole.setAdapter(adapter_hole);
					
					spinner_hole.setOnItemSelectedListener(spinnerhole_listener);*/
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// Another interface callback
					
				}
			});
		}
		
	}
	
	private class FetchHoleDataTask extends AsyncTask<String, Void, List<SpinnerData>> {

		@Override
		protected List<SpinnerData> doInBackground(String... urls) {
			ArrayList<SpinnerData> list = RestClient.populate(urls[0], "id", "holenumber");
			return list;
		}
		
		@Override
		protected void onPostExecute(List<SpinnerData> result) {
			// 填充钻孔列表
			if(spinner_hole==null)
				spinner_hole = (Spinner) findViewById(R.id.setting_spinner_hole);
			adapter_hole = new SpinAdapter(DrillSettingsActivity.this, R.drawable.drop_list_hover, result);
			
			GlobalConstants.holelist = result;
			
			// Specify the layout to use when the list of choices appears
			adapter_hole.setDropDownViewResource(R.drawable.custom_spinner);
			
			// Apply the adapter to the spinner
			spinner_hole.setAdapter(adapter_hole);
			
			spinner_hole.setOnItemSelectedListener(spinnerhole_listener);
		}
	}
	
	private class FetchPeopleDataTask extends AsyncTask<String, Void, List<HoleDeployments>> {

		@Override
		protected List<HoleDeployments> doInBackground(String... urls) {
			List<HoleDeployments> list = RestClient.populate(urls[0]);
			return list;
		}
		
		@Override
		protected void onPostExecute(List<HoleDeployments> result) {
			editor = getPreference();
			
			// 获取该钻孔的人员配组，填充UI
			projectmanager = (TextView) findViewById(R.id.settings_projectmanager); 
			holeleader = (TextView) findViewById(R.id.settings_holeleader); 
			tourleader1 = (TextView) findViewById(R.id.settings_tourleader1); 
			tourleader2 = (TextView) findViewById(R.id.settings_tourleader2); 
			tourleader3 = (TextView) findViewById(R.id.settings_tourleader3); 
			
			projectmanager.setText(""); 
			holeleader.setText(""); 
			tourleader1.setText(""); 
			tourleader2.setText(""); 
			tourleader3.setText(""); 
			
			editor.remove("projectmanager_id"); editor.remove("projectmanager_name");
			editor.remove("holeleader_id"); editor.remove("holeleader_name");
			editor.remove("tourleader1_id"); editor.remove("tourleader1_name");
			editor.remove("tourleader2_id"); editor.remove("tourleader2_name");
			editor.remove("tourleader3_id"); editor.remove("tourleader3_name");
			
			int i = 1;
			for(HoleDeployments h : result) {
				PeopleType p = PeopleType.valueOf(h.getType().toUpperCase());
				switch(p) {
				case PROJECTMANAGER:
					projectmanager.setText(h.getName());
					editor.putString("projectmanager_id", h.getId());
					editor.putString("projectmanager_name", h.getName());
					break;
				case HOLELEADER:
					holeleader.setText(h.getName());
					editor.putString("holeleader_id", h.getId());
					editor.putString("holeleader_name", h.getName());
					break;
				case TOURLEADER:
					if(i == 1) {
						tourleader1.setText(h.getName());
						editor.putString("tourleader1_id", h.getId());
						editor.putString("tourleader1_name", h.getName());
					} else if ( i == 2) {
						tourleader2.setText(h.getName());
						editor.putString("tourleader2_id", h.getId());
						editor.putString("tourleader2_name", h.getName());
					} else if (i == 3) {
						tourleader3.setText(h.getName());
						editor.putString("tourleader3_id", h.getId());
						editor.putString("tourleader3_name", h.getName());
					}
					i++;
					break;
				}
			}
			
			// 保存到sharedpreference
			
			editor.commit();
		}
		
	}

	public SharedPreferences.Editor getPreference() {
		this.sharedPrefs = this.getSharedPreferences(
				GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		return this.sharedPrefs.edit();
	}
	
	private OnItemSelectedListener spinnerhole_listener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			
			SpinnerData data1 = adapter_hole.getItem(position);
			Log.d(DEBUG_TAG, "钻孔id："+data1.getId()+";钻孔holenumber："+data1.getName());
			
			new FetchPeopleDataTask().execute(server+peopleurl+data1.getId());  // 获取项目经理、机长、班长
			
			TextView tv = (TextView)view;  
            tv.setTextColor(getResources().getColor(R.color.black));    //设置颜色  
            tv.setTextSize(15.0f);    //设置大小  
            
			editor = getPreference();
			// 保存到sharedpreference
			editor.putString("holeid", data1.getId());
			editor.putString("holenumber", data1.getName());
						
			editor.commit();
			
			/*
			// 获取该钻孔的人员配组，填充UI
			List<HoleDeployments> holelist = RestClient.populate(server+peopleurl+data1.getId());
			projectmanager = (TextView) findViewById(R.id.settings_projectmanager); 
			holeleader = (TextView) findViewById(R.id.settings_holeleader); 
			tourleader1 = (TextView) findViewById(R.id.settings_tourleader1); 
			tourleader2 = (TextView) findViewById(R.id.settings_tourleader2); 
			tourleader3 = (TextView) findViewById(R.id.settings_tourleader3); 
			
			projectmanager.setText(""); 
			holeleader.setText(""); 
			tourleader1.setText(""); 
			tourleader2.setText(""); 
			tourleader3.setText(""); 
			
			editor.remove("projectmanager_id"); editor.remove("projectmanager_name");
			editor.remove("holeleader_id"); editor.remove("holeleader_name");
			editor.remove("tourleader1_id"); editor.remove("tourleader1_name");
			editor.remove("tourleader2_id"); editor.remove("tourleader2_name");
			editor.remove("tourleader3_id"); editor.remove("tourleader3_name");
			
			int i = 1;
			for(HoleDeployments h : holelist) {
				PeopleType p = PeopleType.valueOf(h.getType().toUpperCase());
				switch(p) {
				case PROJECTMANAGER:
					projectmanager.setText(h.getName());
					editor.putString("projectmanager_id", h.getId());
					editor.putString("projectmanager_name", h.getName());
					break;
				case HOLELEADER:
					holeleader.setText(h.getName());
					editor.putString("holeleader_id", h.getId());
					editor.putString("holeleader_name", h.getName());
					break;
				case TOURLEADER:
					if(i == 1) {
						tourleader1.setText(h.getName());
						editor.putString("tourleader1_id", h.getId());
						editor.putString("tourleader1_name", h.getName());
					} else if ( i == 2) {
						tourleader2.setText(h.getName());
						editor.putString("tourleader2_id", h.getId());
						editor.putString("tourleader2_name", h.getName());
					} else if (i == 3) {
						tourleader3.setText(h.getName());
						editor.putString("tourleader3_id", h.getId());
						editor.putString("tourleader3_name", h.getName());
					}
					i++;
					break;
				}
			}
			*/
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
		
	};
	
	public void buildingPreference() {
		this.sharedPrefs = this.getSharedPreferences(
				GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);

		server = this.sharedPrefs.getString("serverip",server);
		
		//Log.d("the server is ","the server is :" + server);
		//Log.d("test","test");
		
		if (!this.sharedPrefs.getBoolean("first2", true))
			return;

		
		
		/*SharedPreferences.Editor localEditor = this.sharedPrefs.edit();

		// 系统所需的基本配置
		localEditor.putBoolean("first2", false);

		localEditor.putString("holenumber", "zk002");
		localEditor.putString("rignumber", "rigmachine002");

		localEditor.commit();*/

	}
	
	private void open_add_tourreport_window() {
		Intent intent = new Intent(DrillSettingsActivity.this, MainActivity.class);
		startActivity(intent);
	}

	private void open_tourreport_list_window() {
		Intent intent = new Intent(DrillSettingsActivity.this,
				WorkcontentListActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.menu_add_tourreport:
			open_add_tourreport_window();
			break;
		case R.id.menu_tourreport_list:
			open_tourreport_list_window();
			break;
		//case R.id.menu_tourreport_report:
		//	break;
			
		}
	}
}


enum PeopleType {
	PROJECTMANAGER,
	HOLELEADER,
	TOURLEADER
}
