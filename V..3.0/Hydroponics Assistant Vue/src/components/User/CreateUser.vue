<template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="40%" >
      <v-btn dark slot="activator">Tambah Pengguna</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline"></span>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12>
                <v-text-field id="email" label="Email" name="email" v-model="newPengguna.email" required></v-text-field>
              </v-flex>
              <v-flex xs12 sm6>
                <v-text-field id="idarduino" label="ID Arduino" name="idarduino" v-model="newPengguna.idarduino" required></v-text-field>
              </v-flex>
              <v-flex xs12 sm6>
                <v-select
                id="level"
                name="level"
                  label="Level User"
                  required
                  :items="['User','Admin']"
                  v-model="newPengguna.level"
                ></v-select>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn class="blue--text darken-1" flat @click.native="dialog = false">Close</v-btn>
          <v-btn class="blue--text darken-1" :disabled="!formIsValid" flat @click.native="onCreateUser">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
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
      dialog: false,
      newPengguna: {
        email: '',
        idarduino: '',
        level: ''
      }
    }
  },
  computed: {
    formIsValid () {
      return this.newPengguna.email !== '' &&
      this.newPengguna.idarduino !== '' &&
      this.newPengguna.level !== ''
    }
  },
  methods: {
    onCreateUser () {
      if (!this.formIsValid) {
        return
      }
      let ids = []
      ids = this.newPengguna.idarduino.split(',')
      this.newPengguna.idarduino = ids
      firebase.database().ref('pengguna').push(this.newPengguna)
      this.newPengguna.email = ''
      this.newPengguna.idarduino = ''
      this.newPengguna.level = ''
      this.dialog = false
      this.$router.push('/profile')
    }
  }
}
</script>
