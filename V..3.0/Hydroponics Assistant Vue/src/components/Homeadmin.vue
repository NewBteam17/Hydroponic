<template>
 <v-app light>
    <main>
      <template>
      <v-container>
      <v-layout row justify-space>
    <v-flex md12 lg8 class="hidden-xs-only">
      <v-date-picker v-model="e4" landscape></v-date-picker>
    </v-flex>
     <v-flex md12 lg8 class="hidden-xs-only">
      <v-time-picker v-model="e4" landscape></v-time-picker>
    </v-flex>
  </v-layout>
   <br>
    <v-progress-linear v-bind:indeterminate="true"></v-progress-linear>
  <v-parallax>
          <v-layout
            column
            align-center
            justify-center
            class="white--text"
          >
          <img src="../assets/android.png" height="500">
          </v-layout>
        </v-parallax>
  </v-container>
</template>

    </main>
  </v-app>
</template>

<script>
    export default {
      data () {
        return {
          e4: '',
          items: [
            {
              src: 'http://newbteam.herokuapp.com/assets/img/LUCY2.png'
            }
          ]
        }
      },
      computed: {
        monitors () {
          return this.$store.getters.fetureMonitor
        },
        users () {
          return this.$store.getters.fetureUser
        },
        loading () {
          return this.$store.getters.loading
        }
      },
      methods: {
        onloadMonitor (id) {
          this.$router.push('/monitor/' + id)
        }
      }
    }
</script>

<style scoped>
.title {
    position: absolute;
    bottom: 50px;
    background-color: rgba(0,0,0,0.5);
    color: white;
    font-size: 2em;
    padding: 20px;
}
</style>
