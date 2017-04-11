@extends('layouts.app')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Edit LocationProvince #{{ $locationprovince->id }}</div>
            <div class="panel-body">
                <a href="{{ url('/backend/location-province') }}" title="Back">
                    <button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back
                    </button>
                </a>
                <br/>
                <br/>

                @if ($errors->any())
                    <ul class="alert alert-danger">
                        @foreach ($errors->all() as $error)
                            <li>{{ $error }}</li>
                        @endforeach
                    </ul>
                @endif

                {!! Form::model($locationprovince, [
                    'method' => 'PATCH',
                    'url' => ['/backend/location-province', $locationprovince->id],
                    'class' => 'form-horizontal',
                    'files' => true
                ]) !!}

                @include ('location-province.form', ['submitButtonText' => 'Update'])

                {!! Form::close() !!}

            </div>
        </div>
    </div>
@endsection
