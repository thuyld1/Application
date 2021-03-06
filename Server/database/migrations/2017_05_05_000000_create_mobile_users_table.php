<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateMobileUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('mobile_users', function (Blueprint $table) {
            $table->increments('id');
            $table->string('facebook_id', 100)->nullable();
            $table->string('name', 100)->nullable();
            $table->string('device_id', 50)->unique();
            $table->string('os_name', 30);
            $table->string('os_version', 30);
            $table->string('app_version', 10);
            $table->string('push_token', 256)->nullable();
            $table->string('api_token', 60)->nullable();
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
        Schema::dropIfExists('mobile_users');
    }
}
