@extends('layouts.api')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-body">

                <div class="table-responsive">
                    <table class="table table-borderless">
                        <tbody>
                        <tr>
                            <th> Name</th>
                            <td> {{ $doctor->name }} </td>
                        </tr>
                        <tr>
                            <th> Avatar</th>
                            <td>
                                <div>{{ $doctor->avatar }} </div>
                                <br/>
                                <div>
                                    {{ HTML::image($doctor->avatar, null, array('id' => 'thumb-img', 'style' => 'width:400px;')) }}
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th> Phone</th>
                            <td> {{ $doctor->phone }} </td>
                        </tr>
                        <tr>
                            <th> Description</th>
                            <td> {{ $doctor->des }} </td>
                        </tr>
                        <tr>
                            <th> Vote</th>
                            <td> {{ $doctor->vote }} </td>
                        </tr>
                        <tr>
                            <th> Province</th>
                            <td> {{ $doctor->province }} </td>
                        </tr>
                        <tr>
                            <th> District</th>
                            <td> {{ $doctor->district }} </td>
                        </tr>
                        <tr>
                            <th> Specialization</th>
                            <td> {{ $doctor->specialization }} </td>
                        </tr>
                        <tr>
                            <th> Status</th>
                            <td> {{ $doctor->status }} </td>
                        </tr>
                        <tr>
                            <th> Created</th>
                            <td> {{ $doctor->created_at }} </td>
                        </tr>
                        <tr>
                            <th> Updated</th>
                            <td> {{ $doctor->updated_at }} </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
@endsection
