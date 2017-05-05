<?php

namespace App\Http\Controllers\API;

use App\Http\Requests;

use App\Models\Doctor;
use App\Models\LocationDistrict;
use App\Models\MobileUser;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Config;
use Illuminate\Database\QueryException;
use Illuminate\Support\Facades\Log;
use PDOException;
use Session;

class APIClientController extends APIController
{

    /**
     * Call when starting application
     * @param Request $request
     */
    public function register(Request $request)
    {
        // Set default return value
        $status = 1;

        // Get device information
        $deviceID = trim($request->get('device_id'));

        // Validate input parameters
        if (empty($deviceID) || $deviceID == 'null') {
            // Return error code
            $status = -1;
        } else {
            // Create new one
            $requestData = $request->all();

            try {
                MobileUser::create($requestData);
            } catch (QueryException $e) {
                $status = -1;
                Log::error($e->getMessage(), ['DATA ====>' => $requestData]);
            } catch (PDOException $e) {
                $status = -1;
                Log::error($e->getMessage(), ['DATA ====>' => $requestData]);
            }
        }

        return compact('status', 'token');

    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\View\View
     */
    public function findDoctor(Request $request)
    {
        // Build query to find in DB
        $perPage = Config::get('constant.API_RECORD_PER_PAGE');
        $builder = Doctor::select('name', 'avatar', 'phone', 'des', 'vote', 'province', 'district', 'specialization')
            ->where('status', '=', 0);

        // Check filter condition to query
        $provinces = $request->get('provinces');
        $districts = $request->get('districts');
        $specializations = $request->get('specializations');

        if (!empty($districts)) {
            $builder->whereIn('district', explode(",", $districts));
        } else if (!empty($provinces)) {
            $builder->whereIn('province', explode(",", $provinces));
        }

        if (!empty($specializations)) {
            $builder->whereIn('specialization', explode(",", $specializations));
        }

        // Return result
        return $builder->paginate($perPage);
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
     * Display the specified resource.
     *
     * @param  int id
     *
     * @return \Illuminate\View\View
     */
    public function doctor($id)
    {
        $doctor = Doctor::find($id);
        if (empty($doctor)) {
            abort(404);
        } else {
            return view('doctors.details', compact('doctor'));
        }
    }

}
