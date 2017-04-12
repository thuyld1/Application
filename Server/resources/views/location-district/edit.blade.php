@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Edit District #{{ $locationdistrict->id }}</div>
            <div class="panel-body">
                <a href="{{ url('/backend/setting-district') }}" title="Back">
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

                {!! Form::model($locationdistrict, [
                    'method' => 'PATCH',
                    'url' => ['/backend/setting-district', $locationdistrict->id],
                    'class' => 'form-horizontal',
                    'files' => true
                ]) !!}

                @include ('location-district.form', ['submitButtonText' => 'Update'])

                {!! Form::close() !!}

            </div>
        </div>
    </div>
@endsection
