package com.dreaming.drilling.activity;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dreaming.drilling.R;
import com.dreaming.drilling.adapter.HoleAdapter;
import com.dreaming.drilling.adapter.SpinAdapter;
import com.dreaming.drilling.bean.HoleDeployments;
import com.dreaming.drilling.bean.HoleDetail;
import com.dreaming.drilling.bean.SpinnerData;
import com.dreaming.drilling.utils.GlobalConstants;
import com.dreaming.drilling.utils.RestClient;



public class DrillSettingsActivity extends FragmentActivity implements OnClickListener{
	
	private static String DEBUG_TAG = "DrillSettingsActivity";
	protected SharedPreferences sharedPrefs;
	private SharedPreferences.Editor editor;
	
	private String server;
	private String contracturl = "/mobile/contracts.json";
	private String holeurl = "/mobile/contractholes.json?contractid=";
	private String peopleurl = "/mobile/getdeployments.json?holeid=";
	private String detailurl = "/mobile/holedetail.json?holeid=";
	private String queryownholes_url ="/mobile/queryownholes.json?userid=";
	
	private TextView ip;
	private SpinAdapter adapter_contract;
	private SpinAdapter adapter_hole; 
	private HoleAdapter hole_adapter;
	private Spinner spinner_contract;  // 合同spinner
	private Spinner spinner_hole;          // 钻孔spinner
	
	private TextView projectmanager;  // 项目经理
	private TextView holeleader;           // 机长
	private TextView tourleader1;         // 班长1
	private TextView tourleader2;         // 班长2
	private TextView tourleader3;         // 班长3
	private TextView tv_minearea;  // 矿区
	private TextView tv_geologysituation; // 地层情况
	private TextView tv_rigmachine;  // 钻机
	private TextView tv_drilltower;  // 钻塔
	private TextView tv_pump;  // 泥浆泵
	
	
	private String http_str = "http://";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildingPreference();
		setContentView(R.layout.activity_settings);
		
		TextView textView_title = (TextView) findViewById(R.id.title);
		textView_title.setText("个人设置");
		
		//TextView serverip = (TextView) findViewById(R.id.text_server_ip_value);
		
		this.sharedPrefs = this.getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		
		server = this.sharedPrefs.getString("serverip",server);
		
