package com.dreaming.drilling.activity;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import com.dreaming.drilling.bean.SpinnerData;
import com.dreaming.drilling.utils.GlobalConstants;
import com.dreaming.drilling.utils.RestClient;

public class DrillSettingsActivity extends FragmentActivity implements ServerDialogFragment.ServerDialogListener{
	
	private static String DEBUG_TAG = "DrillSettingsActivity";
	protected SharedPreferences sharedPrefs;
	private SharedPreferences.Editor editor;
	
	private String server = "http://192.168.2.126:5000";
	private String contracturl = "/mobile/contracts.json";
	private String holeurl = "/mobile/contractholes.json?contractid=";
	private String peopleurl = "/mobile/getdeployments.json?holeid=";
	
	private TextView ip;
	private SpinAdapter adapter_contract;
	private SpinAdapter adapter_hole; 
	private Spinner spinner_contract;  // 合同spinner
	private Spinner spinner_hole;          // 钻孔spinner
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildingPreference();
		setContentView(R.layout.activity_settings);
		
		TextView textView_title = (TextView) findViewById(R.id.title);
		textView_title.setText("个人设置");
		TextView serverip = (TextView) findViewById(R.id.text_server_ip_value);
//		this.sharedPrefs = this.getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		serverip.setText(this.sharedPrefs.getString("serverip","http://192.168.1.116:5000"));
		
		
		LinearLayout server_ip_layout = (LinearLayout)findViewById(R.id.layout_server_ip);
		Button btn_getContract = (Button) findViewById(R.id.btn_getcontract);
		server_ip_layout.setOnClickListener(server_ip_listener);
		btn_getContract.setOnClickListener(contract_listener);
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
	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String editText) {
		ip = (TextView) findViewById(R.id.text_server_ip_value);
		ip.setText(editText);
		server = editText;
		
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
			
			spinner_contract = (Spinner)findViewById(R.id.setting_spinner_contract);
			adapter_contract = new SpinAdapter(DrillSettingsActivity.this, android.R.layout.simple_spinner_item, result);
			
			// Specify the layout to use when the list of choices appears
			adapter_contract.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			// Apply the adapter to the spinner
			spinner_contract.setAdapter(adapter_contract);
			
			spinner_contract.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// An item was selected. You can retrieve the selected item using
			        // parent.getItemAtPosition(position)
					
					SpinnerData data = adapter_contract.getItem(position);
					Log.d(DEBUG_TAG, "合同id："+data.getId()+";合同名称："+data.getName());
					
					editor = getPreference();
					editor.putString("contractid", data.getId());
					editor.putString("contractname", data.getName());
					editor.commit();
					
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
			
			editor = getPreference();
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

		if (!this.sharedPrefs.getBoolean("first2", true))
			return;

		/*SharedPreferences.Editor localEditor = this.sharedPrefs.edit();

		// 系统所需的基本配置
		localEditor.putBoolean("first2", false);

		localEditor.putString("holenumber", "zk002");
		localEditor.putString("rignumber", "rigmachine002");

		localEditor.commit();*/

	}
}
