package com.dreaming.drilling.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
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
	
	// 自定义custom view
	// 显示图标，区分外协孔和内部孔
	// @editor niu_hr 
	// @updateDate 2014-05-01
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// I created a dynamic TextView here, but you can reference your own custom layout for each spinner item
		TextView label = new TextView(context);
		
		label.setTextSize(20);
		label.setGravity(Gravity.CENTER);
		label.setTextColor(Color.BLACK);
		
		label.setText(getItem(position).getName());
		
		// And finally return your dynamic (or custom) view for each spinner item
		return label;
		
	}
	
	// And here is when the "chooser" is popped up
	// Normally is the same view, but you can customize it if you want
//	@Override
//	public View getDropDownView(int position, View convertView, ViewGroup parent) {
//		TextView label = new TextView(context);
//		
//		label.setGravity(Gravity.CENTER);
//		label.setTextSize(20);
//		label.setTextColor(Color.BLACK);
//		
//		label.setText(getItem(position).getName());
//		
//		return label;
//	}
	
}
