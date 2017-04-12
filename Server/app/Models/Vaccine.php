<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Vaccine extends Model
{
    /**
     * The database table used by the model.
     *
     * @var string
     */
    protected $table = 'vaccines';

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
    protected $fillable = ['v_code', 'v_name', 'v_period', 'v_period_f', 'v_period_t', 'v_short_des', 'v_url', 'status'];


}
