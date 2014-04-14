package com.comp1008.group26.utility;

import java.io.IOException;



/**
 * This Interface stipulates what an engagement sensor must do
 * @author mateo
 *
 */
public interface EngagementSensor {
	
	/**
	 * The 5 possible states an Armature can be in
	 * @author mateo
	 *
	 */
	public static enum EngagementState
	{IDLE, TARGET_NEARBY, TARGET_IN_RANGE, TARGET_ENGAGED, LEFT}
	
	/**
	 * Attempt to connect via Bluetooth to an appropriate sensor
	 */
//	public void connectToSensor(UsbAccessory accessory);
	
	/**
	 * Returns the sensor's current state
	 * @return The state this sensor is currently in
	 * @throws IOException 
	 */
	public EngagementState getCurrentState() throws IOException;
}
