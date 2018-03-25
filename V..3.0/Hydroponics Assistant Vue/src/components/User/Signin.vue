<template>
<v-container>
<v-layout row v-if="error">
<v-flex xs12 sm6 offset-sm3>
<app-alert @dismissed="onDismissed" :text="error.message"></app-alert>
</v-flex>
</v-layout>
<v-layout>
<v-flex xs12 md6 offset-sm3>
<v-card>
<v-card-text>
<v-container>
<v-toolbar class="blue" dark>
<v-toolbar-title>WELCOME</v-toolbar-title>
</br>
 </v-toolbar>
<form>
<br>
<img src="../../assets/aa.jpeg">
<v-layout row>
<v-btn flat primary link small color="blue">
      <v-icon primary>check_circle</v-icon>
      </v-btn>
    <button class="loginBtn loginBtn--google" v-on:click="googleLogin()">
  Login with Google
</button>
</v-layout>
</form>
</v-container>
</v-card-text>

</v-card>
</v-flex>
</v-layout>
</v-container>
</template>
<script>
import Vue from 'vue'
import VueFire from 'vuefire'
import Firebase from 'firebase'
Vue.use(VueFire)
var provider = new Firebase.auth.GoogleAuthProvider()
export default {
  data () {
    return {
      email: '',
      password: ''
    }
  },
  computed: {
    user () {
      return this.$store.getters.user
    },
    error () {
      return this.$store.getters.error
    }
  },
  watch: {
    user (value) {
      if (value !== null && value !== undefined) {
        this.$router.push('/homeadmin')
      }
    }
  },
  methods: {
    onSignin () {
      Firebase.auth().signInWithEmailAndPassword(this.email, this.password).then(user => {
        this.$store.dispatch('setUser', user)
        this.$router.push('/')
      })
      .catch(error => {
        console.log(error)
      })
    },
    onDismissed () {
      this.$store.dispatch('clearError')
    },
    googleLogin: function () {
      Firebase.auth().signInWithPopup(provider).then(result => {
        this.$store.dispatch('autoSignIn', result.user.email)
        this.$router.push('/homeadmin')
        alert('Login Succes')
      })
      .catch((error) => {
        console.log(error)
      })
    }
  }
}
</script>

<style>
body { padding: 2em; }


/* Shared */
.loginBtn {
  box-sizing: border-box;
  position: relative;
  /* width: 13em;  - apply for fixed size */
  margin: 0.2em;
  padding: 0 100px 0 65px;
  border: none;
  text-align: left;
  line-height: 34px;
  white-space: nowrap;
  border-radius: 0.2em;
  font-size: 16px;
  color: #FFF;
}
.loginBtn:before {
  content: "";
  box-sizing: border-box;
  position: absolute;
  top: 0;
  left: 0;
  width: 34px;
  height: 100%;
}
.loginBtn:focus {
  outline: none;
}
.loginBtn:active {
  box-shadow: inset 0 0 0 32px rgba(0,0,0,0.1);
}
/* Google */
.loginBtn--google {
  /*font-family: "Roboto", Roboto, arial, sans-serif;*/
  background: #DD4B39;
}
.loginBtn--google:before {
  border-right: #BB3F30 1px solid;
  background: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/14082/icon_google.png') 6px 6px no-repeat;
}
.loginBtn--google:hover,
.loginBtn--google:focus {
  background: #E74B37;
}
  
</style>
