<template>
 <v-app light>
    <main>
      <section>
      <v-container>
        <v-parallax src="">
          <v-layout
            column
            align-center
            justify-center
            class="white--text"
          >
          </v-layout>
          <img src="../assets/aa.jpeg" height="500">
           </v-parallax>
           </v-container>
      </section>
        <v-layout
          column
          wrap
          class="my-5"
          align-center
        >
          <v-flex xs12 sm4 class="my-3">
            <div class="text-xs-center">
              <h2 class="headline">HYDROPONICS ASSISTANT</h2>
              <span class="subheading">
              POLITEKNIK POS INDONESIA
              </span>
            </div>
          </v-flex>
        </v-layout>
      </section>
      <v-container>
       <v-parallax src="">
          <v-layout
            column
            align-center
            justify-center
            class="white--text"
          >
          </v-layout>
          <img src="../assets/android.png" height="500">
          </v-parallax>
          </v-container>
      <section>
        <v-container grid-list-xl>
          <v-layout row wrap justify-center class="my-5">
            <v-flex xs12 sm4>
              <v-card class="elevation-0 transparent">
                <v-card-title primary-title class="layout justify-center">
                  <div class="headline">Contact us</div>
                </v-card-title>
                <v-card-text>
                  --
                </v-card-text>
                <v-list class="transparent">
                  <v-list-tile>
                    <v-list-tile-action>
                      <v-icon class="blue--text text--lighten-2">phone</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                      <v-list-tile-title>-</v-list-tile-title>
                    </v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-action>
                      <v-icon class="blue--text text--lighten-2">place</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                      <v-list-tile-title>BANDUNG JAWABARAT</v-list-tile-title>
                    </v-list-tile-content>
                  </v-list-tile>
                  <v-list-tile>
                    <v-list-tile-action>
                      <v-icon class="blue--text text--lighten-2">email</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                      <v-list-tile-title>newbTeam17@gmail.com</v-list-tile-title>
                    </v-list-tile-content>
                  </v-list-tile>
                </v-list>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
      </section>
    </main>
  </v-app>
</template>

<script>
    export default {
      data () {
        return {
          items: [
            {
              src: 'http://newbteam.herokuapp.com/assets/img/LUCY2.png'
            },
            {
              src: 'https://lh4.googleusercontent.com/TP1azC4mvzYXUfBjnFUaNluyf0VUv9YwaLMeXGujdiIjfOdapfjDFFypr5UDMJkpd505-VK3J4NsyH8=w1366-h613-rw'
            },
            {
              src: 'https://lh5.googleusercontent.com/d6_i0fRfFW6U2N4Bgr7b6duWKZezolnnk3utxlOdNfcCc1qvGuFRcG4P5maldpaKFS0x3g1d7fz0VdQ=w1366-h613'
            },
            {
              src: 'https://lh3.googleusercontent.com/ADJNWuQ4rPstlON52gIU1uZEEwX4BkLEPkrlQoW--Ngb1L_EUWoNXygS4DwHs-RTEruweJ38VBMGfTI=w1366-h613-rw'
            }
          ]
          // image: [
          //   {imageurl: 'https://i.ytimg.com/vi/XvICz-8xB1E/maxresdefault.jpg', id: '1232342skdhsas'},
          //   {imageurl: 'http://4.bp.blogspot.com/-A_tn57QXI4I/VON_nJKeAMI/AAAAAAAAASI/RUwfStETz1A/s1600/polpos.jpg', id: '1232342skdhsas'}
          // ]
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
