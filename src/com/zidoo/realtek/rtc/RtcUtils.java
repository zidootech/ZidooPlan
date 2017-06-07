package com.zidoo.realtek.rtc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.Intent;

public class RtcUtils {

	public static long getCurrentTimeMillis() {
		Calendar calendar = new GregorianCalendar();
		Date trialtime = new Date();
		calendar.setTime(trialtime);
		return calendar.getTimeInMillis();
	}

	public static void addWakeUpTime(Context context, long wakeUpTimeMillis) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.WAKE_UP_ADD");
		intent.putExtra("timeStart", wakeUpTimeMillis);
		context.sendBroadcast(intent);
	}

	public static void deleteWakeUpTime(Context context, long wakeUpTimeMillis) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.WAKE_UP_RM");
		intent.putExtra("timeStart", wakeUpTimeMillis);
		context.sendBroadcast(intent);
	}
}
