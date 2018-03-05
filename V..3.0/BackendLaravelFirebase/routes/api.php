<?php

use Illuminate\Http\Request;
//ServerTitle
Route::get('ServerTitle','ServerTitleController@serverTitle');
//pushServertitle
Route::get('PushServer','ServerTitleController@PushServer');
Route::get('UpdateServer','ServerTitleController@server1');


//Temperatur
Route::post('temperaturpush','TemperaturController@PushServer');



Route::post('postrtc','TemperaturController@PushRTC');
Route::post('postcontrol','TemperaturController@PushControl');

//Route::get('updst/p1/{prm1?}', 'TemperaturController@updateStatus');
Route::post('updst', 'TemperaturController@updateStatus');

Route::post('userpush','TemperaturController@PushUser'); //UserRoute
Route::post('perangkatpush','TemperaturController@PushPerangkat'); //UserRoute
Route::get('temperaturshow','TemperaturController@serverTitle');
Route::get('temperaturedelete','TemperaturController@DeleteServer');
Route::get('temperatureupdate','TemperaturController@UpdateServer');