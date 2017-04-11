<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    // Table name
    protected $table = 'filter_location_province';
    // public $timestamps = false;

    // Field can edit information
    protected $fillable = [
        'first_name',
        'surname',
        'email',
        'password',
        'active',
        'salt',
        'group'
    ];

    // Field can NOT edit information
    protected $guarded = [
        'id'
    ];

    // Field can NOT show information
    // protected $hidden = ['password', 'remember_token'];
}
