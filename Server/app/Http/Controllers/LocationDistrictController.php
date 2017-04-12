<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use App\Http\Controllers\Controller;

use App\Models\LocationDistrict;
use Illuminate\Http\Request;
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
            $locationdistrict = LocationDistrict::where('code', 'LIKE', "%$keyword%")
				->orWhere('p_code', 'LIKE', "%$keyword%")
				->orWhere('title', 'LIKE', "%$keyword%")
				
                ->paginate($perPage);
        } else {
            $locationdistrict = LocationDistrict::paginate($perPage);
        }

        return view('location-district.index', compact('locationdistrict'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\View\View
     */
    public function create()
    {
        return view('location-district.create');
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
     * @param  int  $id
     *
     * @return \Illuminate\View\View
     */
    public function show($id)
    {
        $locationdistrict = LocationDistrict::findOrFail($id);

        return view('location-district.show', compact('locationdistrict'));
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     *
     * @return \Illuminate\View\View
     */
    public function edit($id)
    {
        $locationdistrict = LocationDistrict::findOrFail($id);

        return view('location-district.edit', compact('locationdistrict'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  int  $id
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
     * @param  int  $id
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
