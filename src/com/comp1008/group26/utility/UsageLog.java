package com.comp1008.group26.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.text.format.*;
import android.util.Log;

public class UsageLog {

	private static final String TAG = "com.comp1008.group26.utility.UsageLog";

	private static UsageLog instance = null;

	private  String location = "";
	private static final String filename = "usageLog.csv";
	private BufferedWriter bw;

	protected UsageLog() {
		File f = new File(location + File.separator + filename);
		try {
			FileWriter fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "File could not be opened");
		}
	}

	public static UsageLog getInstance() {
		if (instance == null) {
			instance = new UsageLog();
		} 
		return instance;

	}

	public boolean isInitialized() {
		return location.equalsIgnoreCase("");
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	private String stringifyEvent(Action event, String view) {
		return event + "," + view.trim();
	}
	
	public void writeEvent (Action action, String view) {
		String line = DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()) + "," + stringifyEvent(action, view);
		
		try {
			bw.write(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "File could not be written to");
			e.printStackTrace();
		}
	}
	
	public static enum Action {ENTER, EXIT, TIMEOUT, PLAY, PAUSE};



}
