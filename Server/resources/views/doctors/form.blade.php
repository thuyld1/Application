@section('extra-css-js')
    <script type="text/javascript" src="{{ URL::asset('js/jquery-3.2.1.min.js') }}"></script>
    <script type="text/javascript" src="{{ URL::asset('js/common.js') }}"></script>
@endsection

<div class="form-group {{ $errors->has('name') ? 'has-error' : ''}}">
    {!! Form::label('name', 'Name*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::text('name', null, ['class' => 'form-control']) !!}
        {!! $errors->first('name', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('avatar') ? 'has-error' : ''}}">
    {!! Form::label('avatar', 'Avatar', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        <div>
            {!! Form::text('avatar', null, ['class' => 'form-control', 'id' => 'thumb']) !!}
            {!! $errors->first('avatar', '<p class="help-block">:message</p>') !!}
        </div>
        <div>
            {{ HTML::image($doctor->avatar, null, array('id' => 'thumb-img', 'style' => 'max-width:100%; width: 200px;')) }}
        </div>
    </div>
</div>
<div class="form-group {{ $errors->has('phone') ? 'has-error' : ''}}">
    {!! Form::label('phone', 'Phone', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::text('phone', null, ['class' => 'form-control']) !!}
        {!! $errors->first('phone', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('des') ? 'has-error' : ''}}">
    {!! Form::label('des', 'Des*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::text('des', null, ['class' => 'form-control']) !!}
        {!! $errors->first('des', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('vote') ? 'has-error' : ''}}">
    {!! Form::label('vote', 'Vote', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::number('vote', null, ['class' => 'form-control']) !!}
        {!! $errors->first('vote', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('province') ? 'has-error' : ''}}">
    {!! Form::label('province', 'Province', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::number('province', null, ['class' => 'form-control']) !!}
        {!! $errors->first('province', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('district') ? 'has-error' : ''}}">
    {!! Form::label('district', 'District', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::number('district', null, ['class' => 'form-control']) !!}
        {!! $errors->first('district', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('specialization') ? 'has-error' : ''}}">
    {!! Form::label('specialization', 'Specialization', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::text('specialization', null, ['class' => 'form-control']) !!}
        {!! $errors->first('specialization', '<p class="help-block">:message</p>') !!}
    </div>
</div>

<div class="form-group">
    <div class="col-md-offset-4 col-md-4">
        {!! Form::submit(isset($submitButtonText) ? $submitButtonText : 'Create', ['class' => 'btn btn-primary']) !!}
    </div>
</div>
