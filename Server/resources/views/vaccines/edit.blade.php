@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Edit Vaccine #{{ $vaccine->id }}</div>
            <div class="panel-body">
                <a href="{{ url('/backend/vaccines') }}" title="Back">
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

                {!! Form::model($vaccine, [
                    'method' => 'PATCH',
                    'url' => ['/backend/vaccines', $vaccine->id],
                    'class' => 'form-horizontal',
                    'files' => true
                ]) !!}

                @include ('vaccines.form', ['submitButtonText' => 'Update'])

                {!! Form::close() !!}

            </div>
        </div>
    </div>
@endsection
