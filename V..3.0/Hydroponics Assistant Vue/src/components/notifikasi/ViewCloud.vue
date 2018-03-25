<template>
<v-container>
</v-layout>
<v-toolbar light> 
    <v-chip label class="pink white--text">
      <v-icon left>info</v-icon>Notifikasi
    </v-chip>
  </v-toolbar>
      <v-data-table
      v-bind:headers="headers"
      :items="pesan"
      class="elevation-12"
    >
    <template slot="headerCell" scope="props">
      <span v-tooltip:bottom="{ 'html': props.header.text }">
        {{ props.header.text }}
      </span>
    </template>
    <template slot="items" scope="props">
    <td>{{ props.item.email }}</td>
    <td>{{ props.item.latti }}</td>
    <td>{{ props.item.longi }}</td>
    <td>{{ props.item.token }}</td>
    </template>
  </v-data-table>
  <v-container></v-container>
  <v-dialog v-model="dialog" fullscreen transition="dialog-bottom-transition" :overlay=false>
      <v-btn primary dark slot="activator">Notifikasi Satu Device</v-btn>
      <v-card>
        <v-toolbar dark class="primary">
          <v-btn icon @click.native="dialog = false" dark>
            <v-icon>close</v-icon>
          </v-btn>
          <v-toolbar-title>Kirim Notifikasi</v-toolbar-title>
        </v-toolbar>
        <v-card class="elevation-0">
    <v-card-text>
      <v-container fluid>
        <v-layout row>
          <v-flex xs2>
            <v-subheader>Judul Berita</v-subheader>
          </v-flex>
          <v-flex xs4>
            <v-text-field
              id="judul"
              label="Berita"
              multi-line
              v-model="judul"
            ></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs2>
            <v-subheader>Isi berita</v-subheader>
          </v-flex>
          <v-flex xs10>
            <v-text-field
              id="berita"
              label="Isi Berita"
              multi-line
              v-model="berita"
            ></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs2>
            <v-subheader>Copy Token Device</v-subheader>
          </v-flex>
          <v-flex xs10>
            <v-text-field
              label="Token"
              v-model="key"
            ></v-text-field>
            <v-btn primary @click="onclick" :disabled="!formIsValid">Kirim Notifikasi</v-btn>
          </v-flex>
        </v-layout>
      </v-container>
    </v-card-text>
  </v-card>
      </v-card>
    </v-dialog>
    <v-dialog v-model="dialogall" fullscreen transition="dialog-bottom-transition" :overlay=false>
      <v-btn primary dark slot="activator">Notifikasi Semua Devices</v-btn>
      <v-btn waring dark slot="activator" router to="/History">Riwayat Broadcast</v-btn>
      
      <v-card>
        <v-toolbar dark class="primary">
          <v-btn icon @click.native="dialogall = false" dark>
            <v-icon>close</v-icon>
          </v-btn>
          <v-toolbar-title>Kirim Broadcast Notifikasi</v-toolbar-title>
        </v-toolbar>
        <v-card class="elevation-0">
    <v-card-text>
      <v-container fluid>
        <v-layout row>
          <v-flex xs2>
            <v-subheader>Judul Berita</v-subheader>
          </v-flex>
          <v-flex xs4>
            <v-text-field
              name="titleall"
              label="judul berita"
              multi-line
              v-model="title"
            ></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row>
          <v-flex xs2>
            <v-subheader>Isi Berita</v-subheader>
          </v-flex>
          <v-flex xs10>
            <v-text-field
              name="input-7-2"
              label="isi berita diisni"
              multi-line
              v-model="body"
            ></v-text-field>
            <v-btn primary @click="onlickalldevices" :disabled="!formIsValidAll">Send Notifikasi Broadcast</v-btn>
          </v-flex>
        </v-layout>
      </v-container>
    </v-card-text>
  </v-card>
      </v-card>
    </v-dialog>
  </v-container>
</template>
  
<script>
  import Vue from 'vue'
  import firebase from '../../firebase.js'
  import VueFire from 'vuefire'

  Vue.use(VueFire)

  export default {
    firebase: {
      pesan: firebase.database().ref('pesan')
    },
    data () {
      return {
        search: '',
        dialogall: false,
        dialog: false,
        notifications: false,
        sound: true,
        widgets: false,
        judul: '',
        berita: '',
        key: '',
        title: '',
        body: '',
        tglberita: new Date(),
        headers: [
          {
            text: 'Email',
            align: 'right',
            sortable: false,
            value: 'name'
          },
          { text: 'Latitude', value: 'level' },
          { text: 'Longatitude', value: 'Action' },
          { text: 'Token', value: 'Action' }
        ],
        items: [
        ]
      }
    },
    computed: {
      loading () {
        return this.$store.getters.loading
      },
      formIsValid () {
        return this.judul !== '' &&
        this.berita !== '' &&
        this.key !== ''
      },
      formIsValidAll () {
        return this.title !== '' &&
        this.body !== ''
      }
    },
    methods: {
      onclick () {
        if (!this.formIsValid) {
          return
        }
        var key = ''
        var to = this.key
        var notification = {
          title: this.judul,
          body: this.berita
        }
        fetch('https://fcm.googleapis.com/fcm/send', {
          'method': 'POST',
          'headers': {
            'Authorization': 'key=' + key,
            'Content-Type': 'application/json'
          },
          'body': JSON.stringify({
            'notification': notification,
            'to': to
          })
        }).then(function (response) {
          console.log(response)
          alert('Notifikasi Success')
        }).catch(function (error) {
          console.error(error)
        })
        firebase.database().ref('berita/').push(notification)
        this.$router.push('/History')
        // alert('Success di simpan')
      },
      onlickalldevices () {
        if (!this.formIsValidAll) {
          return
        }
        var key = ''
        var toall = ''

        const notification = {
          title: this.title,
          body: this.body
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
        }).then(function (notification) {
          alert('Notifikasi Success')
        }).catch(function (error) {
          console.error(error)
          alert('notifikasi gagal')
        })
        firebase.database().ref('berita/').push(notification)
        this.$router.push('/History')
        alert('Success di simpan')
      }
    }
  }
</script>
