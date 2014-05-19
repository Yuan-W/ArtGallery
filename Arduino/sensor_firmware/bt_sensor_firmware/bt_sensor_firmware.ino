// This program uses ideas from the BlueSMiRF tutorial at
// https://learn.sparkfun.com/tutorials/using-the-bluesmirf

#include <SoftwareSerial.h>

#define US 22
#define MS 24
#define BT_RX 31  // Bluetooth serial receive - use this to output TO the module
#define BT_TX 30  // Bluetooth serial transfer - use this to get input FROM module

#define SPEED_OF_SOUND 0.034 // This speed of sound is in cm.us^-1


void setup();
void loop();
uint16_t read_ultrasound();
byte read_motion_sensor();

SoftwareSerial bt(BT_TX, BT_RX);

void setup()
{
	Serial.begin(9600);
        pinMode(BT_TX, INPUT);
        pinMode(BT_RX, OUTPUT);
        
        bt.begin(115200);
        bt.print("$");
        bt.print("$");
        bt.print("$");

        delay(100);
        
        bt.println("U,9600,N");
        bt.begin(9600);
        

}

void loop()
{
	if (bt.available()) {
          Serial.print((char)bt.read());
        }
        
        if (Serial.available()) {
          bt.print((char)Serial.read());
        }
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


