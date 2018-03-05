 #include "Arduino.h"
#include <SoftwareSerial.h>
#include <Servo.h>

#define DEBUG true

// define ec
#include <OneWire.h>
#define StartConvert 0
#define Readtemp 1

//define ultraSonik
#define trigPin1 13
#define echoPin1 12 
#define trigPin2 11
#define echoPin2 10
#define trigPin3 9
#define echoPin3 8
#define trigPin4 7
#define echoPin4 6

// define relay
#define RELAY_ON 0
#define RELAY_OFF 1
#define RELAY_1  4   // pompa 1 isi air
#define RELAY_2  3   // mixer
#define RELAY_3  2   // pompa 2 narik air

//define pH
#define SensorPin A5            //pH meter Analog output to Arduino Analog Input 0
#define Offset 0.00            //deviation compensate
#define samplingInterval 20
#define LED 13
#define ArrayLenth  40    //times of collection
int pHArray[ArrayLenth];   //Store the average value of the sensor feedback
int pHArrayIndex=0;

Servo servo1;

//waktu komunikasi ke heroku
unsigned long previousMillis = 0;
const long intervalSendDataId2 = 1000;
const int maxcountSenDataId2 = 5; //simulasikan total 5 menit
unsigned long countSenDataId2 = 0;

const String HOST = "+your localhost at same ssid, ifconfig+ ";

//ec
const byte numReadings = 20;     //the number of sample times
byte ECsensorPin = A4;  //EC Meter analog output,pin on analog 4
byte DS18B20_Pin = 5; //DS18B20 signal, pin on digital 5
unsigned int AnalogSampleInterval=25,printInterval=700,tempSampleInterval=850;  //analog sample interval;serial print interval;temp sample interval
unsigned int readings[numReadings];      // the readings from the analog input
byte index = 0;                  // the index of the current reading
unsigned long AnalogValueTotal = 0;                  // the running total
unsigned int aAvg = 0,avgV=0;                // the average
unsigned long AnalogSampleTime,printTime,tempSampleTime;
float temp,ECcurrent;

//temp chip i/o
OneWire ds(DS18B20_Pin);  // on digital pin 6
long duration, distance, A,B,W,M;

bool mixPumpRun = false;

void setup() {
  //for PH
  pinMode(LED,OUTPUT);
  Serial.begin(9600);

  //for ec
  // initialize all the readings to 0:
  for (byte thisReading = 0; thisReading < numReadings; thisReading++)
    readings[thisReading] = 0;
  TempProcess(StartConvert);   //let the DS18B20 start the convert

  AnalogSampleTime=millis();
  printTime=millis();
  tempSampleTime=millis();

  //setup ultrasonik

  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);
  pinMode(trigPin3, OUTPUT);
  pinMode(echoPin3, INPUT);
  pinMode(trigPin4, OUTPUT);
  pinMode(echoPin4, INPUT);

  pinMode(A3, OUTPUT);
  servo1.attach(A3);        //memilih pin 3 digital untuk servoku

  pinMode(RELAY_1, OUTPUT);
  pinMode(RELAY_2, OUTPUT);
  pinMode(RELAY_3, OUTPUT);
  digitalWrite(RELAY_1, RELAY_OFF);
  digitalWrite(RELAY_2, RELAY_OFF);
  digitalWrite(RELAY_3, RELAY_OFF);
  Serial.begin(9600);

  //setup esp
  delay(500);
  Serial.begin(9600); // hardware serial

  connectWifi();
  delay(100);
}

