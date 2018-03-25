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
<v-card-title>
<h6 class="primary--text">{{ id }}</h6>
<v-data-table
      v-bind:headers="headers"
      :items="pantaus"
      class="elevation-1"
    >
    <template slot="headerCell" scope="props">
      <span v-tooltip:bottom="{ 'html': props.header.text }">
        {{ props.header.text }}
      </span>
    </template>
    <template slot="items" scope="props">
    <td>{{ props.item['.key'] }}</td>
      <td>
      <br>
      <ul>
      	<td>
      		Ec : {{ props.item.ec }}
      	</td>
      	<td>
      		Ph : {{ props.item.ph }}
      	</td>
      	<td>
      		pupuk A : {{ props.item.pupukA }}
      	</td>
      	<td>
      		pupuk B : {{ props.item.pupukB }}
      	</td>
      	<td>
      		suhu : {{ props.item.suhu }}
      	</td>
      	<td>
      		ketinggian Wadah : {{ props.item.ketinggianWadah }}
      	</td>
      </ul>

	</td>
    </template>
  </v-data-table>
<template v-if="true">
<v-spacer></v-spacer>
</template>
</v-card-title>
</v-card>
<v-card-text>
</v-card-text>
<v-card-actions>
<v-spacer></v-spacer>
<v-btn large router to="/Perangkat" class="info">Back</v-btn>
</v-card-actions>
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
      pantaus: firebase.database().ref('pantau/' + this.id).orderByKey().limitToLast(10)
    }
  },
  data () {
    return {
      headers: [
        {
          text: 'Tanggal rekam',
          align: 'left',
          sortable: false,
          value: 'name'
        }
      ]
    }
  },
  computed: {
    isloading () {
      return this.$store.getters.loading
    },
    userIsAuthenticated () {
      return this.$store.getters.user !== null && this.$store.getters.user !== undefined
    },
    userIsCreator () {
      if (!this.userIsAuthenticated) {
        return false
      }
      return this.$store.getters.user.id === this.id // buat apa?
    }
  }
}
</script>
