// This program is a modified version of DemoKit, available at
// http://developer.android.com/tools/adk/adk.html

#include <Wire.h>
#include <Servo.h>

#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>

#include <CapacitiveSensor.h>

// TODO: Remove unused defines
// TODO: Add defines for sensors
#define  LED3_RED       2
#define  LED3_GREEN     4
#define  LED3_BLUE      3

#define  LED2_RED       5
#define  LED2_GREEN     7
#define  LED2_BLUE      6

#define  LED1_RED       8
#define  LED1_GREEN     10
#define  LED1_BLUE      9

#define  SERVO1         11
#define  SERVO2         12
#define  SERVO3         13

#define  TOUCH_RECV     14
#define  TOUCH_SEND     15

#define  RELAY1         A0
#define  RELAY2         A1

#define  LIGHT_SENSOR   A2
#define  TEMP_SENSOR    A3

#define  BUTTON1        A6
#define  BUTTON2        A7
#define  BUTTON3        A8

#define  JOY_SWITCH     A9      // pulls line down when pressed
#define  JOY_nINT       A10     // active low interrupt input
#define  JOY_nRESET     A11     // active low reset output

AndroidAccessory acc = AndroidAccessory("UCL",
		     "Engagement Sensor",
		     "Flaxman Gallery Armature sensor",
		     "0.1",
		     "http://www.ucl.ac.uk/museums/uclart/about/collections/objects/flaxman-gallery",
		     "0000000012345678");

//TODO: Remove this? I don't know what it does.
// 10M ohm resistor on demo shield
CapacitiveSensor touch_robot(TOUCH_SEND, TOUCH_RECV);

void setup();
void loop();
uint16_t read_ultrasound();
byte read_motion_sensor();

void init_ultrasound() {
 //TODO: Implement! 
}

void init_motion_sensor() {
  //TODO: Implement!
}

void setup()
{
	Serial.begin(115200);
	Serial.print("\r\nStart");

        init_ultrasound();
        init_motion_sensor();

	// autocalibrate OFF
	touch_robot.set_CS_AutocaL_Millis(0xFFFFFFFF);

	acc.powerOn();
}

void loop()
{
	byte err;
	byte idle;
	static byte count = 0;
	byte msg[3];
	long touchcount;

	if (acc.isConnected()) {
		//int len = acc.read(msg, sizeof(msg), 1);
		int i;
		byte b;
		uint16_t val;


		switch (count++ % 0x8) {
		case 0:
			val = read_ultrasound();
			msg[0] = 0x1;
			msg[1] = val >> 8;
			msg[2] = val & 0xff;
			acc.write(msg, 3);
			break;

		case 0x4:
			val = read_motion_sensor();
			msg[0] = 0x2;
			msg[1] = val >> 8;
			msg[2] = 0; // This block isn't used
			acc.write(msg, 3);
			break;
		}
	} else {
		// reset outputs to default values on disconnect
		analogWrite(LED1_RED, 255);
		analogWrite(LED1_GREEN, 255);
		analogWrite(LED1_BLUE, 255);
		analogWrite(LED2_RED, 255);
		analogWrite(LED2_GREEN, 255);
		analogWrite(LED2_BLUE, 255);
		analogWrite(LED3_RED, 255);
		analogWrite(LED3_GREEN, 255);
		analogWrite(LED3_BLUE, 255);
		digitalWrite(RELAY1, LOW);
		digitalWrite(RELAY2, LOW);
	}

	delay(10);
}

uint16_t read_ultrasound() {
  //TODO: Implement!
  return 0;
}

byte read_motion_sensor() {
 //TODO: Implement!
  return 0; 
}


