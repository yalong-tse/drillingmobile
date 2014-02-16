package com.dreaming.drilling.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dreaming.drilling.R;
import com.dreaming.drilling.adapter.WorkcontentListviewAdapter;
import com.dreaming.drilling.bean.EntityTourreport;
import com.dreaming.drilling.bean.Workcontent;
import com.dreaming.drilling.db.TourreportDBHelper;
import com.dreaming.drilling.db.WorkcontentDBHelper;
import com.dreaming.drilling.listview.XListView;
import com.dreaming.drilling.listview.XListView.IXListViewListener;
import com.dreaming.drilling.utils.GlobalConstants;



/**
 * 班报列表的控制类  ， 
 * 
 * 修改为 分页展示班报的功能呢
 * 
 * */

public class WorkcontentListActivity extends Activity implements OnClickListener,IXListViewListener {

	private static String DEBUG_TAG = "WorkcontentListActivity";
	private String title_name = "班报列表";
	private XListView listview = null;
	private Button btn_sync;
	private String serverip;
	private String sync_post_url = "/mobile/savetourreport.json";
	protected SharedPreferences sharedPrefs;
	private String holeid;
	private WorkcontentListviewAdapter adapter;
	
	//private ArrayList<String> items = new ArrayList<String>();
	private List<Map<String, Object>> items;
	
	private long currentpage = 1;
	private long pagesize = 5;
	
