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
    return view('welcome');
});

Auth::routes();

Route::get('/backend', 'BackendController@index');

Route::resource('/backend/setting-province', 'LocationProvinceController');

Route::resource('/admin/posts', 'Admin\\PostsController');
Route::resource('backend/location-province', 'LocationProvinceController');