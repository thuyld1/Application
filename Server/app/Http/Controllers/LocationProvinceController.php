<?php

namespace App\Http\Controllers;


use Illuminate\Support\Facades\DB;

class LocationProvinceController extends Controller
{
    /**
     * Show the list provinces
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $data = DB::table('filter_location_province')->paginate(15);

        return view('setting-provinces', ['data' => $data]);
    }
}
