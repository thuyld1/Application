@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Doctor #{{ $doctor->id }}</div>
            <div class="panel-body">

                <a href="{{ url('/backend/doctors') }}" title="Back">
                    <button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back
                    </button>
                </a>
                <a href="{{ url('/backend/doctors/' . $doctor->id . '/edit') }}" title="Edit Doctor">
                    <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
                    </button>
                </a>
                {!! Form::open([
                    'method'=>'DELETE',
                    'url' => ['backend/doctors', $doctor->id],
                    'style' => 'display:inline'
                ]) !!}
                {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                        'type' => 'submit',
                        'class' => 'btn btn-danger btn-xs',
                        'title' => 'Delete Doctor',
                        'onclick'=>'return confirm("Confirm delete?")'
                ))!!}
                {!! Form::close() !!}
                <br/>
                <br/>

                <div class="table-responsive">
                    <table class="table table-borderless">
                        <tbody>
                        <tr>
                            <th>ID</th>
                            <td>{{ $doctor->id }}</td>
                        </tr>
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
                            <td> {{ $doctor->province_title }} </td>
                        </tr>
                        <tr>
                            <th> District</th>
                            <td> {{ $doctor->district_title }} </td>
                        </tr>
                        <tr>
                            <th> Specialization</th>
                            <td> {{ $doctor->specialization_title }} </td>
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
