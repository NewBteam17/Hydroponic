<?php 

public function PushServer(Request $request){
           $kekeruhan = 21;
           $suhu = 25;
           // $kekeruhan =  $request->input('kr');
           $idarduino = 'AR01';
           $timeNow = Carbon::now()->timestamp;
           $milis = $timeNow % 1000;
            $ts = intval($timeNow / 1000);
            $date = DateTime::createFromFormat('U', $ts);
            $str = $date->format(date('d-m-Y_H:i:s'));
           $fb = Firebase::initialize("https://kekeruhanhydroponics.firebaseio.com/");
           // $kualitasair = $request->input('kl');
           //rendah
            if ($kekeruhan < 10 && $suhu < 10 ) {
            } 
            else if ($kekeruhan > 10 && $kekeruhan < 20 && $suhu > 10 && $suhu < 20 ) {
                $kualitasair = 'Jernih'; 
                 $kondisisuhu = 'Rendah';
                 $kekeruhan [0] = 0;
                 $suhu [0] = 0;
                 $nodePushContent = $fb->set('/Kekeruhan/'.$idarduino.'/'.$timeNow, [
                                    'Kekeruhan'   => $kekeruhan,
                                    'Kualitasair' => $kualitasair,
                                    'Suhu' => $suhu,
                                    'Kondisi'=> $kondisisuhu,
                  ]);
            }
            // sedang
            if ($kekeruhan < 10 && $suhu < 10 ){
            } 
            else if ($kekeruhan > 10 && $kekeruhan < 20 && $suhu > 10 && $suhu < 20 ){
            } 
            else if ($kekeruhan > 20 && $kekeruhan < 40 && $suhu > 20 && $suhu < 40){
                $kualitasair = 'Normal';
                   $kondisisuhu = 'Normal';
                   $kekeruhan [1] = 0;
                 $suhu [1] = 0;
                 $nodePushContent = $fb->set('/Kekeruhan/'.$idarduino.'/'.$timeNow, [
                                    'Kekeruhan'   => $kekeruhan,
                                    'Kualitasair' => $kualitasair,
                                    'Suhu' => $suhu,
                                    'Kondisi'=> $kondisisuhu,
                  ]);
            } 
            if ($kekeruhan <20 && $suhu < 20 ) {
        } else if ($kekeruhan > 20 && $kekeruhan < 30 && $suhu > 20 && $suhu < 30){
        } else ($kekeruhan > 30 && $suhu > 30) {
            $kualitasair = 'Keruh';
                 $kondisisuhu = 'Tinggi';
                 $kekeruhan [2] = 1;
                 $suhu [2] = 1;
                 $nodePushContent = $fb->set('/Kekeruhan/'.$idarduino.'/'.$timeNow, [
                                    'Kekeruhan'   => $kekeruhan,
                                    'Kualitasair' => $kualitasair,
                                    'Suhu' => $suhu,
                                    'Kondisi'=> $kondisisuhu,
                  ]);
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
        return response()->json('Insert Berhasil');
    }
?>