	// 动画特性
	private Animation animation;
	
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workcontent_list);
		initview();
		initAnimation();
		
	}
	

	private void initAnimation()
	{
		btn_sync = (Button) findViewById(R.id.btn_syn);
		animation = AnimationUtils.loadAnimation(this, R.drawable.round_loading);
		
		//btn_sync.startAnimation(animation);
		//btn_sync.setOnClickListener(cloudsync_listener);
		btn_sync.setOnClickListener(cloudsync_listener);

		/*animation = new AlphaAnimation(0.5f,0.5f);
		animation.setDuration(80000);
		animation.setRepeatCount(1);
		animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			
		});*/
		
	}
	
	private OnClickListener cloudsync_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if(networkInfo != null && networkInfo.isConnected()) {
				// post data
				//btn_sync.setAnimation(animation);
				//animation.startNow();
				//btn_sync.invalidate();
				animation.start();
				btn_sync.startAnimation(animation);
				new PostDataTask().execute(serverip + sync_post_url);
			} else {
				// display error
				Toast.makeText(WorkcontentListActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
//	private OnClickListener cloudsync_touch_listener = new OnTouchListener() {
//		
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//			switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                ImageView view = (ImageView) v;
//                view.getDrawable().setColorFilter(0xFFFF8800, PorterDuff.Mode.SRC_ATOP);
//                view.invalidate();
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//            	ImageView view = (ImageView) v;
//                //clear the overlay
//                view.getDrawable().clearColorFilter();
//                view.invalidate();
//                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//    			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//    			if(networkInfo != null && networkInfo.isConnected()) {
//    				// post data
//    				
//    				new PostDataTask().execute(serverip + sync_post_url);
//    			} else {
//    				// display error
//    				Toast.makeText(WorkcontentListActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
//    			}
//            }
//            case MotionEvent.ACTION_CANCEL: {
//                ImageView view = (ImageView) v;
//                //clear the overlay
//                view.getDrawable().clearColorFilter();
//                view.invalidate();
//                break;
//            }
//        }
//
//        return true;
//		}
//	};
	
	/**
	 * 向云端发送数据
	 * 1. 获取全部没有同步的班报数据，并拼接成JSONObject数组
	 * 2. 循环向云端发送HttpPost请求
	 * 3. 根据返回的状态更新mobile端的班报同步状态
	 */
	private class PostDataTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);

			HttpPost httppost = new HttpPost(urls[0]);
			
			try {
				
				List<EntityTourreport> list = new TourreportDBHelper(WorkcontentListActivity.this).getAllTourreportsNoSync();
				List<Workcontent> list_workcontent;
				JSONObject json, workcontent; 
				JSONArray workcontents = new JSONArray();
				for(EntityTourreport tour : list) {
					json = new JSONObject();
//					json.put("holdid", tour.getHoleid());
					json.put("holeid", holeid);
					json.put("administrator", tour.getAdministrator());
					json.put("recorder", tour.getRecorder());
					json.put("projectmanager", tour.getProjectmanager());
					json.put("tourleader", tour.getTourleader());
					json.put("tourdate", tour.getTourdate());
					json.put("starttime", tour.getStarttime());
					json.put("endtime", tour.getEndtime());
					json.put("tourshift", tour.getTourshift().toString());
					json.put("tourcore", tour.getTourcore().toString());
					json.put("status", tour.getStatus());
					json.put("lastdeep", tour.getLastdeep().toString());
					json.put("currentdeep", tour.getCurrentdeep().toString());
					json.put("tourdrillingtime", tour.getTourdrillingtime());
					json.put("tourauxiliarytime", tour.getTourauxiliarytime());
					json.put("holeaccidenttime", tour.getHoleaccidenttime());
					json.put("deviceaccidenttime", tour.getDeviceaccidenttime());
					json.put("othertime", tour.getOthertime());
					json.put("totaltime", tour.getTotaltime());
					json.put("takeoverremark", tour.getTakeoverremark());
					json.put("instrumenttakeover", tour.getInstrumenttakeover());
					json.put("centralizer", tour.getCentralizer());
					
					list_workcontent = new WorkcontentDBHelper(WorkcontentListActivity.this).getWorkcontentByTourreportId(Long.parseLong(tour.getId()));
					
					for (Workcontent c: list_workcontent) {
						workcontent = new JSONObject();
						workcontent.put("content", c.getType());
						workcontent.put("starttime", c.getStarttime());
						workcontent.put("endtime", c.getEndtime());
						workcontent.put("upmore", c.getUpleft());
						workcontent.put("corename", c.getCorename());
						workcontent.put("coregrade", c.getCoregrade());
						workcontent.put("corenumber", c.getCorenumber());
						workcontent.put("corelength", c.getCorelength());
						workcontent.put("coreleftlength", c.getCoreleftlength());
						workcontent.put("drillinglength", c.getDrillinglength());
						workcontent.put("drillbit", c.getDrillbit());
						workcontent.put("rotatespeed", c.getRotatespeed());
						workcontent.put("pumpquantity", c.getPump());
						workcontent.put("pumppressure", c.getPressure());
						workcontent.put("holedeep", c.getHoledeep());
						workcontents.put(workcontent);
					}
					json.put("workcontent", workcontents);
					
					
					json.toString();
		            Log.d(DEBUG_TAG, urls[0]);
		            Log.d(DEBUG_TAG, json.toString());
					
		            ByteArrayEntity entity = new ByteArrayEntity(json.toString().getBytes("UTF-8"));
		            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		            
		            httppost.setEntity(entity);
					HttpResponse response = client.execute(httppost);
					
					animation.cancel();
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "1";
		}
		
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
	private List<Map<String,Object>> getListItems(long currentpage, long pagesize)
	{
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		TourreportDBHelper tourreportDB = new TourreportDBHelper(this);
		List<EntityTourreport> list = tourreportDB.getPagedTourreports(currentpage, pagesize);
		
		for(EntityTourreport entity:list)
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tourreportid", entity.getId());
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
		listview = (XListView) findViewById(R.id.workcontent_list);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(this);
		
		items = getListItems(currentpage,pagesize);
		
		if (items.size() < 20) 
		{
			listview.setPullLoadEnable(false);
		} 
		else 
		{
			listview.setPullLoadEnable(true);
		}
		
		this.currentpage++;
		adapter = new WorkcontentListviewAdapter(this,items);
		listview.setAdapter(adapter);
		
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				LinearLayout ll = (LinearLayout) view;
				
				TextView tv_id = (TextView) ll.findViewById(R.id.tourreport_list_id_tag);
				
				//Toast.makeText(WorkcontentListActivity.this, "the tv_id is :" + tv_id.getTag().toString(), 3000).show();
				
				//Toast.makeText(this, "保存班报成功", Toast.LENGTH_LONG).show();
				
				//Log.d("workcontent", tv_id.getText().toString());
				
				Bundle item_bundle = new Bundle();
				item_bundle.putString("tourreportid", tv_id.getTag().toString());
				Intent intent = new Intent(WorkcontentListActivity.this, TourreportViewActivity.class);
				intent.putExtra(GlobalConstants.TOURREPORTID, item_bundle);
				startActivity(intent);
				
				
			}
		});
		
		findViewById(R.id.menu_add_tourreport).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_list).setOnClickListener(this);
		//findViewById(R.id.menu_tourreport_report).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_setting).setOnClickListener(this);
		
		
		this.sharedPrefs = this.getSharedPreferences(GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);
		serverip = this.sharedPrefs.getString("serverip","http://192.168.1.115:5000");
		holeid = this.sharedPrefs.getString("holeid", "3");
		
		mHandler = new Handler();
		
		
		
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
	
	private void open_setting_window()
	{
		Intent intent = new Intent(WorkcontentListActivity.this, DrillSettingsActivity.class);
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
		//case R.id.menu_tourreport_report:
		//	break;
		case R.id.menu_tourreport_setting:
			open_setting_window();
			break;
		}
		
	}
	
	/**
	 * 加载新的数据
	 * */
	private void addNewItems() {
		List<Map<String, Object>> newitems = getListItems(currentpage,pagesize);
		if(newitems!=null)
		{
			items.addAll(newitems);
			currentpage ++;
			if(newitems.size()<5)
			{
				listview.setPullLoadEnable(false);
			}
			else
			{
				listview.setPullLoadEnable(true);
			}
			
		}
		else
		{
			listview.setPullLoadEnable(false);
		}
	}
	
	private void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime("刚刚");
	}


	/**
	 * 上拉刷新 
	 * */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}


	/**
	 *  下拉加载更多, 
	 *  先从数据库中获取几条记录，
	 *  然后从网络上来获取
	 * */
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				addNewItems();
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	
	
}
