<template>
<v-container>
<v-toolbar light>
     <v-chip label class="pink white--text">
      <v-icon left></v-icon>Form Perangkat
    </v-chip>   
    <v-spacer></v-spacer>
    <app-dialog-create-perangkat :perangkats="perangkats"></app-dialog-create-perangkat>
  </v-toolbar>
      <v-data-table
      v-bind:headers="headers"
      :items="perangkats"
      class="elevation-1"
    >
    <template slot="headerCell" scope="props">
      <span v-tooltip:bottom="{ 'html': props.header.text }">
        {{ props.header.text }}
      </span>
    </template>
    <template slot="items" scope="props">
    <td>{{ props.item['.key'] }}</td>
    <td>{{ props.item.aktif }}</td>
    <td>{{ props.item.tglprod }}</td>
    <v-btn primary :to="'/Monitor/' + props.item['.key']">View</v-btn>
    <v-btn warning :to="'/Chart/' + props.item['.key']">Chart</v-btn>
    <v-menu bottom offset-x>
        <v-btn slot="activator">Pilih Statistik</v-btn>
        <v-list>
        <v-btn info :to="'/GraphPH/' + props.item['.key']">Statistik PH</v-btn>
        </v-list>
        <v-list>
        <v-btn info :to="'/GraphEC/' + props.item['.key']">Statisik EC</v-btn>
        </v-list>
        <v-list>
        <v-btn info :to="'/GraphSuhu/' + props.item['.key']">Statisik Suhu</v-btn>
        </v-list><v-list>
        <v-btn info :to="'/Graphketing/' + props.item['.key']">Statisik Ketinggan Wadah</v-btn>
        </v-list>
        <v-list>
        <v-btn info :to="'/GraphpupukA/' + props.item['.key']">Statisik Pupuk A</v-btn>
        </v-list>
        <v-list>
        <v-btn info :to="'/GraphpupukB/' + props.item['.key']">Statisik Pupuk B</v-btn>
        </v-list>
      </v-menu>
    </template>
  </v-data-table>
  </v-container>
</template>
  
<script>
  import Vue from 'vue'
  import firebase from '../../firebase.js'
  import VueFire from 'vuefire'

  Vue.use(VueFire)

  export default {
    firebase: {
      perangkats: firebase.database().ref('perangkat')
    },
    data () {
      return {
        search: '',
        headers: [
          {
            text: 'ID Arduino',
            align: 'right',
            sortable: false,
            value: 'name'
          },
          { text: 'aktif', value: 'status' },
          { text: 'terproduksi', value: 'level' },
          { text: 'Action', value: 'Action' }
        ],
        items: [
          {
            title: 'Click Me'
          },
          {
            title: 'Click Me'
          },
          {
            title: 'Click Me'
          },
          {
            title: 'Click Me 2'
          }
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
