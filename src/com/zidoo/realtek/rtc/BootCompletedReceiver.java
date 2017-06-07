package com.zidoo.realtek.rtc;

import java.util.ArrayList;

import com.zidoo.realtek.rtc.db.DbBaseManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("bob", "--------rtc-------bootCompletedReceiver");
		// init add wakeup time
		DbBaseManager dbBaseManager = DbBaseManager.getInstans(context);
		ArrayList<Long> list = dbBaseManager.query();
		for (int i = 0; i < list.size(); i++) {
			RtcUtils.addWakeUpTime(context, list.get(i));
		}
		
	}

}
