// This class is based on the android DemoKit app and 

package com.comp1008.group26.utility;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbAccessory;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;


public class SensorManager extends IntentService implements EngagementSensor {

	private UsbAccessory mUsbAccessory;
	private UsbManager mUsbManager;
	private FileInputStream inputStream;

	private static final int MESSAGE_ULTRASOUND = 1;
	private static final int MESSAGE_MOTION_SENSOR = 2;

	private boolean latestMotionSensorReading = false;
	private int latestUltraSoundReading = 300;
	private long timeOfLastInteraction;
	private EngagementState latestState = EngagementState.IDLE;

	public SensorManager(String name) {
		super(name);
		this.mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);


	}

	private void connectToSensor(UsbAccessory accessory) {
		ParcelFileDescriptor pfd = mUsbManager.openAccessory(accessory);
		if (pfd != null) {
			FileDescriptor fd = pfd.getFileDescriptor();
			inputStream = new FileInputStream(fd);
		}

	}

	private void readSensor() throws IOException {
		byte[] buffer = new byte[6];
		if (inputStream != null) {
			switch (buffer[0]) {
			case MESSAGE_ULTRASOUND :
				latestUltraSoundReading = buffer[1] * 256 + buffer[2];
				latestMotionSensorReading = buffer[4] == 0xFF;
				break;
			case MESSAGE_MOTION_SENSOR :
				latestMotionSensorReading = buffer[1] == 0xFF;
				latestUltraSoundReading = buffer[4] * 256 + buffer[5];
			}
		} else {
			throw new IOException("Engagement Sensor hasn't been initialized");
		}
	}

	//Temporary method
	public int getUS() {
		try {
			readSensor();
		} catch (IOException e) {
			connectToSensor(mUsbAccessory);
			return -1;
		}

		return latestUltraSoundReading;
	}

	//Temporary Method
	public boolean getMotion() {
		try {
			readSensor();
		} catch (IOException e) {
			connectToSensor(mUsbAccessory);
			return false;
		}

		return latestMotionSensorReading;
	}

	@Override
	public EngagementState getCurrentState() throws IOException {
		readSensor();

		if (latestMotionSensorReading) {
			if (latestUltraSoundReading > 250) {
				if (latestState == EngagementState.IDLE || latestState == EngagementState.LEFT) {
					latestState = EngagementState.TARGET_NEARBY;
				} else {
					latestState = EngagementState.LEFT;
					timeOfLastInteraction = System.currentTimeMillis();
				}
				
			} else if (latestUltraSoundReading > 50) {
				latestState = EngagementState.TARGET_IN_RANGE;
			} else {
				latestState = EngagementState.TARGET_ENGAGED;
			}
			
		} else {
			if (latestState != EngagementState.IDLE) {
				long time = System.currentTimeMillis();
				if (time - timeOfLastInteraction > 120000) { // 2 minutes
					latestState = EngagementState.IDLE;
				}
			}

		}
		return latestState;




	}

	@Override
	protected void onHandleIntent(Intent intent) {
		this.mUsbAccessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
		connectToSensor(mUsbAccessory);


	}

}
