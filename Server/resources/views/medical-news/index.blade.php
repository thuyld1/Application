@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Medical News</div>
            <div class="panel-body">
                <a href="{{ url('/backend/medical-news/create') }}" class="btn btn-success btn-sm"
                   title="Add New MedicalNews">
                    <i class="fa fa-plus" aria-hidden="true"></i> Add New
                </a>

                {!! Form::open(['method' => 'GET', 'url' => '/backend/medical-news', 'class' => 'navbar-form navbar-right', 'role' => 'search'])  !!}
                <div class="input-group">
                    <input type="text" class="form-control" name="search" placeholder="Search...">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="submit">
                            <i class="fa fa-search glyphicon glyphicon-search"></i>
                        </button>
                    </span>
                </div>
                {!! Form::close() !!}

                <br/>
                <br/>
                <div class="table-responsive">
                    <table class="table table-borderless">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Thumb</th>
                            <th>Title</th>
                            <th>Des</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        @foreach($medicalnews as $item)
                            <tr>
                                <td>{{ $item->id }}</td>
                                <td>
                                    {{ HTML::image($item->thumb, null, array('id' => 'thumb-img', 'style' => 'width:200px;')) }}
                                </td>
                                <td>{{ $item->title }}</td>
                                <td>{{ $item->des }}</td>
                                <td>
                                    <a href="{{ $item->url }}" target='_blank' title="Open Link">
                                        <button class="btn btn-info btn-xs"><i class="fa fa-eye" aria-hidden="true"></i>
                                            Open Link
                                        </button>
                                    </a>
                                    <a href="{{ url('/backend/medical-news/' . $item->id) }}" title="View MedicalNews">
                                        <button class="btn btn-info btn-xs"><i class="fa fa-eye" aria-hidden="true"></i>
                                            View
                                        </button>
                                    </a>
                                    <a href="{{ url('/backend/medical-news/' . $item->id . '/edit') }}"
                                       title="Edit MedicalNews">
                                        <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o"
                                                                                  aria-hidden="true"></i> Edit
                                        </button>
                                    </a>
                                    {!! Form::open([
                                        'method'=>'DELETE',
                                        'url' => ['/backend/medical-news', $item->id],
                                        'style' => 'display:inline'
                                    ]) !!}
                                    {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                                            'type' => 'submit',
                                            'class' => 'btn btn-danger btn-xs',
                                            'title' => 'Delete MedicalNews',
                                            'onclick'=>'return confirm("Confirm delete?")'
                                    )) !!}
                                    {!! Form::close() !!}
                                </td>
                            </tr>
                        @endforeach
                        </tbody>
                    </table>
                    <div class="pagination-wrapper"> {!! $medicalnews->appends(['search' => Request::get('search')])->render() !!} </div>
                </div>
            </div>
        </div>
    </div>
@endsection
