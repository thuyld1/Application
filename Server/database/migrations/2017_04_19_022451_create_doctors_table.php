<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;

class CreateDoctorsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('doctors', function(Blueprint $table) {
            $table->increments('id');
            $table->string('name', 100);
            $table->string('avatar', 255)->nullable();
            $table->string('phone', 50)->nullable();
            $table->string('des', 255);
            $table->float('vote')->nullable()->default(0);
            $table->integer('province', false, true)->nullable();
            $table->integer('district', false, true)->nullable();
            $table->string('specialization')->nullable();
            $table->unsignedTinyInteger('status')->default(0);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('doctors');
    }
}
