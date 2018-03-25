<template>
<v-container>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<h4 class="primary--text">Tambah Kendali</h4>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12>
<form @submit.prevent="onCreateMonitor">
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="idarduino" label="idarduino" id="idarduino" v-model="idarduino" required></v-text-field>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="diffTime" label="DiffTime" id="diffTime" v-model="diffTime" required></v-text-field>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="lastOn" label="LastOn" id="lastOn" v-model="lastOn" required></v-text-field>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-select
id="manual"
name="manual"
label="Manual"
required
:items="['On','Off']"
v-model="manual"
></v-select>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-select
id="status"
name="status"
label="Status"
required
:items="['On','Off']"
v-model="status"
></v-select>
</v-flex>
</v-layout>
<v-spacer></v-spacer>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-btn primary :disabled="!formIsValid" type="submit" >Tambah Kendali Baru</v-btn>
</v-flex>
</v-layout>
</form>
</v-flex>
</v-layout>
</v-container>
</template>

<script>
import Vue from 'vue'
import firebase from '../../firebase.js'
import VueFire from 'vuefire'

Vue.use(VueFire)

export default {
  data () {
    return {
      diffTime: '',
      date: null,
      time: new Date(),
      manual: '',
      lastOn: '',
      status: '',
      idarduino: ''
    }
  },
  computed: {
    formIsValid () {
      return this.idarduino !== '' &&
      this.diffTime !== '' &&
      this.manual !== '' &&
      this.lastOn !== '' &&
      this.status !== ''
    },
    submitTableDateTime () {
      const date = new Date(this.date)
      if (typeof this.time === 'string') {
        const hours = this.time.match(/^(\d+)/)[1]
        const minutes = this.time.match(/:(\d+)/)[1]
        date.setHours(hours)
        date.setMinutes(minutes)
      } else {
        date.setHours(this.time.getHours())
        date.setMinutes(this.time.getMinutes())
      }
      console.log(date)
      return date
    }
  },
  methods: {
    onCreateMonitor () {
      if (!this.formIsValid) {
        return
      }
      const controlData = {
        diffTime: this.diffTime,
        lastOn: this.lastOn,
        manual: this.manual,
        status: this.status
      }
      firebase.database().ref('kendali/' + this.idarduino).set(controlData)
      this.$router.push('/control')
      alert('Success di simpan' + this.idarduino)
    }
  }
}
</script>
