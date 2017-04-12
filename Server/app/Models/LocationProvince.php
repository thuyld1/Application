<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class LocationProvince extends Model
{
    /**
     * The database table used by the model.
     *
     * @var string
     */
    protected $table = 'location_provinces';

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
    protected $fillable = ['code', 'ord', 'title', 'simple'];

    
}
