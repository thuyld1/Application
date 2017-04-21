@extends('layouts.backend')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Province {{ $locationprovince->id }}</div>
            <div class="panel-body">

                <a href="{{ url('/backend/setting-province') }}" title="Back">
                    <button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back
                    </button>
                </a>
                <a href="{{ url('/backend/setting-province/' . $locationprovince->id . '/edit') }}"
                   title="Edit LocationProvince">
                    <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
                    </button>
                </a>
                {!! Form::open([
                    'method'=>'DELETE',
                    'url' => ['backend/settingprovince', $locationprovince->id],
                    'style' => 'display:inline'
                ]) !!}
                {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                        'type' => 'submit',
                        'class' => 'btn btn-danger btn-xs',
                        'title' => 'Delete LocationProvince',
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
                            <td>{{ $locationprovince->id }}</td>
                        </tr>
                        <tr>
                            <th> Code</th>
                            <td> {{ $locationprovince->code }} </td>
                        </tr>
                        <tr>
                            <th> Order</th>
                            <td> {{ $locationprovince->ord }} </td>
                        </tr>
                        <tr>
                            <th> Title</th>
                            <td> {{ $locationprovince->title }} </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>
@endsection
