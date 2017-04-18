<?php

namespace App\Http\Controllers;

use App\Http\Requests;

use App\Models\MedicalNews;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Config;
use Session;

class MedicalNewsController extends Controller
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
            $medicalnews = MedicalNews::where('thumb', 'LIKE', "%$keyword%")
				->orWhere('title', 'LIKE', "%$keyword%")
				->orWhere('des', 'LIKE', "%$keyword%")
				->orWhere('url', 'LIKE', "%$keyword%")
				
                ->paginate($perPage);
        } else {
            $medicalnews = MedicalNews::paginate($perPage);
        }

        return view('medical-news.index', compact('medicalnews'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\View\View
     */
    public function create()
    {
        return view('medical-news.create');
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
        
        MedicalNews::create($requestData);

        Session::flash('flash_message', 'MedicalNews added!');

        return redirect('backend/medical-news');
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
        $medicalnews = MedicalNews::findOrFail($id);

        return view('medical-news.show', compact('medicalnews'));
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
        $medicalnews = MedicalNews::findOrFail($id);

        return view('medical-news.edit', compact('medicalnews'));
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
        
        $medicalnews = MedicalNews::findOrFail($id);
        $medicalnews->update($requestData);

        Session::flash('flash_message', 'MedicalNews updated!');

        return redirect('backend/medical-news');
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
        MedicalNews::destroy($id);

        Session::flash('flash_message', 'MedicalNews deleted!');

        return redirect('backend/medical-news');
    }
}
