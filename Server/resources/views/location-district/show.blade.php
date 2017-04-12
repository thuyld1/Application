@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">District #{{ $locationdistrict->id }}</div>
            <div class="panel-body">

                <a href="{{ url('/backend/setting-district') }}" title="Back">
                    <button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back
                    </button>
                </a>
                <a href="{{ url('/backend/setting-district/' . $locationdistrict->id . '/edit') }}"
                   title="Edit LocationDistrict">
                    <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
                    </button>
                </a>
                {!! Form::open([
                    'method'=>'DELETE',
                    'url' => ['backend/settingdistrict', $locationdistrict->id],
                    'style' => 'display:inline'
                ]) !!}
                {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                        'type' => 'submit',
                        'class' => 'btn btn-danger btn-xs',
                        'title' => 'Delete LocationDistrict',
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
                            <td>{{ $locationdistrict->id }}</td>
                        </tr>
                        <tr>
                            <th> Code</th>
                            <td> {{ $locationdistrict->code }} </td>
                        </tr>
                        <tr>
                            <th> District</th>
                            <td> {{ $locationdistrict->title }} </td>
                        </tr>
                        <tr>
                            <th> Province</th>
                            <td> {{ $province->title }} </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>
@endsection
