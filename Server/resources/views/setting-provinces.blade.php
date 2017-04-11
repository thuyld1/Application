@extends('layouts.functions')

@section('content')
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">Setting Districts</div>

            <div class="panel-body">
                @foreach ($data as $province)
                    <div class="id"></div>
                    <div class="code"></div>
                    <div class="order"></div>
                    <div class="title"></div>
                    <div class="simple"></div>
                    {{ $province->title }}
                @endforeach

                <!-- Pagination view -->
                {{ $data->links() }}
            </div>


        </div>
    </div>
@endsection
