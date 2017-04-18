<?php

namespace App\Http\Controllers\API;

use App\Http\Requests;

use App\Models\MedicalNews;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Config;
use Session;

class APIMedicalNewsController extends APIController
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\View\View
     */
    public function listNews(Request $request)
    {
        $perPage = Config::get('constant.API_RECORD_PER_PAGE');
        $medicalnews = MedicalNews::select('thumb', 'title', 'des', 'url')
            ->paginate($perPage);
        return $medicalnews;
    }


}
