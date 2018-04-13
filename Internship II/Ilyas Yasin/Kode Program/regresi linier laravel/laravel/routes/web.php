<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('admin.index');
});

Route::get('hitung','RegresiController@hitung');

Route::resource('forecasting', 'forecastingController');
Route::get('hitungB','forecastingController@PersamaanNilaiB');

Route::get('/home', 'HomeController@index')->name('home');
Route::get('/home', 'HomeController@index')->name('home');

Route::resource('jenis_tanaman', 'jenis_tanamanController');
Route::resource('jenis_ph', 'jenis_phController');
Auth::routes();
Route::get('/loginregresi', function () {
    return view('login');
});
Route::get('/home', 'HomeController@index')->name('home');
