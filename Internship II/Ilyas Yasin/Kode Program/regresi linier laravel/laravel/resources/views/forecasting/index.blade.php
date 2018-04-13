@extends('admin.index')

@section('content')
    <div class="container">
        <div class="row">
                         <div class="col-md-12">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Forecasting Regresi Linier</h4>
                                <p class="category">Hydrponics Assistant</p>
                         <div class="content table-responsive table-full-width">
                            <table class="table table-hover table-striped">
                                <thead>
                                    <tr>
                                        <th>#</th><th>X</th><th>Y</th>
                                        <th>X2</th>
                                        <th>Y2</th>
                                         <th>XY</th>
                                    </tr>
                                </thead>
                                <tbody>
                                @foreach($forecasting as $item)
                                    <tr>
                                        <td>{{ $loop->iteration or $item->id }}</td>
                                        <td>{{  $item->dosis }}</td>
                                        <td>{{ $item->ppm }}</td>
                                        <td>{{ $item->dosis * $item->dosis }}</td>
                                        <td>{{ $item->ppm * $item->ppm }}</td>
                                        <td>{{ $item->dosis * $item->ppm }}</td>
                                          <td>
                                            <a href="{{ url('/forecasting/' . $item->id . '/edit') }}" title="Edit forecasting"><button class="btn btn-primary btn-sm"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</button></a>

                                            <form method="POST" action="{{ url('/forecasting' . '/' . $item->id) }}" accept-charset="UTF-8" style="display:inline">
                                                {{ method_field('DELETE') }}
                                                {{ csrf_field() }}
                                                <button type="submit" class="btn btn-danger btn-sm" title="Delete forecasting" onclick="return confirm(&quot;Confirm delete?&quot;)"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</button>
                                            </form>
                                        </td>  
                                        
                                    </tr>
                                @endforeach
                                </tbody>
                            </table>
                        </div>
                               <div class="table-responsive">
                            <table class="table">
                            <thead>
                                    <tr>
                                         <th>Jumlah X</th>
                                          <th>Jumlah Y</th>
                                           <th>Jumlah X2</th>
                                            <th>Jumlah Y2</th>
                                             <th>Jumlah XY</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                         <td>{{ $jumlahx }}</td>
                                         <td>{{ $jumlahy }}</td>
                                         <td>{{ $x2}}</td>
                                         <td>{{ $y2 }}</td>
                                         <td>{{ $xy}}</td>
                                    </tr>
                                </tbody>
                            </table>
                            </div>
                              <div class="table-responsive">
                            <table class="table">
                            <thead>
                                    <tr>
                                        <th>Menghitung Nilai Miring = b</th>
                                       <th>Nilai b</th>
                                        <th>Nilai b</th>
                                         <th>Menghitung Nilai Miring = a</th>
                                         <th>Menentukan Besar Kesalahan Standar Estimasi</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                         <td>{{ $b1 = $count * $xy - $jumlahx * $jumlahy  }}</td>
                                             <td>{{ $b2 = $count * $x2 - ($jumlahx * $jumlahx) }}</td> 
                                           <td> {{ $hasil = $b1/$b2 }} </td>
                                            <td>{{$total = (200) * ($jumlahx) - ($jumlahy)}}</td>
                                            <td>{{ $se = $y2 - 0 * $jumlahy - 200 * $xy / $count - 2 }} </td>
                                    </tr>
                                </tbody>
                            </table>
                            </div>
                              <div class="table-responsive">
                            <table class="table">
                            <thead>
                                    <tr>
                                        <th>Hasil R1</th>
                                        <th>Hasil R2</th>
                                        <th>Hasil R3</th>
                                        <th>Hasil Koefisien Determinasi R1 / R2 * R3</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                         <td>{{ $r1 = $count * $xy -  $jumlahx * $jumlahy  }}</td>
                                         <td>{{ $r2 = ($count * $x2 - $jumlahx * $jumlahx)*0.5 }}</td>
                                         <td>{{ $r3 = ($count * $y2 - $jumlahy * $jumlahy)*0.5 }}</td>
                                         <td>{{ $r1 / $r2 * $r3 }} </td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="container">
        <div class="row">
                         <div class="col-md-10">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Persamaan Regresi Linier Sederhana</h4>
                                <p class="category">Faktor Akibat</p>

                                                    <form accept-charset="UTF-8" class="form-inline my-2 my-lg-0 float-right" role="search">
                           <div class="form-group {{ $errors->has('dosis') ? 'has-error' : ''}}">
    <label for="dosis" class="col-md-4 control-label">{{ 'a' }}</label>
    <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" value={{$hasil}} >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
    <label for="dosis" class="col-md-4 control-label">{{ 'b' }}</label> 
     <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" value={{$total}} >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
    <label for="dosis" class="col-md-4 control-label">{{ 'X' }}</label> 
     <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" value="4" >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
    </td>
                            <button class="btn btn-secondary" type="submit">
                                    Cari X</button>
                                    <label for="dosis" class="col-md-4 control-label">{{ 'Dosis' }}</label> 
     <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" value="{{$carix =  4 * $hasil + $total}}" >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
    </br>
    <label> jadi Jika dosis pupuk nya mencapai 4 ml maka dipredisikan jumlah larutan yang dibutuhkan mencapai {{$carix}} PPM</label>
                        </form>
                         <h4 class="title">Persamaan Regresi Linier Sederhana</h4>
                                <p class="category">Faktor Penyebab</p>
                        <form  accept-charset="UTF-8" class="form-inline my-2 my-lg-0 float-right" role="search">
                           <div class="form-group {{ $errors->has('dosis') ? 'has-error' : ''}}">
    <label for="dosis" class="col-md-4 control-label">{{ 'a' }}</label>
    <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" value={{$hasil}} >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
    <label for="dosis" class="col-md-4 control-label">{{ 'b' }}</label> 
     <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" value={{$total}} >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
    <label for="dosis" class="col-md-4 control-label">{{ 'Y' }}</label> 
     <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" value="800" >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
    </td>
                            <button class="btn btn-secondary" type="submit">
                                    Cari Y</button>
                                    <label for="dosis" class="col-md-4 control-label">{{ 'Dosis' }}</label> 
     <div class="col-md-6">
        <input class="form-control" name="dosis" type="text" id="dosis" value="{{ $cariy =  ($total + 800) / $hasil}}" >
        {!! $errors->first('dosis', '<p class="help-block">:message</p>') !!}
    </div>
    </br>
    <label>Jika Jumlah larutan nya PPM 800 (Variabel Y), Berapa Dosis yang dibutuhkan apabila target yang dibutuhkan dalam jumlah ditentukan 						
jadi prediksi larutan yang sesuai mencapai adalah {{$cariy}} ml					
</label>

                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
function kali() {
      var txtFirstNumberValue = document.getElementById('dosis').value;
      var txtSecondNumberValue = document.getElementById('ppm').value;
      var result = parseInt(txtFirstNumberValue) * parseInt(txtSecondNumberValue);
      if (!isNaN(result)) {
         document.getElementById('xy').value = result;
      }
}
</script>
@endsection
