@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Doctor Specialization {{ $doctorspecialization->id }}</div>
            <div class="panel-body">

                <a href="{{ url('/backend/doctor-specialization') }}" title="Back">
                    <button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back
                    </button>
                </a>
                <a href="{{ url('/backend/doctor-specialization/' . $doctorspecialization->id . '/edit') }}"
                   title="Edit DoctorSpecialization">
                    <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
                    </button>
                </a>
                {!! Form::open([
                    'method'=>'DELETE',
                    'url' => ['backend/doctorspecialization', $doctorspecialization->id],
                    'style' => 'display:inline'
                ]) !!}
                {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                        'type' => 'submit',
                        'class' => 'btn btn-danger btn-xs',
                        'title' => 'Delete DoctorSpecialization',
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
                            <td>{{ $doctorspecialization->id }}</td>
                        </tr>
                        <tr>
                            <th> Code</th>
                            <td> {{ $doctorspecialization->code }} </td>
                        </tr>
                        <tr>
                            <th> Title</th>
                            <td> {{ $doctorspecialization->title }} </td>
                        </tr>
                        <tr>
                            <th> Order</th>
                            <td> {{ $doctorspecialization->ord }} </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
@endsection
