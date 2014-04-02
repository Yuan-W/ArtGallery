#define US 22
#define SPEED_OF_SOUND 0.034

uint16_t read_motion_sensor();


void setup()
{
	Serial.begin(115200);
	Serial.print("\r\nStart");


}

void loop()
{
  Serial.print(read_motion_sensor());
  Serial.println(" cm");
  
  delay(100);
}

uint16_t time_to_cm(int microseconds) {
  //Serial.println(microseconds);
  return SPEED_OF_SOUND * microseconds / 2;
}

uint16_t read_motion_sensor() {
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
