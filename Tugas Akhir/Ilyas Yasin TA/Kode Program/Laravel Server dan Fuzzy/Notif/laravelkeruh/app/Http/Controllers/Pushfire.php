<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use Firebase\Firebase;
use Carbon\Carbon;
use DateTime;

class Pushfire extends Controller
{
    public function Push(Request $request){
           $kekeruhan = 21;//$kekeruhan =  $request->input('kr');
           //$suhu =  $request->input('sh');
           $suhu = 18;
           $idarduino = 'AR01';
           $timeNow = Carbon::now()->timestamp;
           $milis = $timeNow % 1000;
            $ts = intval($timeNow / 1000);
            $date = DateTime::createFromFormat('U', $ts);
            $str = $date->format(date('d-m-Y_H:i:s'));
           $fb = Firebase::initialize("https://kekeruhanhydroponics.firebaseio.com/");
            if ($kekeruhan < 10 && $suhu < 15 ) {
                 $kualitasair = 'jernih';
                  $kondisisuhu = 'low';
                 $nodePushContent = $fb->set('/Kekeruhan/'.$idarduino.'/'.$timeNow, [
                                    'Kekeruhan'   => $kekeruhan,
                                    'Kondisi Kualitasair' => $kualitasair,
                                    'suhu' => $suhu,
                                    'kondisi Suhu' => $kondisisuhu,
                  ]);
            }
            if ($kekeruhan > 20 && $kekeruhan < 25 && $suhu > 15 && $suhu < 25){
                $kualitasair = 'Normal';
                $kondisisuhu = 'Normal';
                 $nodePushContent = $fb->set('/Kekeruhan/'.$idarduino.'/'.$timeNow, [
                                    'Kekeruhan'   => $kekeruhan,
                                    'Kondisi Kualitasair' => $kualitasair,
                                    'suhu' => $suhu,
                                    'kondisi Suhu' => $kondisisuhu,
                                    
                  ]);
            }
            if ($kekeruhan > 25 && $kekeruhan < 30 && $suhu > 25 && $suhu < 40 ) {
                 $kualitasair = 'Keruh';
                 $kondisisuhu = 'low';
                 $nodePushContent = $fb->set('/Kekeruhan/'.$idarduino.'/'.$timeNow, [
                                    'Kekeruhan'   => $kekeruhan,
                                    'Kondisi Kualitasair' => $kualitasair,
                                    'suhu' => $suhu,
                                    'kondisi Suhu' => $kondisisuhu,
                  ]);
            // Untuk mengirim Notifikasi ke Android apabila Nilai kekekruhan meningkat hight or Keruh
            $url = "https://fcm.googleapis.com/fcm/send";
            $token = //"f8JwA-XArKc:APA91bElQfKNfFVPwTefnCzy7thgYLwraCJZMVkcjZFCSM8s8RhH0PYbXRW1Vuojc3C27MBgVcFrsDTvFY8lSYNRCkxsTypVy6fPew1_AYmd1AIaQEAsrDeFLuPU-4wj1LyTmqi_XVQ9";
            $serverKey = 'AAAAX9sti_A:APA91bFvv0coQjxvbfApf5rDR_lI3vhq9AMvi0f5gEVCLh2Uv_t4cEtTrQvooHSe3VwHaOUf4aL0qj02gROrvLlMuCqbbf0FX5-ysf3LNo3eaawE1jUmCXc0urEXc5BCczLZOQjl2E-1';
            $title = $kekeruhan;
            $body = $kualitasair;
            $notification = array('title' =>$title , 'body' => $body, 'sound' => 'default', 'badge' => '1');
            $arrayToSend = array('to' => $token, 'notification' => $notification,'priority'=>'high');
            $json = json_encode($arrayToSend);
            $headers = array();
            $headers[] = 'Content-Type: application/json';
            $headers[] = 'Authorization: key='. $serverKey;
            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_CUSTOMREQUEST,"POST");
            curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
            curl_setopt($ch, CURLOPT_HTTPHEADER,$headers);
            //Send the request
            $response = curl_exec($ch);
            //Close request
            if ($response === FALSE) {
            die('FCM Send Error: ' . curl_error($ch));
            }
            curl_close($ch);
             return response()->json('Notif Diaktifkan');
        }
     return response()->json(["'id':"."'".$idarduino."'","'kekeruhan':"."'".$kekeruhan."'","'kondisi air':"."'".$kualitasair."'","'Suhu':"."'".$suhu."'"]);
       
    }


    public function suhu(){
        $suhu = 9;
                $kondisi = '';
           // $suhu =  $request->input('suhu');
           $idarduino = 'AR01';
           $timeNow = Carbon::now()->timestamp;
           $milis = $timeNow % 1000;
            $ts = intval($timeNow / 1000);
            $date = DateTime::createFromFormat('U', $ts);
            $str = $date->format(date('d-m-Y_H:i:s'));
           $fb = Firebase::initialize("https://kekeruhanhydroponics.firebaseio.com/");
        if ($suhu < 10){
            $kondisi = 'Jernih';
        } else if ($suhu > 10 && $suhu < 30){
            //  $kondisi [0] (10 - $suhu)/(30 - 10);
             echo $kondisi = 'Normal';
        } else {
            $kondisi [0] = 0;

        }  if ($suhu < 10){
             $kondisi [1] = 0;
             echo $kondisi = 'Rendah';
        } else if ($suhu > 10 && $suhu < 30){
            //  $kondisi [1] (30 - $suhu)/(30 - 10);
             echo $kondisi = 'Normal'; 
        } else {
             $kondisi [1] = 0;

        } if ($suhu > 30 && $suhu < 50){
            // $kondisi [2] (50 - $suhu)/(30-50);
             echo $kondisi = 'Tinggi'; 
        } else {
            $kondisi [2] = 1;
        }
        echo $suhu;
    }

    public function evaluasi(){
        $k = integer;
        $i = integer;
            for($k = 0; $k < 2; $k++){
                for($i = 0; $i < 2; $i++){
                    $kualair = min($keruh[$k], $suhu[$j]);
                    $rule [$i][$k] = $kualair;
                }
        }
          $rules0 = [0][0]; // (jernih, sedang = jernih)
          $rules1 = [0][1]; // (jernih, normal = jernih)
          $rules2 = [0][2]; // (jernih, tinggi = jernih)
          
          $rules3 = [1][0]; // (normal, sedang = jernih)
          $rules4 = [1][1]; // (normal, normal = normal)
          $rules5 = [1][2]; // (normal, tinggi = keruh)

          $rules6 = [2][0]; // (keruh, sedang = keruh)
          $rules7 = [2][1]; // (keruh, normal = keruh)
          $rules8 = [2][2]; // (keruh, tinggi = keruh)
    }
    public function Defuzzification(){
        // metode sugeno (weighted average)
        $rules0 = [0][0]; // (jernih, sedang = jernih)
        $rules1 = [0][1]; // (jernih, normal = jernih)
        $rules2 = [0][2]; // (jernih, tinggi = jernih)
          
        $rules3 = [1][0]; // (normal, sedang = jernih)
        $rules4 = [1][1]; // (normal, normal = normal)
        $rules5 = [1][2]; // (normal, tinggi = keruh)

        $rules6 = [2][0]; // (keruh, sedang = keruh)
        $rules7 = [2][1]; // (keruh, normal = keruh)
        $rules8 = [2][2]; // (keruh, tinggi = keruh)
        $k = array();
        $i = array();

        $jernih = 10;
        $normal = 30;
        $keruh = 50;
        
        $pwm = ( $rules0 * $jernih) + ( $rules1 * $jernih) + ( $rules2 * $jernih) + 
             ( $rules3 * $normal) + ( $rules4 * $normal) + ( $rules5 * $normal) +
            ( $rules6 * $keruh) + ( $rules7 * $keruh) + ( $rules8 * $keruh);
        $defus = 0;
         for($k = 0; $k < 2; $k++){
                for($i = 0; $i < 2; $i++){
                    $defus = $defus + $rules[$i][$k];
                }
                $pwm = $pwm / $defus;

        }
    }
}
