package com.zidoo.realtek.rtc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zidoo.realtek.rtc.db.DbBaseManager;
import com.zidoo.realtek.rtc.selecttime.TimeWheelDialog;
import com.zidoo.realtek.rtc.selecttime.TimeWheelDialog.ZidooDialogListener;

public class MainActivity extends Activity implements OnClickListener {

	private MyAdapter		mMyAdapter				= null;
	// min time
	public final static int	MIN_RESERVATION_TIME	= 1 * 60 * 1000;
	private DbBaseManager	mDbBaseManager			= null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDbBaseManager = DbBaseManager.getInstans(this);
		initView();
		initData();
	}

	private void initData() {
		ArrayList<Long> list = mDbBaseManager.query();
		mMyAdapter.setData(list);
	}

	private void initView() {
		ListView listView = (ListView) findViewById(R.id.listview);
		mMyAdapter = new MyAdapter(this);
		listView.setAdapter(mMyAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final long rtctime = (long) mMyAdapter.getItem(position);
				new AlertDialog.Builder(MainActivity.this).setTitle("Delete rtc time").setMessage("Are you sure delete this rtc time : " + millisToTimeString(rtctime) + " ?")
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub

							}
						}).setNeutralButton("Delete", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								if (mDbBaseManager.delete(rtctime)) {
									RtcUtils.deleteWakeUpTime(MainActivity.this, rtctime);
									initData();
								}
							}
						}).create().show();
			}
		});
		findViewById(R.id.add).setOnClickListener(this);
	}

	class MyAdapter extends BaseAdapter {
		ArrayList<Long>	mList		= new ArrayList<Long>();
		Context			mContext	= null;

		public MyAdapter(Context mContext) {
			super();
			this.mContext = mContext;
		}

		public void setData(ArrayList<Long> mList) {
			this.mList = mList;
			notifyDataSetChanged();
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
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MyView myView = null;
			if (convertView == null) {
				myView = new MyView();
				convertView = View.inflate(mContext, R.layout.list_item_view, null);
				myView.titleView = (TextView) convertView.findViewById(R.id.list_item_view_title);
				convertView.setTag(myView);
			} else {
				myView = (MyView) convertView.getTag();
			}
			myView.titleView.setText((position + 1) + "、" + millisToTimeString(mList.get(position)));
			return convertView;
		}

		class MyView {
			TextView	titleView;
		}

	}

	private String millisToTimeString(long timeMillis) {
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(timeMillis); 
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd '-' HH:mm:ss");
		String formatted = format1.format(c.getTime());
		return formatted;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add: {
			new TimeWheelDialog().getZidooDialog(MainActivity.this, false, false, MainActivity.this.getString(R.string.added_time_select), new ZidooDialogListener() {

				@Override
				public void onConfirm(Dialog dialog, int year, int mouth, int day, int hour, int munith, int second) {
					long mVisitorTime = currentTime(year, mouth - 1, day, hour, munith, 0);
					if (mVisitorTime < System.currentTimeMillis() + MIN_RESERVATION_TIME) {
						Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.select_time_hint), Toast.LENGTH_SHORT).show();
						return;
					}
					dialog.dismiss();
					if (mDbBaseManager.insert(mVisitorTime)) {
						RtcUtils.addWakeUpTime(MainActivity.this, mVisitorTime);
						initData();
					}
				}

				@Override
				public void onCancel(Dialog dialog) {
					dialog.dismiss();
				}
			}).show();
		}
			break;

		default:
			break;
		}
	}

	public static long currentTime(int year, int month, int day, int h, int min, int s) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.MILLISECOND, 0);
		startCalendar.set(year, month, day, h, min, s);
		return startCalendar.getTimeInMillis();
	}

}
