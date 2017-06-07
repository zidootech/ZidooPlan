package com.zidoo.realtek.rtc.db;

import java.util.ArrayList;

import com.zidoo.realtek.rtc.RtcUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbBaseManager {
	// 自增长_id
	public final static String		KEY_AOTO			= "_id";
	public final static String		TABLE_NAME			= "rtc_time";
	public final static String		KEY_WAKE_UP_TIME	= "wake_up_time";
	public final static String		RTC_TABLER			= "create table if not exists " + TABLE_NAME + " (" + KEY_AOTO + " integer primary key autoincrement, " + KEY_WAKE_UP_TIME
																+ " integer);";

	private static SQLiteDatabase	BASEMANAGER			= null;

	private static DbBaseManager	mDbBaseManager		= null;

	public DbBaseManager(Context context) {
		if (BASEMANAGER == null) {
			DbHelper base = new DbHelper(context);
			BASEMANAGER = base.getWritableDatabase();
		}
	}

	public static DbBaseManager getInstans(Context context) {
		if (mDbBaseManager == null) {
			mDbBaseManager = new DbBaseManager(context);
		}
		return mDbBaseManager;
	}

	public boolean insert(long walkupTime) {
		if (isdb(walkupTime)) {
			return false;
		}
		ContentValues values = new ContentValues();
		values.put(KEY_WAKE_UP_TIME, walkupTime);
		long len = BASEMANAGER.insert(TABLE_NAME, null, values);
		if (len >= 0) {
			return true;
		}
		return false;
	}

	public boolean isdb(long walkupTime) {
		boolean isdb = false;
		Cursor cursor = BASEMANAGER.query(TABLE_NAME, null, KEY_WAKE_UP_TIME + " =? ", new String[] { walkupTime + "" }, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				isdb = true;
			}
			cursor.close();
		}
		return isdb;
	}

	public boolean delete(long walkupTime) {
		return BASEMANAGER.delete(TABLE_NAME, KEY_WAKE_UP_TIME + " =? ", new String[] { walkupTime + "" }) > 0;
	}

	public ArrayList<Long> query() {
		Cursor cursor = BASEMANAGER.query(TABLE_NAME, null, null, null, null, null, KEY_WAKE_UP_TIME + " asc ");
		ArrayList<Long> list = new ArrayList<Long>();
		if (cursor != null) {
			long cTime = RtcUtils.getCurrentTimeMillis();
			while (cursor.moveToNext()) {
				long waketime = cursor.getLong(cursor.getColumnIndex(KEY_WAKE_UP_TIME));
				if (waketime < cTime) {
					delete(waketime);
				} else {
					list.add(waketime);
				}
			}
			cursor.close();
		}
		return list;
	}

}
