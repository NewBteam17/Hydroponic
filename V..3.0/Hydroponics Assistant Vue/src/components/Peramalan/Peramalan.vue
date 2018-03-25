<template>
 <v-app light>
 <v-text>Peramalan</v-text>
 <v-layout row>
<v-flex xs12 sm6 offset-sm3>
 <v-layout id="data">
     <v-text-field value="X" readonly></v-text-field>
     <v-text-field value="Y" readonly></v-text-field>
    </v-layout>
    <v-layout id="data"> <label>No :  1</label>
     <v-text-field type="number" value="" v-model="nilaiX"></v-text-field>
     </br>
     <v-text-field type="number" value="" v-model="nilaiY"></v-text-field>
    </v-layout>
     <v-layout> <label>No :  2</label>
     <v-text-field type="number" value="" v-model="nilaiX"></v-text-field>
     </br>
     <v-text-field type="number" value="" v-model="nilaiY"></v-text-field>
    </v-layout>
     <v-layout> <label>No :  3</label>
     <v-text-field type="number" value=""></v-text-field>
     </br>
     <v-text-field type="number" value=""></v-text-field>
    </v-layout>
     <v-layout> <label>No :  4</label>
     <v-text-field type="number" value=""></v-text-field>
     </br>
     <v-text-field type="number" value=""></v-text-field>
    </v-layout>
     <v-layout> <label>No :  5</label>
     <v-text-field type="number" value=""></v-text-field>
     </br>
     <v-text-field type="number" value=""></v-text-field>
    </v-layout>
     <v-layout> <label>No :  6</label>
     <v-text-field type="number" value=""></v-text-field>
     </br>
     <v-text-field type="number" value=""></v-text-field>
    </v-layout>
     <v-layout> <label>No :  7</label>
     <v-text-field type="number" value=""></v-text-field>
     </br>
     <v-text-field type="number" value=""></v-text-field>
    </v-layout>
     <v-btn type="submit" id="prosesData">Proses</v-btn>
</v-flex>
</v-layout>
  </v-app>
</template>

