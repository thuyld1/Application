<div class="form-group {{ $errors->has('v_code') ? 'has-error' : ''}}">
    {!! Form::label('v_code', 'Code*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::number('v_code', null, ['class' => 'form-control']) !!}
        {!! $errors->first('v_code', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('v_name') ? 'has-error' : ''}}">
    {!! Form::label('v_name', 'Name*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::text('v_name', null, ['class' => 'form-control']) !!}
        {!! $errors->first('v_name', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('v_period') ? 'has-error' : ''}}">
    {!! Form::label('v_period', 'Period Description*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::text('v_period', null, ['class' => 'form-control']) !!}
        {!! $errors->first('v_period', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('v_period_f') ? 'has-error' : ''}}">
    {!! Form::label('v_period_f', 'Period From (day)*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::number('v_period_f', null, ['class' => 'form-control']) !!}
        {!! $errors->first('v_period_f', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('v_period_t') ? 'has-error' : ''}}">
    {!! Form::label('v_period_t', 'Period To (day)*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::number('v_period_t', null, ['class' => 'form-control']) !!}
        {!! $errors->first('v_period_t', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('v_short_des') ? 'has-error' : ''}}">
    {!! Form::label('v_short_des', 'Short Description*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::text('v_short_des', null, ['class' => 'form-control']) !!}
        {!! $errors->first('v_short_des', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('v_url') ? 'has-error' : ''}}">
    {!! Form::label('v_url', 'URL*', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::text('v_url', null, ['class' => 'form-control']) !!}
        {!! $errors->first('v_url', '<p class="help-block">:message</p>') !!}
    </div>
</div>
{{--<div class="form-group {{ $errors->has('status') ? 'has-error' : ''}}">
    {!! Form::label('status', 'Status', ['class' => 'col-md-4 control-label']) !!}
    <div class="col-md-6">
        {!! Form::number('status', null, ['class' => 'form-control']) !!}
        {!! $errors->first('status', '<p class="help-block">:message</p>') !!}
    </div>
</div>--}}

<div class="form-group">
    <div class="col-md-offset-4 col-md-4">
        {!! Form::submit(isset($submitButtonText) ? $submitButtonText : 'Create', ['class' => 'btn btn-primary']) !!}
    </div>
</div>
