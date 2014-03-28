package com.comp1008.group26.utility;

import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;

public class SensorManager implements EngagementSensor {

	private UsbAccessory mUsbAccessory;
	private UsbManager mUsbManager;
	
	private static final int MESSAGE_ULTRASOUND = 1;
	private static final int MESSAGE_MOTION_SENSOR = 2;
	
	public SensorManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void connectToSensor() {
		// TODO Auto-generated method stub

	}

	@Override
	public EngagementState getCurrentState() {
		// TODO Auto-generated method stub
		return null;
	}

}
