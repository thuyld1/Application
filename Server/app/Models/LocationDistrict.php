<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class LocationDistrict extends Model
{
    /**
     * The database table used by the model.
     *
     * @var string
     */
    protected $table = 'location_districts';

    /**
    * The database primary key value.
    *
    * @var string
    */
    protected $primaryKey = 'id';

    /**
     * Attributes that should be mass-assignable.
     *
     * @var array
     */
    protected $fillable = ['code', 'p_code', 'title'];

    /**
     * Get Province of district
     * @return \Illuminate\Database\Eloquent\Relations\HasOne
     */
    public function province() {
        return $this->hasOne('App\Models\LocationProvince', 'code', 'p_code');
    }

    
}
