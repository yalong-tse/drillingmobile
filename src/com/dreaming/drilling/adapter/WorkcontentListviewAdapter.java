package com.dreaming.drilling.adapter;

import java.util.List;
import java.util.Map;

import com.dreaming.drilling.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * 
 * 
 * 
 * */
public class WorkcontentListviewAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String,Object>> listItems;
	private LayoutInflater listContainer;
	
	
	/**
	 * 内部类
	 * */
	public class ListItemView
	{
		TextView tv_id_tag;
		TextView tv_date;
		TextView tv_time ;
		TextView tv_drillinglength ;
		TextView tv_corelength ;
		TextView tv_holedeep ;
		TextView tv_drillingtime ;
		TextView tv_auxtime ;
		TextView tv_othertime ;
		TextView tv_holeinnertime ;
		TextView tv_devicetime ;
		TextView tv_totaltime ;
	}
	
	
	
	public WorkcontentListviewAdapter(Context context,List<Map<String,Object>> listItems)
	{
		this.context = context;
		this.listItems = listItems;
		
		this.listContainer = LayoutInflater.from(context);
		
		Log.d("ADAPTER", "in the adapter create");
		
	}
	
	
	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ListItemView itemviews = null;
		
		if(convertview==null)
		{
			itemviews = new ListItemView();
			convertview =  this.listContainer.inflate(R.layout.workcontent_listview_filling, null);
		
			itemviews.tv_id_tag = (TextView) convertview.findViewById(R.id.tourreport_list_id_tag);
			itemviews.tv_date = (TextView) convertview.findViewById(R.id.tourreport_list_date);
			itemviews.tv_time = (TextView) convertview.findViewById(R.id.tourreport_list_time);
			itemviews.tv_drillinglength = (TextView) convertview.findViewById(R.id.tourreport_list_drillinglength);
			itemviews.tv_corelength = (TextView) convertview.findViewById(R.id.tourreport_list_corelength);
			itemviews.tv_holedeep = (TextView) convertview.findViewById(R.id.tourreport_list_holedeep);
			itemviews.tv_drillingtime = (TextView) convertview.findViewById(R.id.tourreport_list_drilltime);
			itemviews.tv_auxtime = (TextView) convertview.findViewById(R.id.tourreport_list_auxtime);
			itemviews.tv_othertime = (TextView) convertview.findViewById(R.id.tourreport_list_othertime);
			itemviews.tv_holeinnertime = (TextView) convertview.findViewById(R.id.tourreport_list_holeinnertime);
			itemviews.tv_devicetime = (TextView) convertview.findViewById(R.id.tourreport_list_devicerepairtime);
			itemviews.tv_totaltime = (TextView) convertview.findViewById(R.id.tourreport_list_alltime);
			convertview.setTag(itemviews);
			
		}
		else
		{
			itemviews = (ListItemView) convertview.getTag();
		}
		
		/**
		itemviews.tv_date = (TextView) convertview.findViewById(R.id.tourreport_list_date);
		itemviews.tv_time = (TextView) convertview.findViewById(R.id.tourreport_list_time);
		itemviews.tv_drillinglength = (TextView) convertview.findViewById(R.id.tourreport_list_drillinglength);
		itemviews.tv_corelength = (TextView) convertview.findViewById(R.id.tourreport_list_corelength);
		itemviews.tv_holedeep = (TextView) convertview.findViewById(R.id.tourreport_list_holedeep);
		itemviews.tv_drillingtime = (TextView) convertview.findViewById(R.id.tourreport_list_drilltime);
		itemviews.tv_auxtime = (TextView) convertview.findViewById(R.id.tourreport_list_auxtime);
		itemviews.tv_othertime = (TextView) convertview.findViewById(R.id.tourreport_list_othertime);
		itemviews.tv_holeinnertime = (TextView) convertview.findViewById(R.id.tourreport_list_holeinnertime);
		itemviews.tv_devicetime = (TextView) convertview.findViewById(R.id.tourreport_list_devicerepairtime);
		Texttv_totaltime = (TextView) convertview.findViewById(R.id.tourreport_list_alltime);
		*/

		// 增加一个 tag 用来存储Id,其余的使用text 显示相关的信息
		itemviews.tv_id_tag.setTag(listItems.get(position).get("tourreportid").toString());
		itemviews.tv_date.setText(listItems.get(position).get("tourdate").toString());
		itemviews.tv_time.setText(listItems.get(position).get("tourtime").toString());
		itemviews.tv_drillinglength.setText(listItems.get(position).get("tourshift").toString());
		itemviews.tv_corelength.setText(listItems.get(position).get("tourcore").toString());
		itemviews.tv_holedeep.setText(listItems.get(position).get("currentdeep").toString());
		itemviews.tv_drillingtime.setText(listItems.get(position).get("drillingtime").toString());
		itemviews.tv_auxtime.setText(listItems.get(position).get("auxtime").toString());
		itemviews.tv_othertime.setText(listItems.get(position).get("othertime").toString());
		itemviews.tv_holeinnertime.setText(listItems.get(position).get("holetime").toString());
		itemviews.tv_devicetime.setText(listItems.get(position).get("devicetime").toString());
		itemviews.tv_totaltime.setText(listItems.get(position).get("totaltime").toString());
		
		return convertview;
	}
	
	

}
