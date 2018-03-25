import Vue from 'vue'
// import VueFire from 'vuefire'
import Vuetify from 'vuetify' // Install plgin vuetify untuk template design
import App from './App'
import router from './router'
import { store } from './store'
import DateFilter from './filters/date'
// import * as firebase from 'firebase'
import firebase from './firebase.js' // import firebase.js
import AlertCmp from './components/Shared/Alert.vue'
import EditMonitor from './components/Pantau/EditMonitor.vue'
import EditMonitorDialog from './components/Pantau/EditMonitorDialog.vue'
import EditMonitorTime from './components/Pantau/EditMonitorTime.vue'
import EditUser from './components/User/EditUser.vue'
import EditControl from './components/Kendali/EditControl.vue'
import CreateControl from './components/User/CreateUser.vue'
import CreatePerangkat from './components/Perangkat/CreatePerangkat.vue'
import VueResource from 'vue-resource'

// Vue.use(VueFire)
Vue.use(VueResource)
Vue.use(Vuetify)
Vue.filter('date', DateFilter)
Vue.component('app-alert', AlertCmp)
Vue.component('app-edit-monitor-details-dialog', EditMonitor)
Vue.component('app-edit-monitor-date-dialog', EditMonitorDialog)
Vue.component('app-edit-monitor-time-dialog', EditMonitorTime)
Vue.component('app-edit-monitor-user-dialog', EditUser)
Vue.component('app-edit-monitor-control-dialog', EditControl)
Vue.component('app-dialog-create-user', CreateControl)
Vue.component('app-dialog-create-perangkat', CreatePerangkat)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
  created () {
    firebase.auth().onAuthStateChanged((user) => {
      if (user) {
        this.$store.dispatch('autoSignIn', user)
      }
    }) // untuk login otomatis masuk ke $store.dispatch
    // this.$store.dispatch('abc')
  }
})
