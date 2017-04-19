<?php

namespace App\Http\Controllers;

use App\Http\Requests;

use App\Models\Doctor;
use App\Models\DoctorSpecialization;
use App\Models\LocationDistrict;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\DB;
use Session;

class DoctorsController extends Controller
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
            $doctors = Doctor::where('name', 'LIKE', "%$keyword%")
                ->orWhere('avatar', 'LIKE', "%$keyword%")
                ->orWhere('phone', 'LIKE', "%$keyword%")
                ->orWhere('des', 'LIKE', "%$keyword%")
                ->orWhere('vote', 'LIKE', "%$keyword%")
                ->orWhere('province', 'LIKE', "%$keyword%")
                ->orWhere('district', 'LIKE', "%$keyword%")
                ->orWhere('specialization', 'LIKE', "%$keyword%")
                ->orWhere('status', 'LIKE', "%$keyword%")
                ->paginate($perPage);
        } else {
            $doctors = Doctor::paginate($perPage);
        }

        return view('doctors.index', compact('doctors'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\View\View
     */
    public function create()
    {
        $provinces = $this->getProvince();
        reset($provinces);
        $first_key = key($provinces);
        $districts = $this->getDistrict($first_key);
        $specializations = $this->getSpecialization();
        return view('doctors.create', compact('provinces', 'districts', 'specializations'));
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

        Doctor::create($requestData);

        Session::flash('flash_message', 'Doctor added!');

        return redirect('backend/doctors');
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
        $doctor = Doctor::findOrFail($id);

        return view('doctors.show', compact('doctor'));
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
        $doctor = Doctor::findOrFail($id);

        $provinces = $this->getProvince();
        $districts = $this->getDistrict($doctor->province);
        $specializations = $this->getSpecialization();

        return view('doctors.edit', compact('doctor', 'provinces', 'districts', 'specializations'));
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

        $doctor = Doctor::findOrFail($id);
        $doctor->update($requestData);

        Session::flash('flash_message', 'Doctor updated!');

        return redirect('backend/doctors');
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
        Doctor::destroy($id);

        Session::flash('flash_message', 'Doctor deleted!');

        return redirect('backend/doctors');
    }


    /**
     * Get all province data for selection box
     * @return array
     */
    private function getProvince()
    {
        $provinces = DB::table('location_provinces')
            ->select('code', 'title')
            ->orderBy('ord')
            ->pluck('title', 'code')
            ->toArray();
        return $provinces;
    }

    /**
     * Get all province data for selection box
     * @param int $province
     * @return array
     */
    private function getDistrict($province = null)
    {
        if (empty($province)) {
            $districts = array();
        } else {
            $districts = LocationDistrict::select('code', 'title')
                ->where('p_code', '=', $province)
                ->pluck('title', 'code')
                ->toArray();
        }
        return $districts;
    }

    /**
     * Get all specialization data for selection box
     */
    private function getSpecialization()
    {
        $listProvinces = DoctorSpecialization::select('code', 'title')
            ->orderBy('ord')
            ->pluck('title', 'code')
            ->toArray();
        return $listProvinces;
    }
}
