<?php

namespace App\Http\Controllers;

use App\Http\Requests;

use App\Models\DoctorSpecialization;
use Illuminate\Http\Request;
use Session;

class DoctorSpecializationController extends Controller
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
            $doctorspecialization = DoctorSpecialization::where('code', 'LIKE', "%$keyword%")
                ->orWhere('title', 'LIKE', "%$keyword%")
                ->orWhere('ord', 'LIKE', "%$keyword%")
                ->paginate($perPage);
        } else {
            $doctorspecialization = DoctorSpecialization::paginate($perPage);
        }

        return view('doctor-specialization.index', compact('doctorspecialization'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\View\View
     */
    public function create()
    {
        return view('doctor-specialization.create');
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

        DoctorSpecialization::create($requestData);

        Session::flash('flash_message', 'DoctorSpecialization added!');

        return redirect('backend/doctor-specialization');
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
        $doctorspecialization = DoctorSpecialization::findOrFail($id);

        return view('doctor-specialization.show', compact('doctorspecialization'));
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
        $doctorspecialization = DoctorSpecialization::findOrFail($id);

        return view('doctor-specialization.edit', compact('doctorspecialization'));
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

        $doctorspecialization = DoctorSpecialization::findOrFail($id);
        $doctorspecialization->update($requestData);

        Session::flash('flash_message', 'DoctorSpecialization updated!');

        return redirect('backend/doctor-specialization');
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
        DoctorSpecialization::destroy($id);

        Session::flash('flash_message', 'DoctorSpecialization deleted!');

        return redirect('backend/doctor-specialization');
    }
}
