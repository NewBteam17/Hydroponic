<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Carbon\Carbon;
use Firebase\Firebase;
use Firebase\Criteria;
use App\Http\Controllers\GuzzleHttp\Client;
use DateTime;

class TemperaturController extends Controller
{

    public function PushControl(Request $request){

        $idarduino = $request->input('id');
        $diffTime = $request->input('diff');
        $lastOn = $request->input('last');
        $status = $request->input('stat');

        $fb = Firebase::initialize("YourFirebaseURL", "YourApiKeyFirebase"); //Ex : ("https://myfirebase.firebaseio.com/","okjtg6878hj79897yuhj")
        $nodePushContent = $fb->push('controlling', array( //controling is node
            'idarduino' => $idarduino, // param and value 1
            'diffTime' => $diffTime, // param and value 2
            'lastOn' => $lastOn, // param and value 3
            'status' => $status, // param and value 4

            ));
            return response()->json(['insert berhasil']);
    }



    public function PushPerangkat(Request $request){


        $fb = Firebase::initialize("YourFirebaseURL", "YourApiKeyFirebase");

        //$tglm = date('d-m-Y_H:i:s'); //date('d.m.Y H:i:s');
        $idarduino='AR02';
        $current_timestamp = Carbon::now()->timestamp;

        $nodeWriteContent = $fb->set('/perangkat/'.$idarduino, [
                                'aktif' => 'on', //status on/off
                                'tglprod' => $current_timestamp, //time production
                        ]);
        return response()->json(['insert berhasil'.$current_timestamp]);

    }



    public function PushServer(Request $request){

        //Request from arduino
        $idarduino = $request->input('id');
        $ec = $request->input('e');
        $ph = $request->input('p');
        $suhu = $request->input('s');
        $pupukA = $request->input('A');
        $pupukB = $request->input('B');
        $ketinggianWadah = $request->input('W');


        $fb = Firebase::initialize("YourFirebaseURL", "YourApiKeyFirebase");

        //convert date to unixtimestamp
        $timeNow = Carbon::now()->timestamp;
        $milis = $timeNow % 1000;
        $ts = intval($timeNow / 1000);
        $date = DateTime::createFromFormat('U', $ts);
        $str = $date->format(date('d-m-Y_H:i:s'));

        $nodeWriteContent = $fb->set('/pantau/'.$idarduino.'/'.$timeNow, [
                                'ec' => $ec, 
                                'ketinggianWadah' => $ketinggianWadah, 
                                    'ph' => $ph, 
                                    'pupukA' => $pupukA, 
                                    'pupukB' => $pupukB, 
                                    'suhu' => $suhu, 
                        ]);
        return response()->json([$idarduino]);

    }


    public function UpdateServer(){
       $fb = Firebase::initialize("YourFirebaseURL", "YourApiKeyFirebase");
        $nodeUpdateContent = $fb->update('temperatur', array(
            'kelembaban_EC' => '20',
            'kelembaban_PH' => '20',
            ));
            return response()->json(['update berhasil']);
    }

    public function PushUser(Request $request){
        $idarduino = $request->input('id');
        $email = $request->input('email');
        $level = $request->input('level');

        $fb = Firebase::initialize("YourFirebaseURL", "YourApiKeyFirebase");
        $nodePushContent = $fb->push('users', array(
            'idarduino' => $idarduino,
            'email' => $email,
            'level' => $level,
            ));
            return response()->json(['insert User berhasil']);
    }

    public function updateStatus(Request $request){
        $idarduino = $request->input('id');
        $fb = Firebase::initialize("YourFirebaseURL", "YourApiKeyFirebase");

         $lastOnDate = $fb->get('/kendali/'.$idarduino.'/lastOn');
         $abc = Carbon::createFromTimestamp($lastOnDate)->format('d-m-Y_H:i:s');
         $diffTime = $fb->get('/kendali/'.$idarduino.'/diffTime');
         $manual = $fb->get('/kendali/'.$idarduino.'/manual');
         $timeNowStamp = Carbon::now()->timestamp;
         $timeNow = Carbon::createFromTimestamp($timeNowStamp)->format('d-m-Y_H:i:s');

         $start_date = DateTime::createFromFormat('d-m-Y_H:i:s', $abc);
         $end_date = DateTime::createFromFormat('d-m-Y_H:i:s', $timeNow);

            if(!empty($diffTime))
            {

                $interval = $start_date->diff($end_date);

                    $d2s = $interval->d * 86400;
                    $h2s = $interval->h * 3600;
                    $i2s = $interval->i * 60;
                    $s = $interval->s;
                    $waktutemp = 0;

                    $hasil = $d2s + $h2s + $i2s + $s;

                    if ($hasil >= $diffTime)
                     {
                            $status = "on";
                            $lastOnDate = $timeNowStamp;
                
                     } else if ($manual == "on"){
                            $status = "on";
                     }
                    else
                     {
                            $status = "off";
                     }

                    //
                        $nodeWriteContent = $fb->set('/kendali/'.$idarduino, [
                                      'diffTime' => $diffTime, //ganti
                                       'lastOn' => $lastOnDate, //ganti
                                       'status' => $status,
                                       'manual' => 'off'   //kendali manual
                               ]);

            }
            else
            {
                $nodeWriteContent = $fb->set('/kendali/'.$idarduino, [
                              'diffTime' => '259200', //ganti
                              'lastOn' => $timeNowStamp, //ganti
                              'status' => 'on',
                              'manual' => 'on'   //kendali manual
                               ]);

                        $status = "on";
            }

            return response()->json([$status]);

}

}