// The loop function is called in an endless loop
void loop() {

  //loop ultra sonik
  SonarSensor(trigPin1, echoPin1);
  A = distance;
  SonarSensor(trigPin2, echoPin2);
  B = distance;
  SonarSensor(trigPin3, echoPin3);
  W = distance;
  SonarSensor(trigPin4, echoPin4);
  M = distance;

  //loop ec
  if(millis()-AnalogSampleTime>=AnalogSampleInterval)
  {
    AnalogSampleTime=millis();
    // subtract the last reading:
    AnalogValueTotal = AnalogValueTotal - readings[index];
    // read from the sensor:
    readings[index] = analogRead(ECsensorPin);
    // add the reading to the total:
    AnalogValueTotal = AnalogValueTotal + readings[index];
    // advance to the next position in the array:
    index = index + 1;
    // if we're at the end of the array...
    if (index >= numReadings)
      // ...wrap around to the beginning:
      index = 0;
    // calculate the average:
    aAvg = AnalogValueTotal / numReadings;
  }

  if(millis()-tempSampleTime>=tempSampleInterval)
  {
    tempSampleTime=millis();
    temp = TempProcess(Readtemp);  // read the current temp from the  DS18B20
    TempProcess(StartConvert);                   //after the reading,start the convert for next reading
  }
  /*
   Every once in a while,print the information on the serial monitor.
   */
  if(millis()-printTime>=printInterval)
  {
    printTime=millis();
    avgV=aAvg*(float)5000/1024;

    float TempCoefficient=1.0+0.0185*(temp-25.0);    //temp compensation formula: fFinalResult(25^C) = fFinalResult(current)/(1.0+0.0185*(fTP-25.0));
    float CoefficientVolatge=(float)avgV/TempCoefficient;
    if(CoefficientVolatge<150){}//Serial.println("No solution!");   //25^C 1413us/cm<-->about 216mv  if the voltage(compensate)<150,that is <1ms/cm,out of the range
    else if(CoefficientVolatge>3300){}//Serial.println("Out of the range!");  //>20ms/cm,out of the range
    else
    {
      if(CoefficientVolatge<=448)ECcurrent=6.84*CoefficientVolatge-64.32;   //1ms/cm<EC<=3ms/cm
      else if(CoefficientVolatge<=1457)ECcurrent=6.98*CoefficientVolatge-127;  //3ms/cm<EC<=10ms/cm
      else ECcurrent=5.3*CoefficientVolatge+2278;                           //10ms/cm<EC<20ms/cm
      ECcurrent/=1000;    //convert us/cm to ms/cm
      //Serial.print(ECcurrent,2);  //two decimal
      //Serial.println("ms/cm");
    }
  }

  //loop ph
  static unsigned long samplingTime = millis();
  static float pHValue,voltage;
  if(millis()-samplingTime > samplingInterval)
  {
    pHArray[pHArrayIndex++]=analogRead(SensorPin);
    if(pHArrayIndex==ArrayLenth)pHArrayIndex=0;
    voltage = avergearray(pHArray, ArrayLenth)*5.0/1024;
    pHValue = 3.5*voltage+Offset;
    samplingTime=millis();
  }


  String request ="";
  String cmd = "";
  String cmd2 = "";
  String pjg = "";
  String closeCommand = "";
  String isi = "";

  countSenDataId2++;
  //countSenDataId2 = 1; //sementara

  if(countSenDataId2 >= maxcountSenDataId2){
    countSenDataId2 = 0;
    //{"id":"AR01","p":"244","e":"50","s":"0.00","A":"102","B":"153","W":"264"}
    request = "{\"id\":\"";
    request += "AR01";
    request += "\",\"p\":\"";
    request += pHValue;
    request += "\",\"e\":\"";
    request+= aAvg;
    request += "\",\"s\":\"";
    request += temp;
    request += "\",\"A\":\"";
    request += A;
    request += "\",\"B\":\"" ;
    request += B;
    request += "\",\"st\":\"";
    request += "tes";
    request += "\",\"W\":\"";
    request += W;
    request += "\"}";

    cmd2 = "POST /api/temperaturpush";
  }else{
    //{"id":","st":"ON"}
    request =  "{\"id\":\"";
    request += "AR01";
    request += "\"}";

    cmd2 = "POST /api/updst";
  }

  //send ke esp8266


  cmd = "AT+CIPSTART=\"TCP\",\"";
  cmd += HOST;
  cmd += "\",80\r\n";
  sendCommand(cmd, 2000, DEBUG);

  cmd2 += " HTTP/1.1\r\n";
  cmd2 += "Host: " + HOST + "\r\n";
  cmd2 += "Accept: application/json\r\n";
  cmd2 += "Content-Type: application/json\r\n";
  cmd2 += "Content-Length: ";
  cmd2 += request.length();
  cmd2 += "\r\n\r\n";
  cmd2 += request;

  pjg = "AT+CIPSEND=";
  pjg += cmd2.length();
  pjg += "\r\n";

  sendCommand(pjg, 2000, DEBUG);
  // tampung respon balik dari server
  isi = sendCommand(cmd2, 10000, DEBUG);
  //Serial.println(isi);

  //siap untuk diparsing
  //misal untuk Parsing dari laravel ["OFF"]
  int ygke = isi.indexOf("[") +2 ;
  int ygakhir = isi.indexOf("]") -1 ;
  //

  String result = isi.substring(ygke, ygakhir);

  // Serial.println(result);

  // Parsing dari laravel ["OFF"]
  ygke = result.indexOf("[");
  ygakhir = result.indexOf("]");
  String vStat = result.substring(ygke+1,ygakhir-1);

  // Serial.println(vId);
  //Serial.println("ini");
  Serial.println(vStat);

  if(vStat == "on"){
    mixPumpRun = true;
  } else {
    mixPumpRun = false;
  }

  delay(500);
  closeCommand = "AT+CIPCLOSE";
  closeCommand += "\r\n";
  sendCommand(closeCommand, 500, DEBUG);

    //SonarSensor(trigPin4, echoPin4);
    //M = distance;
    //Serial.println(M);
  if (mixPumpRun){
    //teknik delay mengakibatkan sistem menunggu
    int dlyRelay = 0;
    servo1.write(0);        
    delay(3000);              
    servo1.write(90);       
    delay(3000);              
    digitalWrite(RELAY_1, RELAY_ON);  //isi air bersih ??ml untuk (dianggap) sekian 10 detik
    //hitung M juga atau 10 detik, 10000 jadi 1000 karena delaynya 10
    M = 16;
    while(M > 9){
      SonarSensor(trigPin4, echoPin4);
      M = distance;
      if (dlyRelay > 20000){M = 0;}
      //Serial.println(M);
      delay(10000);
      dlyRelay++;
    }
    digitalWrite(RELAY_1, RELAY_OFF);
    digitalWrite(RELAY_2, RELAY_ON);  //mixing, untuk 3 menit
    delay(1 * 60 * 1000UL);
    digitalWrite(RELAY_2, RELAY_OFF);
    digitalWrite(RELAY_3, RELAY_ON);  //injeksi ke sistem hidroponik, dianggap sampai habis, 15 detik
    delay(60000);
    digitalWrite(RELAY_3, RELAY_OFF);
    //delay(10000);
    mixPumpRun = false;

  } else {
    digitalWrite(RELAY_1, RELAY_OFF);
    digitalWrite(RELAY_2, RELAY_OFF);
    digitalWrite(RELAY_3, RELAY_OFF);
    mixPumpRun = false;
  }

}

