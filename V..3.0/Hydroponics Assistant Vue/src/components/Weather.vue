<template>
<v-container>

<v-toolbar light>
    <v-chip label class="pink white--text">
      <v-icon left></v-icon>Form Kontrol
    </v-chip>
  </v-toolbar>
  <v-data-table
      v-bind:headers="headers"
      :items="user"
      class="elevation-1"
    >
    <template slot="headerCell" scope="props">
      <span v-tooltip:bottom="{ 'html': props.header.text }">
        {{ props.header.text }}        
      </span>
    </template>
    <template slot="items" scope="props">
      <td>{{ props.item.uid }}</td>
    </template>
  </v-data-table>  
  </v-container>
</template>
<script>
  import Vue from 'vue'
  import firebase from '../firebase.js'
  import VueFire from 'vuefire'

  Vue.use(VueFire)
  export default {
    firebase: {
      user: firebase.database().ref('users')
    },
    data () {
      return {
        headers: [
          {
            text: 'Control Arduino',
            align: 'left',
            sortable: false,
            value: 'name'
          },
          { text: ' DiffTime', value: 'diffTime' },
          { text: 'Last ON', value: 'lastOn' },
          { text: 'Manual', value: 'manual' },
          { text: 'Status', value: 'status' },
          { text: 'Action', value: 'Action' }
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