<script>
    // var buttonCariY = document.querySelector('#cariY')
    // var xNumber = document.querySelector('#Xnumber')
    // var hasilY = document.querySelector('#hasilY')
    export default {
      data () {
        var hasilTable = document.querySelector('#hasilTable')
        var solusiRumus = document.querySelector('#solusiRumus')
        var Hkorelasi = document.querySelector('#korelasi')
        var HrumusKorelasi = document.querySelector('#rumuskorelasi')
        var kesimpulanKorelasiHasil = document.querySelector('#kesimpulankorelasi')
        var a
        var b
        // var y
        var nilaiX = []
        var nilaiY = []
        var nilaiXkuadrat = []
        var nilaiYkuadrat = []
        var nilaiXkaliY = []
        var jumlahNilaiX = 0
        var jumlahNilaiY = 0
        var jumlahNilaiXkuadrat = 0
        // var jumlahNilaiYKuadrat = 0
        var jumlahNilaiXkaliY = 0
        var banyakData = document.querySelector('#banyakData')
        var kolomData = document.querySelector('#kolomData')
        var data = document.querySelector('#data')
        var hasil = document.querySelector('#hasil')
        // var rumusnya = document.querySelector('#rumusnya')
        var jumlahData
        var buttonMasukan = document.querySelector('#masukanBanyakData')
        var buttonProses = document.querySelector('#prosesData')
        // buttonCariY.addEventListener('click', function () {
        //   y = a + (b * Number(xNumber.value))
        //   hasilY.innerHTML = 'Y = ' + y.toFixed(2)
        // })
        buttonMasukan.addEventListener('click', function () {
          hasil.style.display = 'none'
          kolomData.style.display = 'block'

          jumlahData = Number(banyakData.value)
          let textsnya = ''
          let no = 0
          for (var i = 0; i < jumlahData; i++) {
            no++
            textsnya += '<p>' + no + " <input class='x' type='number'> <input class='y' type='number'>"
          }
          data.innerHTML = textsnya
        })
        buttonProses.addEventListener('click', function () {
          for (var i = 0; i < jumlahData; i++) {
                // loop lakukan perhitungan dan pengambilan x2, y2, x * y
            nilaiX[i] = Number(document.querySelectorAll('.x')[i].value)
            nilaiXkuadrat[i] = Math.pow(nilaiX[i], 2)
            nilaiY[i] = Number(document.querySelectorAll('.y')[i].value)
            nilaiYkuadrat[i] = Math.pow(nilaiY[i], 2)
            nilaiXkaliY[i] = nilaiX[i] * nilaiY[i]
          }
            // lakukan pencarian nilai jumlah tiap varible arraay
          ambilJumlahNilai()
          // pecahkanRumus()
          cetakTableHasil()
          cetakKonsataA()
          cetakKonsataB()
          cetakKorelasi()
          kesimpulanKorelasi()
          // masukanMatikan()
        })
        //   function masukanMatikan () {
        //     buttonMasukan.disabled = true
        //   }
        function penumlahanNilai (data) {
          return data.reduce(function (a, b) {
            return a + b
          })
        }
        function hitungA () {
          a = (jumlahNilaiY * jumlahNilaiXkuadrat) - (jumlahNilaiX * jumlahNilaiXkaliY)
          return Number((a / ((jumlahData * jumlahNilaiXkuadrat) - Math.pow(jumlahNilaiX, 2))).toFixed(2))
        }
        function hitungB () {
          b = (jumlahData * jumlahNilaiXkaliY) - (jumlahNilaiX * jumlahNilaiY)
          return Number((b / ((jumlahData * jumlahNilaiXkuadrat) - (Math.pow(jumlahNilaiX, 2)))).toFixed(2))
        }
        //   function pecahkanRumus () {
        //     hasil.style.display = 'block'
        //     rumusnya.textContent = "Y = ' + a + ' + ' + b + ' * X "
        //   }
        function ambilJumlahNilai () {
          jumlahNilaiX = penumlahanNilai(nilaiX)
          jumlahNilaiY = penumlahanNilai(nilaiY)
          jumlahNilaiXkaliY = penumlahanNilai(nilaiXkaliY)
          jumlahNilaiXkuadrat = penumlahanNilai(nilaiXkuadrat)
          // jumlahNilaiYKuadrat = penumlahanNilai(nilaiYkuadrat)
          a = hitungA()
          b = hitungB()
        }
        function cetakTableHasil () {
          jumlahData = Number(banyakData.value)
          var textsnya = ''
          var no = 0
          for (var i = 0; i < jumlahData; i++) {
            no++
            textsnya += no + ' <input class="x" type="number" value=' + nilaiX[i] +
              "' readonly='readonly'> <input class='y' type='number'  value='" + nilaiY[i] +
              '"readonly="readonly"> <input class="x2" type="number"  value="' + nilaiXkuadrat[i] +
              '"readonly="readonly"> <input class="xkaliy" type="number"  value="' + nilaiXkaliY[i] +
              '"readonly="readonly">'
          }
          textsnya += '<input class="ex" type="text" value="Ex = ' + jumlahNilaiX +
               '" readonly="readonly"> <input class="ey" type="text"  value="Ey = ' + jumlahNilaiY +
               '"readonly="readonly"> <input class="ex2" type="text"  value="Ex2 = ' + jumlahNilaiXkuadrat +
               '"readonly="readonly"> <input class="exkaliy" type="text"  value="Ex*y = ' + jumlahNilaiXkaliY +
               '"readonly="readonly">'
          hasilTable.innerHTML = textsnya
        }
        function cetakKonsataA () {
          var teks = '<p>a = ((Ey)(Ex2) - (Ey)(Exy)) / n(Ex2) - (Ex)p2</p>'
          teks += '<p>a = ((' + jumlahNilaiY + ')(' + jumlahNilaiXkuadrat + ') - (' + jumlahNilaiY + ')(' + jumlahNilaiXkaliY + ')) / ' + jumlahData + '(' + jumlahNilaiXkuadrat + ') - (' + jumlahNilaiX + ')p2</p>'
          teks += '<p>a = " + a + "</p><hr>'
          solusiRumus.innerHTML = teks
        }
        function cetakKonsataB () {
          var teks = solusiRumus.innerHTML
          teks += '<p>b = (n(Exy) - (Ex)(Ey)) / n(Ex2) - (Ex)p2</p>'
          teks += '<p>a = (' + jumlahData + '(' + jumlahNilaiXkaliY + ') - (' + jumlahNilaiX + ')(' + jumlahNilaiY + ')) / ' + jumlahData + '(' + jumlahNilaiXkuadrat + ') - (' + jumlahNilaiX + ')p2</p>'
          teks += '<p>b = ' + b + '</p>'
          solusiRumus.innerHTML = teks
        }
        function cetakKorelasi () {
          HrumusKorelasi()
          Hkorelasi.textContent = Hkorelasi()
        }
        function kesimpulanKorelasi () {
          var hasilKesimpulan = Hkorelasi()
          var kesimpulan = 'Jadi Korelasinya'
          if (hasilKesimpulan <= 0) {
            kesimpulan += ' Tidak ada korelasi diantar dua variable (0 atau < 0)'
          } else if (hasilKesimpulan <= 0.25) {
            kesimpulan += ' Korelasi sangat lemah (>0 - 0,25)'
          } else if (hasilKesimpulan <= 0.5) {
            kesimpulan += ' Korelasi cukup  (>0.25 - 0.5)'
          } else if (hasilKesimpulan <= 0.75) {
            kesimpulan += ' Korelasi kuat (>0.5 - 0.75)'
          } else if (hasilKesimpulan <= 0.99) {
            kesimpulan += ' Korelasi sangat kuat (>0.75 - 0.99)'
          } else if (hasilKesimpulan <= 1) {
            kesimpulan += ' Korelasi sempurna (>0.99 - 1)'
          }
          kesimpulanKorelasiHasil.textContent = kesimpulan
        }

        // })
      }
    }
</script>
