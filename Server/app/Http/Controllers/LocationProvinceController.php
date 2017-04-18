<?php

namespace App\Http\Controllers;

use App\Http\Requests;

use App\Models\LocationProvince;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Config;
use Session;

class LocationProvinceController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\View\View
     */
    public function index(Request $request)
    {
        $keyword = $request->get('search');
        $perPage = Config::get('constant.BACKEND_RECORD_PER_PAGE');

        if (!empty($keyword)) {
            $locationprovince = LocationProvince::where('code', 'LIKE', "%$keyword%")
                ->orWhere('title', 'LIKE', "%$keyword%")
                ->orWhere('simple', 'LIKE', "%$keyword%")
                ->paginate($perPage);
        } else {
            $locationprovince = LocationProvince::paginate($perPage);
        }

        return view('location-province.index', compact('locationprovince'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\View\View
     */
    public function create()
    {
        return view('location-province.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param \Illuminate\Http\Request $request
     *
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector
     */
    public function store(Request $request)
    {

        $requestData = $request->all();

        LocationProvince::create($requestData);

        Session::flash('flash_message', 'LocationProvince added!');

        return redirect('backend/setting-province');
    }

    /**
     * Display the specified resource.
     *
     * @param  int $id
     *
     * @return \Illuminate\View\View
     */
    public function show($id)
    {
        $locationprovince = LocationProvince::findOrFail($id);

        return view('location-province.show', compact('locationprovince'));
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int $id
     *
     * @return \Illuminate\View\View
     */
    public function edit($id)
    {
        $locationprovince = LocationProvince::findOrFail($id);

        return view('location-province.edit', compact('locationprovince'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  int $id
     * @param \Illuminate\Http\Request $request
     *
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector
     */
    public function update($id, Request $request)
    {

        $requestData = $request->all();

        $locationprovince = LocationProvince::findOrFail($id);
        $locationprovince->update($requestData);

        Session::flash('flash_message', 'LocationProvince updated!');

        return redirect('backend/setting-province');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int $id
     *
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector
     */
    public function destroy($id)
    {
        LocationProvince::destroy($id);

        Session::flash('flash_message', 'LocationProvince deleted!');

        return redirect('backend/setting-province');
    }
}
