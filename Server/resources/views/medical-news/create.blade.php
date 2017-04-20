@extends('layouts.backend')

@section('extra-css-js')
    <script type="text/javascript" src="{{ URL::asset('js/jquery-3.2.1.min.js') }}"></script>
    <script type="text/javascript" src="{{ URL::asset('js/medical-news.js') }}"></script>
@endsection

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Create New Medical News</div>
            <div class="panel-body">
                <a href="{{ url('/backend/medical-news') }}" title="Back">
                    <button class="btn btn-warning btn-xs"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back
                    </button>
                </a>
                <br/>
                <br/>

                <div class="form-group form-horizontal">
                    {!! Form::label('link', 'Detect Link', ['class' => 'col-md-2 control-label']) !!}
                    <div class="col-md-8 input-group">
                        {!! Form::text('link', null, ['class' => 'form-control', 'id' => 'detect_link']) !!}
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="submit" id="detect_btn">
                                <i class="fa fa-search 	glyphicon glyphicon-refresh"></i>
                            </button>
                        </span>
                    </div>
                </div>
                <br/>

                @if ($errors->any())
                    <ul class="alert alert-danger">
                        @foreach ($errors->all() as $error)
                            <li>{{ $error }}</li>
                        @endforeach
                    </ul>
                @endif

                <div class="col-md-4">
                    {{ HTML::image(Config::get('constant.DEFAULT_IMAGE'), null, array('id' => 'thumb-img', 'style' => 'max-width:100%; max-height:100%;')) }}
                </div>
                <div class="col-md-8">
                    {!! Form::open(['url' => '/backend/medical-news', 'class' => 'form-horizontal', 'files' => true]) !!}

                    @include ('medical-news.form')

                    {!! Form::close() !!}
                </div>

            </div>
        </div>
    </div>
@endsection
