<template>
<v-container>
<v-layout row wrap v-if="isloading">
    <v-flex xs12 class="text-xs-center">
    <v-progress-circular indeterminate class="primary--text" :width="7" :size="70"> 
    </v-progress-circular>
    </v-flex>
    </v-layout>
    
<v-layout row wrap v-else>
<v-flex>
<v-card>
<v-card-title :items="users" scope="props">
<h6 class="primary--text">Detail</h6>
<v-spacer></v-spacer>
<h6 class="primary--text">User</h6>
<template v-if="true">
<v-spacer></v-spacer>
</template>
</v-card-title>
</v-card> 
<v-card-actions>
<v-spacer></v-spacer>
</v-card-actions>

<v-data-table
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
      <td>{{ props.item['.value'] }}</td>
      
    </template>
    
  </v-data-table>
<v-spacer></v-spacer>
<v-btn router to="/Profile" class="info">Back</v-btn>
<app-edit-monitor-user-dialog :users="users" :idu="id"></app-edit-monitor-user-dialog>
  

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
  props: ['id'],
  firebase: function () {
    return {
      users: firebase.database().ref('pengguna/' + this.id)
    }
  },
  data () {
    // let xxx = this.users[0]['.value']
    return { /*
      mUsers: {
        email: 'sasasa',
        level: 'ccccc',
        idarduino: '222'
      } */
      qqq: this.users
    }
  },
  computed: {
    isloading () {
      return this.$store.getters.loading
    }
  }
}
</script>
