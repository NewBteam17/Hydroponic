<?php

use Illuminate\Http\Request;

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('pushfirebase','FirebaseController@PushServer');
Route::get('pushserver','Pushfire@Push');
Route::get('febby','febby@push');
Route::get('fuzzy','FirebaseController@fuzzy');
Route::post('fuzzy2','FirebaseController@fuzzy2');
Route::get('server','FirebaseController@server');
Route::get('evaluasi','FirebaseController@evaluasi');

Route::get('defuzi','FirebaseController@Defuzzification');