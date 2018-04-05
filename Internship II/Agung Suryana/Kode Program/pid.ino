#include <PID_v1.h>

// Libraries for the DS18B20 sensor
#include <OneWire.h>
#include <DallasTemperature.h>

// DS18B20 on PIN 6 on the Arduino
#define ONE_WIRE_BUS 2

//Solid state relay on PIN 5 on the Arduino
#define RELAY_PIN 5
#define LED_PIN 13

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

//Define Variables we'll be connecting to
double Setpoint, Input, Output;

//Specify the links and initial tuning parameters
double Kp=8, Ki=4, Kd=2;
PID myPID(&Input, &Output, &Setpoint, Kp, Ki, Kd, DIRECT);

int WindowSize = 10000;
unsigned long windowStartTime;

void setup()
{
  
  pinMode(RELAY_PIN, OUTPUT);
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(RELAY_PIN, LOW);
  digitalWrite(LED_BUILTIN, LOW);
  Serial.begin(9600);
  Serial.println("Starting");
  windowStartTime = millis();

  //initialize the variables we're linked to
  Setpoint = 30;

  //tell the PID to range between 0 and the full window size
  myPID.SetOutputLimits(0, WindowSize);

  //turn the PID on
  myPID.SetMode(AUTOMATIC);
}

void loop()
{
  sensors.requestTemperatures();
  Input = sensors.getTempCByIndex(0);
  Serial.print("Temperature: ");
  Serial.println(Input);
  myPID.Compute();

  /************************************************
   * turn the output pin on/off based on pid output
   ************************************************/
  if (millis() - windowStartTime > WindowSize)
  {
    //time to shift the Relay Window
    windowStartTime += WindowSize;
  }
  if (Output < millis() - windowStartTime) 
//    digitalWrite(RELAY_PIN, HIGH);
    digitalWrite(LED_BUILTIN, HIGH);
  else 
//    digitalWrite(RELAY_PIN, LOW);
    digitalWrite(LED_BUILTIN, LOW);

}
