package com.dreaming.drilling.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.dreaming.drilling.utils.GlobalConstants;
import com.dreaming.drilling.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * �౨��Ľ��棬Ҳ��������
 * */
public class MainActivity extends Activity implements OnClickListener {

	private String title_name = "�౨¼��";

	protected SharedPreferences sharedPrefs;

	// ��ȡһ����������
	private Calendar thedate = Calendar.getInstance(Locale.CHINA);
	private Calendar starttime = Calendar.getInstance(Locale.CHINA);
	private Calendar endtime = Calendar.getInstance(Locale.CHINA);

	private SimpleDateFormat date_fmt = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat time_fmt = new SimpleDateFormat("HH:mm");

	private TextView tv_date;
	private TextView tv_starttime;
	private TextView tv_endtime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buildingPreference();
		initView();

	}

	private void initView() {
		TextView textView_title = (TextView) findViewById(R.id.title);
		textView_title.setText(title_name);

		// �׺�
		TextView textview_holenumber = (TextView) findViewById(R.id.tourreport_holenumber_value);
		String holenumber = this.sharedPrefs.getString("holenumber", "zk001");
		Log.d("holenumber", holenumber);
		// ����������ʽ
		SpannableString msp = new SpannableString(holenumber);
		// msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
		// holenumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //����
		msp.setSpan(new UnderlineSpan(), 0, holenumber.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview_holenumber.setText(msp);

		// ������
		TextView textview_rigmachine_number = (TextView) findViewById(R.id.tourreport_rigmachine_number_value);

		String rignumber = this.sharedPrefs.getString("rignumber",
				"rigmachine001");
		// ����������ʽ
		SpannableString msp_rig = new SpannableString(rignumber);
		// msp_rig.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
		// rignumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //����
		msp_rig.setSpan(new UnderlineSpan(), 0, rignumber.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textview_rigmachine_number.setText(msp_rig);

		// ����౨�����ں�ʱ��
		tv_date = (TextView) findViewById(R.id.tourreport_date_value);
		tv_starttime = (TextView) findViewById(R.id.tourreport_starttime_value);
		tv_endtime = (TextView) findViewById(R.id.tourreport_endtime_value);

		updateDate();
		updateStarttime();
		updateEndtime();

		tv_date.setOnClickListener(dp_click);
		tv_starttime.setOnClickListener(tp_starttime_click);
		tv_endtime.setOnClickListener(tp_endtime_click);

		findViewById(R.id.tourreport_add_takeover).setOnClickListener(this);
		findViewById(R.id.tourreport_add_workcontent).setOnClickListener(this);

		findViewById(R.id.menu_add_tourreport).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_list).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_report).setOnClickListener(this);
		findViewById(R.id.menu_tourreport_setting).setOnClickListener(this);

	}

	private TimePickerDialog.OnTimeSetListener tp_starttime_listener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			starttime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			starttime.set(Calendar.MINUTE, minute);
			updateStarttime();
		}
	};

	private TimePickerDialog.OnTimeSetListener tp_endtime_listener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			endtime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			endtime.set(Calendar.MINUTE, minute);
			updateEndtime();
		}
	};

	// �����DatePickerDialog�ؼ������ð�ťʱ�����ø÷���
	private DatePickerDialog.OnDateSetListener dp_listener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// �޸������ؼ����꣬�£���
			// �����year,monthOfYear,dayOfMonth��ֵ��DatePickerDialog�ؼ����õ�����ֵһ��
			thedate.set(Calendar.YEAR, year);
			thedate.set(Calendar.MONTH, monthOfYear);
			thedate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			// ��ҳ��TextView����ʾ����Ϊ����ʱ��
			updateDate();
		}
	};

	private View.OnClickListener dp_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// ����һ��DatePickerDialog���󣬲���ʾ����ʾ��DatePickerDialog�ؼ�����ѡ�������գ�������
			new DatePickerDialog(MainActivity.this, dp_listener,
					thedate.get(Calendar.YEAR), thedate.get(Calendar.MONTH),
					thedate.get(Calendar.DAY_OF_MONTH)).show();
		}
	};

	private View.OnClickListener tp_starttime_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			new TimePickerDialog(MainActivity.this, tp_starttime_listener,
					starttime.get(Calendar.HOUR_OF_DAY),
					starttime.get(Calendar.MINUTE), true).show();
		}
	};

	private View.OnClickListener tp_endtime_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			new TimePickerDialog(MainActivity.this, tp_endtime_listener,
					endtime.get(Calendar.HOUR_OF_DAY),
					endtime.get(Calendar.MINUTE), true).show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ϵͳ�Ļ�������
	 * */
	public void buildingPreference() {
		this.sharedPrefs = this.getSharedPreferences(
				GlobalConstants.PREFERENCE_NAME, GlobalConstants.MODE);

		if (!this.sharedPrefs.getBoolean("first2", true))
			return;

		SharedPreferences.Editor localEditor = this.sharedPrefs.edit();

		// ϵͳ����Ļ�������
		localEditor.putBoolean("first2", false);

		localEditor.putString("holenumber", "zk002");
		localEditor.putString("rignumber", "rigmachine002");

		localEditor.commit();

	}

	private void updateDate() {
		if (tv_date != null) {
			SpannableString msp_date = new SpannableString(
					date_fmt.format(thedate.getTime()));
			msp_date.setSpan(new UnderlineSpan(), 0,
					date_fmt.format(thedate.getTime()).length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_date.setText(msp_date);
		}

	}

	private void updateStarttime() {
		if (tv_starttime != null) {
			SpannableString msp_starttime = new SpannableString(
					time_fmt.format(starttime.getTime()));
			msp_starttime.setSpan(new UnderlineSpan(), 0,
					time_fmt.format(starttime.getTime()).length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_starttime.setText(msp_starttime);

		}
	}

	private void updateEndtime() {
		if (tv_endtime != null) {
			SpannableString msp_endtime = new SpannableString(
					time_fmt.format(endtime.getTime()));
			msp_endtime.setSpan(new UnderlineSpan(), 0,
					time_fmt.format(endtime.getTime()).length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv_endtime.setText(msp_endtime);
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tourreport_add_takeover:
			open_workcontent();
			break;

		case R.id.tourreport_add_workcontent:
			open_workcontent();
			break;

		case R.id.menu_add_tourreport:
			open_add_tourreport_window();
			break;
		case R.id.menu_tourreport_list:
			open_tourreport_list_window();
			break;
		case R.id.menu_tourreport_report:
			break;
		case R.id.menu_tourreport_setting:
			break;
		}
	}

	private void open_workcontent() {
		Intent intent = new Intent(MainActivity.this, WorkcontentActivity.class);
		startActivity(intent);

	}
	
	
	private void open_add_tourreport_window()
	{
		Intent intent = new Intent(MainActivity.this,MainActivity.class);
		startActivity(intent);
	}
	
	private void open_tourreport_list_window()
	{
		Intent intent = new Intent(MainActivity.this,WorkcontentListActivity.class);
		startActivity(intent);
	}

}
