<template>
<v-container>
<v-text-field v-model="titleall" id="title" label="Topik"></v-text-field>
<v-text-field v-model="bodyall" id="body" label="Isi Topik"></v-text-field>
<v-text-field v-model="key" id="key" label="Copy Token One Device"></v-text-field>
<v-btn info @click="onlickonedevices" :disabled="!formIsValid">Send One Devices</v-btn>
<v-text-field v-model="title" id="title" label="Topik"></v-text-field>
<v-text-field v-model="body" id="body" label="Isi Topik"></v-text-field>
<v-text-field  v-model="tglberita" id="tglberita" disabled>Tanggal Topik</v-text-field>
<v-btn info @click="onlickalldevices" :disabled="!formIsValidAll">Send Broadcash</v-btn>
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
      title: '',
      body: '',
      key: '',
      titleall: '',
      bodyall: '',
      tglberita: new Date()
    }
  },
  computed: {
    formIsValid () {
      return this.titleall !== '' &&
      this.bodyall !== '' &&
      this.key !== ''
    },
    formIsValidAll () {
      return this.title !== '' &&
      this.body !== ''
    }
  },
  methods: {
    onlickonedevices () {
      if (!this.formIsValid) {
        return
      }
      var key = '' // Token user
      var toOnedevices = this.key

      const notification = {
        titleall: this.titleall,
        bodyall: this.bodyall
      }
      fetch('https://fcm.googleapis.com/fcm/send', {
        'method': 'POST',
        'headers': {
          'Authorization': 'key=' + key,
          'Content-Type': 'application/json'
        },
        'body': JSON.stringify({
          'notification': notification,
          'to': toOnedevices
        })
      }).then(function (response) {
        alert('Notifikasi Success')
        console.log(response)
      }).catch(function (error) {
        console.error(error)
        alert('notifikasi gagal')
      })
      firebase.database().ref('berita/').push(notification)
      this.$router.push('/control')
      alert('Success di simpan')
    },
    onlickalldevices () {
      if (!this.formIsValidAll) {
        return
      }
      var key = '' // token user
      var toall = '/topics/' // name topic

      const notification = {
        title: this.title,
        body: this.body,
        tglberita: this.tglberita
      }
      fetch('https://fcm.googleapis.com/fcm/send', {
        'method': 'POST',
        'headers': {
          'Authorization': 'key=' + key,
          'Content-Type': 'application/json'
        },
        'body': JSON.stringify({
          'notification': notification,
          'to': toall
        })
      }).then(function (response) {
        alert('Notifikasi Success')
        console.log(response)
      }).catch(function (error) {
        console.error(error)
        alert('notifikasi gagal')
      })
      firebase.database().ref('berita/').push(notification)
      this.$router.push('/ViewCloud')
      alert('Success di simpan')
    }
  }
}
</script>
