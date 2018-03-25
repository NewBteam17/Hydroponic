<template>
<v-container>
<v-toolbar light>
    <v-chip label class="pink white--text">
      <v-icon left></v-icon>Form Pengguna
    </v-chip>
    <v-spacer></v-spacer>
    <v-toolbar-items class="hidden-sm-and-down">
      <app-dialog-create-user :users="users"></app-dialog-create-user>
    </v-toolbar-items>
  </v-toolbar>
      <v-data-table
      v-bind:headers="headers"
      :items="users"
      class="elevation-1"
    >
    <template slot="headerCell" scope="props">
      <span v-tooltip:bottom="{ 'html': props.header.text }">
        {{ props.header.text }}
      </span>
    </template>
    <template slot="items" scope="props">
    <td>{{ props.item['.key'] }}</td>
      <td class="text-xs-right">{{ props.item.email }}</td>
      <td class="text-xs-right"><!-- {{ props.item.idarduino }} -->
      
        <ul id="list-idarduino">
		  <tr v-for="item in props.item.idarduino">
		    {{ item }}
		  </tr>
		</ul>
      
      </td>
      <td class="text-xs-right">{{ props.item.level }}</td>
      <v-btn :to="'/Profileid/' + props.item['.key']">View</v-btn>
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
      users: firebase.database().ref('pengguna')
    },
    data () {
      return {
        headers: [
          {
            text: 'Unique key',
            align: 'left',
            sortable: false,
            value: 'name'
          },
          { text: 'Email', value: 'email' },
          { text: 'ID Arduino', value: 'status' },
          { text: 'Status Pengguna', value: 'level' },
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