		initview();
		
	}
	
	
	// 
	private void initview()
	{
		
		
		//Log.d("the contractslist size is ",GlobalConstants.contractslist.size() +"");
		//Log.d("the hostslist size is ",GlobalConstants.holelist.size() +"");
		
		//Toast.makeText(this, "111111111", Toast.LENGTH_SHORT).show();
		/*if(GlobalConstants.contractslist!=null)
		{
			//Toast.makeText(this, "the size is:" + GlobalConstants.contractslist.size(), Toast.LENGTH_SHORT).show();
			spinner_contract = (Spinner)findViewById(R.id.setting_spinner_contract);
			adapter_contract = new SpinAdapter(DrillSettingsActivity.this, R.drawable.drop_list_hover, GlobalConstants.contractslist);
			adapter_contract.setDropDownViewResource(R.drawable.custom_spinner);
			spinner_contract.setAdapter(adapter_contract);
		}
		*/
		
		
		if(GlobalConstants.holelist!=null)
		{
			
			//Log.d("DrillSettings", "111111111111111");
			spinner_hole = (Spinner) findViewById(R.id.setting_spinner_hole);
			
//			adapter_hole = new SpinAdapter(DrillSettingsActivity.this, R.drawable.drop_list_hover, GlobalConstants.holelist);
//			adapter_hole.setDropDownViewResource(R.drawable.custom_spinner);
//			spinner_hole.setAdapter(adapter_hole);
			
			hole_adapter = new HoleAdapter(this, GlobalConstants.holelist);
			spinner_hole.setAdapter(hole_adapter);
		}
		
		
		//Toast.makeText(DrillSettingsActivity.this, "result is:" + server+queryownholes_url+GlobalConstants.userid, Toast.LENGTH_LONG).show();
		
		
		new FetchHoleDataTask().execute(http_str+server+queryownholes_url+GlobalConstants.userid);
		
		//ip = (TextView) findViewById(R.id.text_server_ip_value);
		//ip.setText(server);
		
		//LinearLayout server_ip_layout = (LinearLayout)findViewById(R.id.layout_server_ip);
		//Button btn_getContract = (Button) findViewById(R.id.btn_getcontract);
		//server_ip_layout.setOnClickListener(server_ip_listener);
		//btn_getContract.setOnClickListener(contract_listener);
		
		
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
	
/*	private OnClickListener contract_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getContract(v);
		}
	};
*/
	
	private void showAlertDialog() {
		
		//if(ip==null)
		//	ip = (TextView) findViewById(R.id.text_server_ip_value);
		
		DialogFragment dialog = ServerDialogFragment.newInstance(ip.getText().toString());
		
		//InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		
		dialog.show(getSupportFragmentManager(), "server ip");
	}
	
	/**
	 * 获取合同列表
	 */
//	private void getContract(View v) {
//		
//		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//		if(networkInfo != null && networkInfo.isConnected()) {
//			// fetch data
//			new FetchDataTask().execute(server+contracturl);
//		} else {
//			// display error
//			Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
//		}
//		
//	}

	
	// The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the ServerDialogFragment.ServerDialogListener interface
	// 
	//@Override
	//public void onDialogPositiveClick(DialogFragment dialog, String editText) {
	//	ip = (TextView) findViewById(R.id.text_server_ip_value);
	//	ip.setText(editText);
	//	server = editText;
	//Log.d("-------------------------------------------------", server);
	//GlobalConstants.fetchurl = server;
		
	//	editor = getPreference();
	//	editor.putString("serverip", server);
	//	editor.commit();
	//}

	/*private class FetchDataTask extends AsyncTask<String, Void, List<SpinnerData>> {

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
					
					// 填充钻孔列表
					spinner_hole = (Spinner) findViewById(R.id.setting_spinner_hole);
					ArrayList<SpinnerData> holelist = RestClient.populate(server+holeurl+data.getId(), "id", "holenumber");
					
					adapter_hole = new SpinAdapter(DrillSettingsActivity.this, android.R.layout.simple_spinner_item, holelist);
					
					// Specify the layout to use when the list of choices appears
					adapter_hole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					
					// Apply the adapter to the spinner
					spinner_hole.setAdapter(adapter_hole);
					
					spinner_hole.setOnItemSelectedListener(spinnerhole_listener);
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// Another interface callback
					
				}
			});
		}
		
	}*/
	
	private class FetchHoleDataTask extends AsyncTask<String, Void, List<SpinnerData>> {

		@Override
		protected List<SpinnerData> doInBackground(String... urls) {
			//Toast.makeText(DrillSettingsActivity.this, urls[0], Toast.LENGTH_LONG).show();
			ArrayList<SpinnerData> list = RestClient.getHolelist(urls[0]);
			return list;
		}
		
		@Override
		protected void onPostExecute(List<SpinnerData> result) {
			Log.d("DrillSettings", "2222222222222222222");
			// 填充钻孔列表
			if(spinner_hole==null)
				spinner_hole = (Spinner) findViewById(R.id.setting_spinner_hole);
			
			hole_adapter = new HoleAdapter(DrillSettingsActivity.this, result);
			spinner_hole.setAdapter(hole_adapter);
			
//			adapter_hole = new SpinAdapter(DrillSettingsActivity.this, R.drawable.drop_list_hover, result);
			
			GlobalConstants.holelist = result;
			
			// Specify the layout to use when the list of choices appears
//			adapter_hole.setDropDownViewResource(R.drawable.custom_spinner);
			
			// Apply the adapter to the spinner
//			spinner_hole.setAdapter(adapter_hole);
			
			spinner_hole.setOnItemSelectedListener(spinnerhole_listener);
		}
	}
	
	private class FetchHoleDetail extends AsyncTask<String,Void, HoleDetail>{

		@Override
		protected HoleDetail doInBackground(String... urls) {

			HoleDetail detail = RestClient.populateHoleDetail(urls[0]);
			
			return detail;
		}
		
		@Override
		protected void onPostExecute(HoleDetail result)
		{
			//Toast.makeText(DrillSettingsActivity.this, "result is:" +result.getMinearea(), Toast.LENGTH_LONG).show();
			
			editor = getPreference();
			editor.remove("minearea");
			editor.remove("geologysituation");
			editor.putString("minearea", result.getMinearea());
			editor.putString("geologysituation", result.getGeologysituation());
			editor.putString("outerflag",result.getOutferlag());
			editor.commit();
			
			tv_minearea = (TextView) findViewById(R.id.settings_minearea);
			tv_geologysituation = (TextView) findViewById(R.id.settings_geologysituation);
			
			tv_minearea.setText(result.getMinearea());
			tv_geologysituation.setText(result.getGeologysituation());
			
		}
		

		
	}
	
	private class FetchDeploymentDataTask extends AsyncTask<String, Void, List<HoleDeployments>> {

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
			tv_rigmachine = (TextView) findViewById(R.id.settings_rigmachine);
			tv_drilltower = (TextView) findViewById(R.id.settings_drilltower);
			tv_pump = (TextView) findViewById(R.id.settings_pump);
			
			
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
			editor.remove("rigmachine_id");editor.remove("rigmachine_devicenumber");
			editor.remove("drilltower_id");editor.remove("drilltower_devicenumber");
			editor.remove("pump_id");editor.remove("pump_devicenumber");
			
			int i = 1;
			for(HoleDeployments h : result) {
				DeploymentType p = DeploymentType.valueOf(h.getType().toUpperCase());
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
					
				case RIGMACHINE:
					tv_rigmachine.setText(h.getName());
					editor.putString("rigmachine_id", h.getId());
					editor.putString("rigmachine_devicenumber", h.getName());
					break;
					
				case DRILLTOWER:
					tv_drilltower.setText(h.getName());
					editor.putString("drilltower_id", h.getId());
					editor.putString("drilltower_devicenumber", h.getName());
					break;
					
				case PUMP:
					tv_pump.setText(h.getName());
					editor.putString("pump_id", h.getId());
					editor.putString("pump_devicenumber", h.getName());
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
			
//			SpinnerData data1 = adapter_hole.getItem(position);
			SpinnerData data1 = (SpinnerData) hole_adapter.getItem(position);
			Log.d(DEBUG_TAG, "钻孔id："+data1.getId()+";钻孔holenumber："+data1.getName());
			
			new FetchDeploymentDataTask().execute(http_str+server+peopleurl+data1.getId());  // 获取项目经理、机长、班长
			
			//Toast.makeText(DrillSettingsActivity.this, "url is:" +server+detailurl+data1.getId(), Toast.LENGTH_LONG).show();
			new FetchHoleDetail().execute(http_str+server+detailurl+data1.getId());  // 获取钻孔的详细情况
			
//			TextView tv = (TextView)view;  
//            tv.setTextColor(getResources().getColor(R.color.black));    //设置颜色  
//            tv.setTextSize(15.0f);    //设置大小  
            
			editor = getPreference();
			// 保存到sharedpreference
			editor.putString("holeid", data1.getId());
			editor.putString("holenumber", data1.getName());
						
			editor.commit();
			
			
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


enum DeploymentType {
	PROJECTMANAGER,
	HOLELEADER,
	TOURLEADER,
	RIGMACHINE,
	DRILLTOWER,
	PUMP
}
