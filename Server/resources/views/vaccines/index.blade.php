@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Vaccines</div>
            <div class="panel-body">
                <a href="{{ url('/backend/vaccines/create') }}" class="btn btn-success btn-sm" title="Add New Vaccine">
                    <i class="fa fa-plus" aria-hidden="true"></i> Add New
                </a>

                {!! Form::open(['method' => 'GET', 'url' => '/backend/vaccines', 'class' => 'navbar-form navbar-right', 'role' => 'search'])  !!}
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
                            <th>Code</th>
                            <th>Name</th>
                            <th>Period</th>
                            <th>Short Description</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        @foreach($vaccines as $item)
                            <tr>
                                <td>{{ $item->id }}</td>
                                <td>{{ $item->v_code }}</td>
                                <td>{{ $item->v_name }}</td>
                                <td>{{ $item->v_period }}</td>
                                <td>{{ $item->v_short_des }}</td>
                                <td>{{ $item->status }}</td>
                                <td>
                                    <a href="{{ $item->v_url }}" target='_blank' title="Open Vaccine Link" >
                                        <button class="btn btn-info btn-xs"><i class="fa fa-eye" aria-hidden="true"></i>
                                            Link
                                        </button>
                                    </a>
                                    <a href="{{ url('/backend/vaccines/' . $item->id) }}" title="View Vaccine">
                                        <button class="btn btn-info btn-xs"><i class="fa fa-eye" aria-hidden="true"></i>
                                            View
                                        </button>
                                    </a>
                                    <a href="{{ url('/backend/vaccines/' . $item->id . '/edit') }}"
                                       title="Edit Vaccine">
                                        <button class="btn btn-primary btn-xs"><i class="fa fa-pencil-square-o"
                                                                                  aria-hidden="true"></i> Edit
                                        </button>
                                    </a>
                                    {!! Form::open([
                                        'method'=>'DELETE',
                                        'url' => ['/backend/vaccines', $item->id],
                                        'style' => 'display:inline'
                                    ]) !!}
                                    {!! Form::button('<i class="fa fa-trash-o" aria-hidden="true"></i> Delete', array(
                                            'type' => 'submit',
                                            'class' => 'btn btn-danger btn-xs',
                                            'title' => 'Delete Vaccine',
                                            'onclick'=>'return confirm("Confirm delete?")'
                                    )) !!}
                                    {!! Form::close() !!}
                                </td>
                            </tr>
                        @endforeach
                        </tbody>
                    </table>
                    <div class="pagination-wrapper"> {!! $vaccines->appends(['search' => Request::get('search')])->render() !!} </div>
                </div>
            </div>
        </div>
    </div>
@endsection
