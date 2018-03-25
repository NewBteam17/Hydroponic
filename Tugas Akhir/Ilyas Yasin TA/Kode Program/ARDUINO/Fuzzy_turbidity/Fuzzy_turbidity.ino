 #include <Wire.h>
static float kekeruhan;
static float teg;

void setup(){
  Serial.begin(9600);
  }
void loop(){
  int val = analogRead(A0);
  teg = val*(5.0/1024);
  kekeruhan = 100.00-(teg/4.16)*100.00;  
  if (kekeruhan < 10){  
   Serial.println("Kualitas Air jernih");  
   Serial.println(kekeruhan);
   Serial.print(" ");
   Serial.println(" Nilai ADC Low"); 
   Serial.println(teg);
   Serial.print(" ");
  }  
  if (kekeruhan > 30 && kekeruhan < 40){  
   Serial.println(kekeruhan);
   Serial.print(" ");
   Serial.println(" Kualitas Air Normal"); 
   Serial.println(teg);
   Serial.print(" ");
   Serial.println(" Nilai ADC Normal"); 
  }  
  if (kekeruhan > 40){
   Serial.println(kekeruhan);
   Serial.print(" ");
   Serial.println(" Kualitas Air Keruh");  
   Serial.println(teg);
   Serial.print(" ");
   Serial.println(" Nilai ADC Hight "); 
  } 
    delay (5000);
}
