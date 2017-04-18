@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Medical News #{{ $medicalnews->id }}</div>
            <div class="panel-body">

                <a href="{{ url('/backend/medical-news') }}" title="Back">
                    <button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back
                    </button>
                </a>
                <a href="{{ $medicalnews->url }}" target='_blank' title="Open Link">
                    <button class="btn btn-info btn-xs"><i class="fa fa-eye" aria-hidden="true"></i>
                        Open Link
                    </button>
                </a>
                <a href="{{ url('/backend/medical-news/' . $medicalnews->id . '/edit') }}" title="Edit MedicalNews">
                    <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
                    </button>
                </a>
                {!! Form::open([
                    'method'=>'DELETE',
                    'url' => ['backend/medicalnews', $medicalnews->id],
                    'style' => 'display:inline'
                ]) !!}
                {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                        'type' => 'submit',
                        'class' => 'btn btn-danger btn-xs',
                        'title' => 'Delete MedicalNews',
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
                            <td>{{ $medicalnews->id }}</td>
                        </tr>
                        <tr>
                            <th> Thumb</th>
                            <td>
                                <div>{{ $medicalnews->thumb }} </div>
                                <br/>
                                <div>
                                    {{ HTML::image($medicalnews->thumb, null, array('id' => 'thumb-img', 'style' => 'width:400px;')) }}
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th> Title</th>
                            <td> {{ $medicalnews->title }} </td>
                        </tr>
                        <tr>
                            <th> Des</th>
                            <td> {{ $medicalnews->des }} </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
@endsection
