package com.comp1008.group26.utility;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.SyncFailedException;

import android.text.format.*;
import android.util.Log;

public class UsageLog {

	private static final String TAG = "com.comp1008.group26.utility.UsageLog";

	private static UsageLog instance = null;

	private static String location = "";
	private static final String filename = "usageLog.csv";
	private FileWriter fw;
	private FileOutputStream fos;
	private boolean isInitialized = false;

	protected UsageLog() {
		String uri = location + File.separator + filename;
		Log.d(TAG, "Accessing " + uri);
		try {
			fos = new FileOutputStream(uri, true);
			fw = new FileWriter(fos.getFD());
			isInitialized = true;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "File could not be opened");
		} catch (NullPointerException e) {
			e.printStackTrace();
			Log.e(TAG, "Writer is null");
		}
	}

	public static UsageLog getInstance() {
		if (instance == null) {
			instance = new UsageLog();
		} 
		return instance;

	}

	public boolean isInitialized() {
		return isInitialized;
	}
	
	public static void setLocation(String location) {
		UsageLog.location = location;
	}
	
	private String stringifyEvent(Action event, String view) {
		if (view == null) {
			view = "Unknown view";
		}
		return event + "," + view.trim();
	}
	
	public void writeEvent (Action action, String view) {
		String line = DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()) + "," + stringifyEvent(action, view) + "\n";
		Log.d(TAG, "Writing line: " + line);
		try {
			fw.append(line);
			fw.flush();
			fos.getFD().sync();
			
		} catch (SyncFailedException e) {
			Log.e(TAG, "Sync failed");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "File could not be written to: IO Exception");
			e.printStackTrace();
		} 
		
	}
	
	public void close() {
		try {
			fw.close();
			fos.getFD().sync();
			fos.close();
		} catch (SyncFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static enum Action {ENTER, EXIT, TIMEOUT, PLAY, PAUSE};



}
