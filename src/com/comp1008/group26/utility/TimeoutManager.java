package com.comp1008.group26.utility;

import java.util.HashSet;

import com.comp1008.group26.utility.UsageLog.Action;

import android.app.Activity;

public class TimeoutManager implements Runnable {

	private static HashSet<TimeoutManager> instances = new HashSet<TimeoutManager>();
	private Activity activity;
	private String activity_name;
	private boolean timeout = true;
	
	public TimeoutManager(Activity activity, String name) {
		this.activity = activity;
		this.activity_name = name;
		instances.add(this);
	}
	
	public void setTimeout(boolean t) {
		this.timeout = t;
	}
	
	public static void cancelAll() {
		for (TimeoutManager tom : instances) {
			tom.setTimeout(false);
		}
	}
	
	@Override
	public void run() {

		if (timeout) {
			UsageLog.getInstance().writeEvent(Action.TIMEOUT, activity_name);
			activity.finish();
			instances.remove(this);
		}
		
	}

}
