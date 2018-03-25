<template>
  <v-app id="example-2" toolbar>
    <v-navigation-drawer absolute persistent light :mini-variant.sync="mini" v-model="drawer" overflow>
      <v-toolbar flat class="transparent">
        <v-list class="pa-0">
          <v-list-tile avatar>
            <v-list-tile-avatar>
              <img src="static/ic_hydro512px.png">
            </v-list-tile-avatar>
            <v-list-tile-content>
              <v-list-tile-title>Admin</v-list-tile-title>
            </v-list-tile-content>
            <v-list-tile-action>
              <v-btn icon @click.native.stop="mini = !mini">
                <v-icon>chevron_left</v-icon>
              </v-btn>
            </v-list-tile-action>
            <v-toolbar-items class="hidden-xs-only">
    </v-toolbar-items>	
          </v-list-tile>
        </v-list>
      </v-toolbar>
      <v-list class="pt-0" dense>
        <v-divider></v-divider>
        <v-list-tile v-for="item in menuItems" :key="item.title" :to="item.link">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar fixed class="primary" dark>
    <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
    <v-toolbar-title class="white--text">Hydroponics Real Time</v-toolbar-title>
    <v-spacer></v-spacer>
      <v-btn icon v-if="userIsAuthenticated" @click="onLogout"><v-icon dark>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <main>
    <router-view></router-view>
    <v-footer class="blue darken-4">
        <v-layout row wrap align-center>
          <v-flex xs12>
            <div class="white--text ml-1">
          <a class="white--text">NewBTeam</a>
            </div>
          </v-flex>
        </v-layout>
      </v-footer>
    </main>
  </v-app>
</template>
<script>
  export default {
    data () {
      return {
        sideNav: false,
        drawer: true,
        mini: false,
        right: null
      }
    },
    computed: {
      menuItems () {
        let menuItems = [
          // { icon: 'person', title: 'Sign up', link: '/signup' },
          { icon: 'lock_open', title: 'Sign in', link: '/signin' },
          { icon: 'info', title: 'About', link: '/About' }
        ]
        if (this.userIsAuthenticated) {
          menuItems = [
          { icon: 'gavel', title: 'Home Admin', link: '/homeadmin' },
          { icon: 'contacts', title: 'Pengguna', link: '/profile' },
          { icon: 'content_copy', title: ' Tampil Kendali', link: '/control' },
          { icon: 'add', title: 'Tambah Kendali', link: '/createcontrol' },
          { icon: 'featured_play_list', title: 'Perangkat', link: '/Perangkat' },
          { icon: 'lightbulb_outline', title: 'Notifikasi', link: '/ViewCloud' }
          ]
        }
        return menuItems
      },
      userIsAuthenticated () {
        return this.$store.getters.user !== null && this.$store.getters.user !== undefined
      }
    },
    methods: {
      onLogout () {
        this.$store.dispatch('logout')
        this.$router.push('/')
        alert('Logout Succes')
      }
    }
  }
</script>

<style lang="stylus">
  @import './stylus/main'
</style>
