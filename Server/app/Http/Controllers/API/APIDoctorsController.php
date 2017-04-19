<?php

namespace App\Http\Controllers\API;

use App\Http\Requests;

use App\Models\MedicalNews;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\DB;
use Session;

class APIDoctorsController extends APIController
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\View\View
     */
    public function findDoctor(Request $request)
    {
        $perPage = Config::get('constant.API_RECORD_PER_PAGE');
        $medicalnews = MedicalNews::select('thumb', 'title', 'des', 'url')
            ->paginate($perPage);
        return $medicalnews;
    }


    /**
     * Get list districts by province
     *
     * @param \Illuminate\Http\Request $request
     *
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector
     */
    public function getDistricts(Request $request)
    {
        $province = $request->get('province');
        $list = $this->getDistrict($province);
        return $list;
    }

    /**
     * Get all province data for selection box
     * @param int $province
     * @return array
     */
    private function getDistrict($province = null)
    {
        $districts = array();
        if (!empty($province)) {
            $listDistricts = DB::table('location_districts')
                ->select('code', 'title')
                ->where('p_code', '=', $province)
                ->get();
            foreach ($listDistricts as $item) {
                $districts[$item->code] = $item->title;
            }
        }
        return $districts;
    }

}
