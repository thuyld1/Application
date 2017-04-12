@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Create New Vaccine</div>
            <div class="panel-body">
                <a href="{{ url('/backend/vaccines') }}" title="Back"><button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back</button></a>
                <br />
                <br />

                @if ($errors->any())
                    <ul class="alert alert-danger">
                        @foreach ($errors->all() as $error)
                            <li>{{ $error }}</li>
                        @endforeach
                    </ul>
                @endif

                {!! Form::open(['url' => '/backend/vaccines', 'class' => 'form-horizontal', 'files' => true]) !!}

                @include ('vaccines.form')

                {!! Form::close() !!}

            </div>
        </div>
    </div>
@endsection
