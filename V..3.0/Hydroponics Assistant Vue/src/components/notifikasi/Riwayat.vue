<template>
    <v-container>
  <v-layout row>
    <v-flex xs12 sm12>
      <v-card>
      <v-container></v-container>
        <v-toolbar class="light-blue accent-2" dark>
          <v-toolbar-title>Riwayat</v-toolbar-title>
        </v-toolbar>
        <v-list two-line>
          <template v-for=" beritas in berita">
            <v-list-tile avatar>
              <v-list-tile-content>
              <v-list-tile-title>Judul Berita : {{ beritas.titleall }}  {{ beritas.title }}</v-list-tile-title>
                <v-list-tile-sub-title>{{ beritas.bodyall }} - {{ beritas.body }}</v-list-tile-sub-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-list-tile-action-text>{{ beritas['.key'] }}</v-list-tile-action-text>
                <v-icon color="grey lighten-1">info</v-icon>
              </v-list-tile-action>
            </v-list-tile>
          </template>
        </v-list>
      </v-card>
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
    firebase: {
      berita: firebase.database().ref('berita/')
    },
    data () {
      return {
        headers: [
          {
            text: 'History',
            align: 'left',
            sortable: false,
            value: 'name'
          },
          { text: 'Isi Berita', value: 'status' },
          { text: 'Email', value: 'email' },
          { text: 'ID Arduino', value: 'status' }
        ],
        items: [
        ]
      }
    },
    computed: {
      loading () {
        return this.$store.getters.loading
      }
    }
  }
</script>
