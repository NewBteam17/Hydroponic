@extends('admin.index')

@section('content')
</br>
</br>
    <div class="container">
        <div class="row">
            <div class="col-md-10">
                <div class="card">
                    <div class="card-header">Create New forecasting</div>
                    <div class="card-body">
                        <a href="{{ url('/forecasting') }}" title="Back"><button class="btn btn-warning btn-sm"><i class="fa fa-arrow-left" aria-hidden="true"></i> Back</button></a>
                        <br />
                        <br />

                        @if ($errors->any())
                            <ul class="alert alert-danger">
                                @foreach ($errors->all() as $error)
                                    <li>{{ $error }}</li>
                                @endforeach
                            </ul>
                        @endif

                        <form method="POST" action="{{ url('/forecasting') }}" accept-charset="UTF-8" class="form-horizontal" enctype="multipart/form-data">
                            {{ csrf_field() }}

                           <div class="form-group {{ $errors->has('dosis') ? 'has-error' : ''}}">
    <label for="dosis" class="col-md-4 control-label">{{ 'Dosis' }}</label>
    <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" onkeyup="kali();" >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
</div>
<div class="form-group {{ $errors->has('ppm') ? 'has-error' : ''}}">
    <label for="ppm" class="col-md-4 control-label">{{ 'Ppm' }}</label>
    <div class="col-md-6">
        <input class="form-control" name="ppm" type="text" id="ppm" onkeyup="kali();" >
        {!! $errors->first('ppm', '<p class="help-block">:message</p>') !!}
    </div>
</div>

<div class="form-group {{ $errors->has('x2') ? 'has-error' : ''}}">
    <label for="x2" class="col-md-4 control-label">{{ 'X2' }}</label>
    <div class="col-md-6">
        <input class="form-control" name="x2" type="text" id="x2" disabled>
        {!! $errors->first('x2', '<p class="help-block">:message</p>') !!}
    </div>
</div>

<div class="form-group {{ $errors->has('x2') ? 'has-error' : ''}}">
    <label for="x2" class="col-md-4 control-label">{{ 'Y2' }}</label>
    <div class="col-md-6">
        <input class="form-control" name="y2" type="text" id="y2" disabled>
        {!! $errors->first('y2', '<p class="help-block">:message</p>') !!}
    </div>
</div>

<div class="form-group {{ $errors->has('x2') ? 'has-error' : ''}}">
    <label for="x2" class="col-md-4 control-label">{{ 'XY' }}</label>
    <div class="col-md-6">
        <input class="form-control" name="xy" type="text" id="xy" disabled>
        {!! $errors->first('xy', '<p class="help-block">:message</p>') !!}
    </div>
</div>


<div class="form-group">
    <div class="col-md-offset-4 col-md-4">
        <input class="btn btn-primary" type="submit" value="{{ $submitButtonText or 'Create' }}">
    </div>
</div>



                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
     <script>
function kali() {
      var txtFirstNumberValue = document.getElementById('dosis').value;
      var txtSecondNumberValue = document.getElementById('ppm').value;
      var result = parseInt(txtFirstNumberValue) * parseInt(txtFirstNumberValue);
       var result1 = parseInt(txtSecondNumberValue) * parseInt(txtSecondNumberValue);
        var result2 = parseInt(txtFirstNumberValue) * parseInt(txtSecondNumberValue);
      if (!isNaN(result)) {
         document.getElementById('x2').value = result;
      }

      if (!isNaN(result1)) {
         document.getElementById('y2').value = result1;
      }

      if (!isNaN(result2)) {
         document.getElementById('xy').value = result2;
      }

}
</script>
@endsection
