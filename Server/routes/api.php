<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

//Route::middleware('auth:api')->get('/user', function (Request $request) {
//    return $request->user();
//});


Route::get('register', 'API\APIClientController@register');

Route::group(['prefix' => 'api', 'middleware' => 'auth:api'], function () {
    Route::get('/medical-news', 'API\APIMedicalNewsController@listNews');

    Route::get('findDoctors', 'API\APIDoctorsController@findDoctor');
    Route::get('doctor/{id}', 'API\APIDoctorsController@doctor');
    Route::get('getDistricts', 'API\APIDoctorsController@getDistricts');
});
