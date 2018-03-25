<?php

use Illuminate\Http\Request;

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('pushfirebase','FirebaseController@PushServer');
Route::get('pushserver','Pushfire@Push');
Route::get('suhu','FirebaseController@suhu');