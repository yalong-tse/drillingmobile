package com.dreaming.drilling.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dreaming.drilling.bean.SpinnerData;

public class SpinAdapter extends ArrayAdapter<SpinnerData> {

	private Context context;
//	private SpinnerData[] values;
	private List<SpinnerData> values;

	public SpinAdapter(Context context, int resource, List<SpinnerData> values) {
		super(context, resource, values);
		this.context = context;
		this.values = values;
	}
	
	public int getCount() {
		return values.size();
	}
	
	public SpinnerData getItem(int position) {
		return values.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		
		label.setTextColor(Color.BLACK);
		
		label.setText(getItem(position).getName());
		
		return label;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		
		label.setTextColor(Color.BLACK);
		
		label.setText(getItem(position).getName());
		
		return label;
	}
	
}
