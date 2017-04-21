@extends('layouts.backend')

@section('extra-css-js')
    <script type="text/javascript" src="{{ URL::asset('js/jquery-3.2.1.min.js') }}"></script>
    <script type="text/javascript" src="{{ URL::asset('js/medical-news.js') }}"></script>
@endsection

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Edit Medical News #{{ $medicalnews->id }}</div>
            <div class="panel-body">
                <a href="{{ url('/backend/medical-news') }}" title="Back">
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

                {!! Form::model($medicalnews, [
                    'method' => 'PATCH',
                    'url' => ['/backend/medical-news', $medicalnews->id],
                    'class' => 'form-horizontal',
                    'files' => true
                ]) !!}

                <div class="form-group ">
                    <div class="col-md-4"></div>
                    <div class="col-md-8">
                        {{ HTML::image($medicalnews->thumb, null, array('id' => 'thumb-img', 'style' => 'width:400px;')) }}
                    </div>
                </div>

                @include ('medical-news.form', ['submitButtonText' => 'Update'])

                {!! Form::close() !!}

            </div>
        </div>
    </div>
@endsection
