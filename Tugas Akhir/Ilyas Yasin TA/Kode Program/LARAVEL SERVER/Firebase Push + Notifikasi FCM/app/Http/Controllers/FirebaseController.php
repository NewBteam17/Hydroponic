<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use Firebase\Firebase;

class FirebaseController extends Controller
{
    public function PushServer(){
       
            $kekeruhan = 9;
            if($kekeruhan < 10) { 
                 $kualitasair = 'Jernih';
                 $fb = Firebase::initialize("https://kekeruhanhydroponics.firebaseio.com/");
                 $nodePushContent = $fb->push('KekeruhanMonitoring', array(
            'Kekeruhan' => $kekeruhan,
            'Kualitasair' => $kualitasair,
                       ));
            }
            if ($kekeruhan > 20 && $kekeruhan < 25 ){
                 $kualitasair = 'Normal';
                 $fb = Firebase::initialize("https://kekeruhanhydroponics.firebaseio.com/");
                 $nodePushContent = $fb->push('KekeruhanMonitoring', array(
            'Kekeruhan' => $kekeruhan,
            'Kualitasair' => $kualitasair,
                       ));
            } 
            if ($kekeruhan > 25 && $kekeruhan < 30 ) {
                 $kualitasair = 'Keruh';
                 $fb = Firebase::initialize("https://kekeruhanhydroponics.firebaseio.com/");
                 $nodePushContent = $fb->push('KekeruhanMonitoring', array(
            'Kekeruhan' => $kekeruhan,
            'Kualitasair' => $kualitasair,
                       ));
            // Untuk mengirim Notifikasi ke Android apabila Nilai kekekruhan meningkat hight or Keruh
            $url = "https://fcm.googleapis.com/fcm/send";
            $token = "f8JwA-XArKc:APA91bElQfKNfFVPwTefnCzy7thgYLwraCJZMVkcjZFCSM8s8RhH0PYbXRW1Vuojc3C27MBgVcFrsDTvFY8lSYNRCkxsTypVy6fPew1_AYmd1AIaQEAsrDeFLuPU-4wj1LyTmqi_XVQ9";
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
        }
        
    }
}
