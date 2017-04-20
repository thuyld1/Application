@extends('layouts.backend')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Vaccine #{{ $vaccine->id }}</div>
            <div class="panel-body">

                <a href="{{ url('/backend/vaccines') }}" title="Back">
                    <button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back
                    </button>
                </a>
                <a href="{{ url('/backend/vaccines/' . $vaccine->id . '/edit') }}" title="Edit Vaccine">
                    <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
                    </button>
                </a>
                {!! Form::open([
                    'method'=>'DELETE',
                    'url' => ['backend/vaccines', $vaccine->id],
                    'style' => 'display:inline'
                ]) !!}
                {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                        'type' => 'submit',
                        'class' => 'btn btn-danger btn-xs',
                        'title' => 'Delete Vaccine',
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
                            <td>{{ $vaccine->id }}</td>
                        </tr>
                        <tr>
                            <th> Code</th>
                            <td> {{ $vaccine->v_code }} </td>
                        </tr>
                        <tr>
                            <th> Name</th>
                            <td> {{ $vaccine->v_name }} </td>
                        </tr>
                        <tr>
                            <th> Period</th>
                            <td> {{ $vaccine->v_period }} </td>
                        </tr>
                        <tr>
                            <th> Period From</th>
                            <td> {{ $vaccine->v_period_f }} </td>
                        </tr>
                        <tr>
                            <th> Period To</th>
                            <td> {{ $vaccine->v_period_t }} </td>
                        </tr>
                        <tr>
                            <th> Short Description</th>
                            <td> {{ $vaccine->v_short_des }} </td>
                        </tr>
                        <tr>
                            <th> Status</th>
                            <td> {{ $vaccine->status }} </td>
                        </tr>
                        <tr>
                            <th> Link</th>
                            <td> {{ HTML::link($vaccine->v_url, null, array('target' => '_blank'))}} </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
@endsection
