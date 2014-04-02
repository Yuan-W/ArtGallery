#define MOTION_OUT A0


byte read_motion_sensor();


void setup()
{
	Serial.begin(115200);
	Serial.print("\r\nStart");


}

void loop()
{
  if (read_motion_sensor() != 0) {
    Serial.print("Motion\n"); 
  } else {
    Serial.print("No Motion\n");
  }
  
  delay(100);
}


byte read_motion_sensor() {
  return digitalRead(MOTION_OUT) == HIGH ? 0xFF : 0; 
}
