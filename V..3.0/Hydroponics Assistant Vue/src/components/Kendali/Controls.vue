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
<h6 class="primary--text">{{id }}</h6>
<v-spacer></v-spacer>
<h6 class="primary--text">status: {{kendali[2]['.value']}}</h6>
<template v-if="true">
<v-spacer></v-spacer>
<app-edit-monitor-control-dialog :control="kendali" :idc="id"></app-edit-monitor-control-dialog>
</template>
</v-card-title>
</v-card>
<v-card-text>
</v-card-text>
<v-card-actions>
<v-spacer></v-spacer>
<v-btn large router to="/control" class="info">Back</v-btn>
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
      kendali: firebase.database().ref('kendali/' + this.id)
    }
  },
  computed: {
    isloading () {
      return this.$store.getters.loading
    }
  }
}
</script>
