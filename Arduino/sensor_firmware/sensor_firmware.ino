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

#define US 22
#define MS 24

AndroidAccessory acc = AndroidAccessory("UCL",
		     "Engagement Sensor",
		     "Flaxman Gallery Armature sensor",
		     "1.0",
		     "http://www.ucl.ac.uk/museums/uclart/about/collections/objects/flaxman-gallery",
		     "0000000012345678");

//TODO: Remove this? I don't know what it does.
// 10M ohm resistor on demo shield
CapacitiveSensor touch_robot(TOUCH_SEND, TOUCH_RECV);

void setup();
void loop();
uint16_t read_ultrasound();
byte read_motion_sensor();

void setup()
{
	Serial.begin(115200);
	Serial.print("\r\nStart");


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
			msg[1] = val;
			msg[2] = msg[1]; // This block isn't used
			acc.write(msg, 3);
			break;
		}
	} else {
		// reset outputs to default values on disconnect
		delay(150);
	}

	delay(10);
}

uint16_t time_to_cm(int microseconds) {
  return SPEED_OF_SOUND * microseconds / 2;
}


// NOTE: This method is partially based on the one found at:
// http://arduino.cc/en/Tutorial/ping
uint16_t read_ultrasound() {
  
  int pulseDuration;
  pinMode(US, OUTPUT);
  digitalWrite(US, LOW);
  delayMicroseconds(2);
  digitalWrite(US, HIGH);
  delayMicroseconds(5);
  digitalWrite(US, LOW);
  
  pinMode(US, INPUT);
  pulseDuration = pulseIn(US, HIGH);
  
  return time_to_cm(pulseDuration);
}


byte read_motion_sensor() {
   pinMode(MS, INPUT);
   return digitalRead(MS) == HIGH ? 0xFF : 0; 
}


