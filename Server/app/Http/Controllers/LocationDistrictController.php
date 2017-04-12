<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use App\Http\Controllers\Controller;

use App\Models\LocationDistrict;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Session;

class LocationDistrictController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\View\View
     */
    public function index(Request $request)
    {
        $keyword = $request->get('search');
        $perPage = 25;

        if (!empty($keyword)) {
            $locationdistrict = LocationDistrict::join('location_provinces', 'location_provinces.code', '=', 'location_districts.p_code')
                ->select('location_provinces.title as province', 'location_districts.*')
                ->where('location_districts.code', 'LIKE', "%$keyword%")
                ->orWhere('location_districts.title', 'LIKE', "%$keyword%")
                ->orWhere('location_provinces.title', 'LIKE', "%$keyword%")
                ->paginate($perPage);
        } else {
            $locationdistrict = LocationDistrict::join('location_provinces', 'location_provinces.code', '=', 'location_districts.p_code')
                ->select('location_provinces.title as province', 'location_districts.*')
                ->paginate($perPage);
        }

        return view('location-district.index', compact('locationdistrict'));
    }

    /**
     * Get all province data for selection box
     */
    private function getProvince()
    {
        $provinces = array();
        $listProvinces = DB::table('location_provinces')->select('code', 'title')->get();
        foreach ($listProvinces as $province) {
            $provinces[$province->code] = $province->title;
        }
        return $provinces;
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\View\View
     */
    public function create()
    {
        $provinces = $this->getProvince();
        return view('location-district.create', compact('provinces'));
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

        LocationDistrict::create($requestData);

        Session::flash('flash_message', 'LocationDistrict added!');

        return redirect('backend/setting-district');
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
        $locationdistrict = LocationDistrict::findOrFail($id);
        $province = LocationDistrict::find($id)->province;

        return view('location-district.show', compact('locationdistrict', 'province'));
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
        $locationdistrict = LocationDistrict::findOrFail($id);
        $provinces = $this->getProvince();

        return view('location-district.edit', compact('locationdistrict', 'provinces'));
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

        $locationdistrict = LocationDistrict::findOrFail($id);
        $locationdistrict->update($requestData);

        Session::flash('flash_message', 'LocationDistrict updated!');

        return redirect('backend/setting-district');
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
        LocationDistrict::destroy($id);

        Session::flash('flash_message', 'LocationDistrict deleted!');

        return redirect('backend/setting-district');
    }
}
