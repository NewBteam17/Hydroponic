<script>
import { Line } from 'vue-chartjs'
import Vue from 'vue'
import firebase from '../firebase.js'
import VueFire from 'vuefire'

Vue.use(VueFire)
export default Line.extend({
  props: ['idk'],
  firebase: function () {
    return {
      pantaus: firebase.database().ref('pantau/' + this.idk).orderByKey().limitToLast(6)
    }
  },
  data () {
    return {
      chartph: [],
      chartpupukA: [],
      chartpupukB: [],
      chartKetinggian: [],
      chartec: [],
      chartsuhu: [],
      charttgl: [],
      options: {
        animation: false,
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: true
            },
            gridLines: {
              display: true
            }
          }],
          xAxes: [ {
            gridLines: {
              display: false
            }
          }]
        },
        legend: {
          display: true
        },
        responsive: true,
        maintainAspectRatio: false
      }
    }
  },
  mounted () {
    let that = this
    // setTimeout
    // setInterval
    setInterval(function () {
      let timestamp = 0
      let date = 0
      let formattedDate = ''
      that.chartph = []
      that.chartec = []
      that.chartpupukA = []
      that.chartpupukB = []
      that.chartKetinggian = []
      that.chartsuhu = []
      that.charttgl = []
      for (let item of that.pantaus) {
        that.chartph.push(item.ph)
        that.chartec.push(item.ec)
        that.chartpupukA.push(item.pupukA)
        that.chartpupukB.push(item.pupukB)
        that.chartKetinggian.push(item.ketinggianWadah)
        that.chartsuhu.push(item.suhu)
        timestamp = item['.key']
        date = new Date(timestamp * 1000)
        formattedDate =
        ('0' + date.getDate()).slice(-2) + '/' +
        ('0' + (date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear() + ' ' +
        ('0' + date.getHours()).slice(-2) + ':' +
        ('0' + date.getMinutes()).slice(-2)
        that.charttgl.push(formattedDate)
      }
      gambar(that)
    }, 1000)
    function gambar (a) {
      // alert(a.charttgl)
      a.renderChart({
        labels: a.charttgl,
        datasets: [
          {
            label: 'ec',
            backgroundColor: '#FA8258',
            pointBackgroundColor: 'red',
            borderWidth: 1,
            pointBorderColor: '#FA8258',
            data: a.chartec
          },
          {
            label: 'ph',
            backgroundColor: '#CED8F6',
            pointBackgroundColor: 'blue',
            borderWidth: 1,
            pointBorderColor: '#0101DF',
            data: a.chartph
          },
          {
            label: 'suhu',
            backgroundColor: '#81F79F',
            pointBackgroundColor: 'green',
            borderWidth: 1,
            pointBorderColor: '#82FA58',
            data: a.chartsuhu
          },
          {
            label: 'pupukA',
            backgroundColor: '#F3E2A9',
            pointBackgroundColor: 'yellow',
            borderWidth: 1,
            pointBorderColor: '#FACC2E',
            data: a.chartpupukA
          },
          {
            label: 'pupukB',
            backgroundColor: '#E2A9F3',
            pointBackgroundColor: 'purple',
            borderWidth: 1,
            pointBorderColor: '#CC2EFA',
            data: a.chartpupukB
          },
          {
            label: 'ketinggian Wadah',
            backgroundColor: '#A4A4A4',
            pointBackgroundColor: '#848484',
            borderWidth: 1,
            pointBorderColor: '#424242',
            data: a.chartKetinggian
          }
        ]
      }, a.options)
    }
  }
})
</script>

