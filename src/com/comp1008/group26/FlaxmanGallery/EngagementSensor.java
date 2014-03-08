package com.comp1008.group26.FlaxmanGallery;


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
	public void connectToSensor();
	// TODO: This method may require some parameters and may throw exceptions - I'll add them when I'm sure
	
	/**
	 * Returns the sensor's current state
	 * @return The state this sensor is currently in
	 */
	public EngagementState getCurrentState();
}
