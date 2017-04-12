<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use App\Http\Controllers\Controller;

use App\Models\Vaccine;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Session;

class VaccinesController extends Controller
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
            $vaccines = Vaccine::where('v_code', 'LIKE', "%$keyword%")
                ->orWhere('v_name', 'LIKE', "%$keyword%")
                ->orWhere('v_period', 'LIKE', "%$keyword%")
                ->orWhere('v_period_f', 'LIKE', "%$keyword%")
                ->orWhere('v_period_t', 'LIKE', "%$keyword%")
                ->orWhere('v_short_des', 'LIKE', "%$keyword%")
                ->orWhere('v_url', 'LIKE', "%$keyword%")
                ->orWhere('status', 'LIKE', "%$keyword%")
                ->paginate($perPage);
        } else {
            $vaccines = Vaccine::paginate($perPage);
        }

        return view('vaccines.index', compact('vaccines'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\View\View
     */
    public function create()
    {
        // Get max of code
        $maxCode = DB::table('vaccines')
            ->max('v_code');
        $maxCode++;
        return view('vaccines.create', compact('maxCode'));
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

        Vaccine::create($requestData);

        Session::flash('flash_message', 'Vaccine added!');

        return redirect('backend/vaccines');
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
        $vaccine = Vaccine::findOrFail($id);

        return view('vaccines.show', compact('vaccine'));
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
        $vaccine = Vaccine::findOrFail($id);

        return view('vaccines.edit', compact('vaccine'));
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

        $vaccine = Vaccine::findOrFail($id);
        $vaccine->update($requestData);

        Session::flash('flash_message', 'Vaccine updated!');

        return redirect('backend/vaccines');
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
        Vaccine::destroy($id);

        Session::flash('flash_message', 'Vaccine deleted!');

        return redirect('backend/vaccines');
    }
}
