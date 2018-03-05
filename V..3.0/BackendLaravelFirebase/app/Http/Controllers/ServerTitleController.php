<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Firebase\Firebase;
use App\Http\Controllers\GuzzleHttp\Client;
class ServerTitleController extends Controller
{
    public function serverTitle(){
        $fb = Firebase::initialize("https://fir-dcfae.firebaseio.com", "nMIiIlrdEWCjrzLqTzjVJfu5b5ERG2unxdERLO4u");
        $nodeGetContent = $fb->get('object');

        // var_dump ($nodeGetContent);
        // exit();
       return response()->json($nodeGetContent, 201);
    }

    public function server1(){
        $fb = Firebase::initialize("https://fir-dcfae.firebaseio.com", "nMIiIlrdEWCjrzLqTzjVJfu5b5ERG2unxdERLO4u");
        $nodeSetContent = $fb->set('temperatur', array(
            'kelembaban_EC' => '2.6',
            'kelembaban_PH' => '5.0',
        ));
         return response()->json('updatea berhasil');
    }

    public function PushServer(Request $request){
         $fb = Firebase::initialize("https://fir-dcfae.firebaseio.com", "nMIiIlrdEWCjrzLqTzjVJfu5b5ERG2unxdERLO4u");
        $nodePushContent = $fb->push('object', array(
            'name' => 'ilyas yasin',
            'kelas' => 'D4 Teknik Informatika',
            ));
            return response()->json(['insert berhasil']);
            
    }

    public function DeleteServer(){
        $fb = Firebase::initialize("https://fir-dcfae.firebaseio.com", "nMIiIlrdEWCjrzLqTzjVJfu5b5ERG2unxdERLO4u");
        $nodeDeleteContent = $fb->delete('object');
        return response()->json(['delete berhasil']);
    }

    public function UpdateServer(){
        $fb = Firebase::initialize("https://fir-dcfae.firebaseio.com", "nMIiIlrdEWCjrzLqTzjVJfu5b5ERG2unxdERLO4u");
        $nodeUpdateContent = $fb->update('object', array(
            'name' => 'required',
            'kelas' => 'required',
            ));
            return response()->json(['update berhasil']);
    }
}
