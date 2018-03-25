<template>
<v-container>
<v-toolbar light>
    <v-chip label class="pink white--text">
      <v-icon left></v-icon>Form Kontrol
    </v-chip>
  </v-toolbar>
  <v-data-table
      v-bind:headers="headers"
      :items="kendali"
      class="elevation-1"
    >
    <template slot="headerCell" scope="props">
      <span v-tooltip:bottom="{ 'html': props.header.text }">
        {{ props.header.text }}        
      </span>
    </template>
    <template slot="items" scope="props">
    <td>{{ props.item['.key'] }}</td>
      <td>{{ props.item.diffTime }}</td>
      <td class="text-xs-right">{{ props.item.lastOn }}</td>
      <td class="text-xs-right">{{ props.item.manual }}</td>
      <td class="text-xs-right">{{ props.item.status }}</td>
      <v-btn primary :to="'/controls/' + props.item['.key']">View</v-btn>
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
      kendali: firebase.database().ref('kendali')
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