void SonarSensor(int trigPin,int echoPin)
{
  digitalWrite(trigPin, LOW);
  delayMicroseconds(8);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(8);
  digitalWrite(trigPin, LOW);
  delayMicroseconds(8);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;

}

String sendCommand(String command, const int timeout, boolean debug) {
  String response = "";

  //send the read character to the esp8266
  Serial.print(command);

  long int time = millis();

  while ((time + timeout) > millis()) {
    while (Serial.available()) {
      // The esp has data so display its output to the serial window
      // read the next character.
      char c = Serial.read();
      response += c;
    }
  }

  if (debug) {
    //Serial.print(response);
  }

  return response;
}

void connectWifi() {
  //Set-wifi
  //Serial.print("Restart Module...\n");
  sendCommand("AT+RST\r\n", 6000, DEBUG);//2000
  //Serial.print("Set wifi mode...\n");
  sendCommand("AT+CWMODE=1\r\n", 6000, DEBUG);//1000
  //Serial.print("Connect to access point...\n");
  sendCommand("AT+CWJAP=\"+SSID+\",\"+Password+\"\r\n", 1000, DEBUG);
  //Serial.print("Check IP Address...\n");
  sendCommand("AT+CIFSR\r\n", 6000, DEBUG); // get ip address
}

float TempProcess(bool ch)
{
  //returns the temp from one DS18B20 in DEG Celsius
  static byte data[12];
  static byte addr[8];
  static float tempSum;
  if(!ch){
    if ( !ds.search(addr)) {
      //Serial.println("no more sensors on chain, reset search!");
      ds.reset_search();
      return 0;
    }
    if ( OneWire::crc8( addr, 7) != addr[7]) {
      //Serial.println("CRC is not valid!");
      return 0;
    }
    if ( addr[0] != 0x10 && addr[0] != 0x28) {
      //Serial.print("Device is not recognized!");
      return 0;
    }
    ds.reset();
    ds.select(addr);
    ds.write(0x44,1); // start conversion, with parasite power on at the end
  }
  else{
    byte present = ds.reset();
    ds.select(addr);
    ds.write(0xBE); // Read Scratchpad
    for (int i = 0; i < 9; i++) { // we need 9 bytes
      data[i] = ds.read();
    }
    ds.reset_search();
    byte MSB = data[1];
    byte LSB = data[0];
    float tempRead = ((MSB << 8) | LSB); //using two's compliment
    tempSum = tempRead / 16;
  }
  return tempSum;
}

//ph looping
double avergearray(int* arr, int number){
  int i;
  int max,min;
  double avg;
  long amount=0;
  if(number<=0){
    Serial.println("Error number for the array to avraging!/n");
    return 0;
  }
  if(number<5){   //less than 5, calculated directly statistics
    for(i=0;i<number;i++){
      amount+=arr[i];
    }
    avg = amount/number;
    return avg;
  }else{
    if(arr[0]<arr[1]){
      min = arr[0];max=arr[1];
    }
    else{
      min=arr[1];max=arr[0];
    }
    for(i=2;i<number;i++){
      if(arr[i]<min){
        amount+=min;        //arr<min
        min=arr[i];
      }else {
        if(arr[i]>max){
          amount+=max;    //arr>max
          max=arr[i];
        }else{
          amount+=arr[i]; //min<=arr<=max
        }
      }//if
    }//for
    avg = (double)amount/(number-2);
  }//if
  return avg;
}