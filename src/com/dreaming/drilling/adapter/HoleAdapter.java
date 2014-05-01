package com.dreaming.drilling.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreaming.drilling.R;
import com.dreaming.drilling.bean.SpinnerData;

public class HoleAdapter extends BaseAdapter{
	
	private List<SpinnerData> mList;
	private Context mContext;
	
	public HoleAdapter(Context pContext, List<SpinnerData> pList) {
		this.mList = pList;
		this.mContext = pContext;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.hole_spinner, null);
		TextView holename = (TextView)rowView.findViewById(R.id.holename);
		holename.setText(mList.get(position).getName());
		
		ImageView outerflag = (ImageView) rowView.findViewById(R.id.hole_outerflag);
		if(mList.get(position).getOuterflag() == 1) { // 外协孔
			outerflag.setImageResource(R.drawable.hole_out);
		} else {
			outerflag.setImageResource(R.drawable.hole_in);
			outerflag.setVisibility(View.INVISIBLE);
		}
		
		return rowView;
	}

}
