<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Firebase\Firebase;
use App\Http\Controllers\GuzzleHttp\Client;
class ServerController extends Controller
{
    public function serverTitle(){
        $fb = Firebase::initialize("https://fir-dcfae.firebaseio.com", "nMIiIlrdEWCjrzLqTzjVJfu5b5ERG2unxdERLO4u");
        $nodeGetContent = $fb->get('title');
       return response()->json($nodeGetContent, 201);
    }

    public function server1(){
        $fb = Firebase::initialize("https://fir-dcfae.firebaseio.com", "nMIiIlrdEWCjrzLqTzjVJfu5b5ERG2unxdERLO4u");
        $nodeSetContent = $fb->set('temperatur', array(
            'kelembaban' => '4'
        ));
         return response()->json('updated berhasil');
    }

    public function PushServer(Request $request){
         $fb = Firebase::initialize("https://fir-dcfae.firebaseio.com", "nMIiIlrdEWCjrzLqTzjVJfu5b5ERG2unxdERLO4u");
        $nodePushContent = $fb->push('title', array(
            'name' => 'ilyas yasin',
            'kelas' => 'D4 Teknik Informatika',
            'kampus' => 'Poltekpos',
            ));
            return response()->json([
            'insert berhasil'
  ]);
    }

}
