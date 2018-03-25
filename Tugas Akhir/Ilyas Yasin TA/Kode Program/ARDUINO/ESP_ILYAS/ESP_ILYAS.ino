#include "Arduino.h"
#include <SoftwareSerial.h>
#include <OneWire.h>

#define DEBUG true
  static float kekeruhan;
  static float teg;
 const String HOST = "kualitasair.000webhostapp.com"; //your localhost at same ssid, ifconfig
 SoftwareSerial ser(A2, A3); // RX 2, TX 3

  void setup(){
    // setup kekeruhan
  Serial.begin(9600);
  }
  
  void loop() {
  // script kekeruhan
  int val = analogRead(A0);
  teg = val*(5.0/1024);
  kekeruhan = 100.00-(teg/4.16)*100.00;    
  }

  void sendDataID(String prm1) {
  String request = "{\"kr\":\"" +prm1+ "\"}";
  String cmd = "AT+CIPSTART=\"TCP\",\"";
 
   cmd += HOST;
   cmd += "\",80\r\n";
   sendCommand(cmd, 600, DEBUG);
   delay(30);
 
   String cmd2 = "POST /api/pushserver";
   //cmd2 += String(svalFertInterval, DEC);
 
   cmd2 += " HTTP/1.1\r\n";
   cmd2 += "Host: " + HOST + "\r\n";
   cmd2 += "Accept: application/json\r\n";
   cmd2 += "Content-Type: application/json\r\n";
   cmd2 += "Content-Length: ";
   cmd2 += request.length();
   cmd2 += "\r\n\r\n";
   cmd2 += request;
 
   String pjg = "AT+CIPSEND=";
   pjg += cmd2.length();
   pjg += "\r\n";
 
   String closeCommand = "AT+CIPCLOSE";
   closeCommand += "\r\n";
 
   sendCommand(pjg, 600, DEBUG);
   delay(100);
   sendCommand(cmd2, 1000, DEBUG);
   delay(100);
   sendCommand(closeCommand, 500, DEBUG);
   delay(100);
}

String sendCommand(String command, const int timeout, boolean debug) {
   String response = "";
 
   ser.print(command); // send the read character to the esp8266
 
   long int time = millis();
 
   while ((time + timeout) > millis()) {
     while (ser.available()) {
       // The esp has data so display its output to the serial window
       char c = ser.read(); // read the next character.
       response += c;
     }
   }
 
   if (debug) {
     Serial.print(response);
   }
   return response;
 }

 void connectWifi() {
   //Set-wifi
   Serial.print("Restart Module...\n");
   sendCommand("AT+RST\r\n", 1000, DEBUG);
   delay(500);
   Serial.print("Set wifi mode...\n");
   sendCommand("AT+CWMODE=1\r\n", 1000, DEBUG); //
   delay(500);
   Serial.print("Connect to access point...\n");
   sendCommand("AT+CWJAP=\"Willy-Fachrul\",\"081214130007\"\r\n", 1000, DEBUG);
   delay(2000);
   Serial.print("Check IP Address...\n");
   sendCommand("AT+CIFSR\r\n", 1000, DEBUG); // get ip address
   delay(500);
 }

