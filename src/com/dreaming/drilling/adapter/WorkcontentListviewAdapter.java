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
		
		TextView tv_date = (TextView) convertview.findViewById(R.id.tourreport_list_date);
		TextView tv_time = (TextView) convertview.findViewById(R.id.tourreport_list_time);
		TextView tv_drillinglength = (TextView) convertview.findViewById(R.id.tourreport_list_drillinglength);
		TextView tv_corelength = (TextView) convertview.findViewById(R.id.tourreport_list_corelength);
		TextView tv_holedeep = (TextView) convertview.findViewById(R.id.tourreport_list_holedeep);
		
		
		tv_date.setText(listItems.get(position).get("").toString());
		tv_time.setText(listItems.get(position).get("").toString());
		tv_drillinglength.setText(listItems.get(position).get("").toString());
		tv_corelength.setText(listItems.get(position).get("").toString());
		tv_holedeep.setText(listItems.get(position).get("").toString());
		
		return convertview;
	}
	
	

}
