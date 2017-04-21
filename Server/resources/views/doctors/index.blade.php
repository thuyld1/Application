@extends('layouts.backend')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Doctors</div>
            <div class="panel-body">
                <a href="{{ url('/backend/doctors/create') }}" class="btn btn-success btn-sm" title="Add New Doctor">
                    <i class="fa fa-plus" aria-hidden="true"></i> Add New
                </a>

                {!! Form::open(['method' => 'GET', 'url' => '/backend/doctors', 'class' => 'navbar-form navbar-right', 'role' => 'search'])  !!}
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
                            <th>Avatar</th>
                            <th>Name</th>
                            <th>Phone</th>
                            <th>Province</th>
                            <th>Specialization</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        @foreach($doctors as $item)
                            <tr>
                                <td>{{ $item->id }}</td>
                                <td>
                                    {{ HTML::image($item->avatar, null, array('id' => 'thumb-img', 'style' => 'width:100px;')) }}
                                </td>
                                <td>{{ $item->name }}</td>
                                <td>{{ $item->phone }}</td>
                                <td>{{ $item->province_title }}</td>
                                <td>{{ $item->specialization_title }}</td>
                                <td>
                                    <a href="{{ url('/backend/doctors/' . $item->id) }}" title="View Doctor">
                                        <button class="btn btn-info btn-xs"><i class="fa fa-eye" aria-hidden="true"></i>
                                            View
                                        </button>
                                    </a>
                                    <a href="{{ url('/backend/doctors/' . $item->id . '/edit') }}" title="Edit Doctor">
                                        <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o"
                                                                                  aria-hidden="true"></i> Edit
                                        </button>
                                    </a>
                                    {!! Form::open([
                                        'method'=>'DELETE',
                                        'url' => ['/backend/doctors', $item->id],
                                        'style' => 'display:inline'
                                    ]) !!}
                                    {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                                            'type' => 'submit',
                                            'class' => 'btn btn-danger btn-xs',
                                            'title' => 'Delete Doctor',
                                            'onclick'=>'return confirm("Confirm delete?")'
                                    )) !!}
                                    {!! Form::close() !!}
                                </td>
                            </tr>
                        @endforeach
                        </tbody>
                    </table>
                    <div class="pagination-wrapper"> {!! $doctors->appends(['search' => Request::get('search')])->render() !!} </div>
                </div>
            </div>
        </div>
    </div>
@endsection
