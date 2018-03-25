  import Vue from 'vue'
  import Vuex from 'vuex'
  // import firebase from 'firebase'
  import firebase from '../firebase.js'

  Vue.use(Vuex)

  export const store = new Vuex.Store({
    state: {
      loadedAbc: [
        {
          id: 'pppppppppppppppppppppp'
        }
      ],
      user: null,
      loading: false,
      error: null
    },
    mutations: {
      setloadAbc (state, payload) {
      // setloadAbc (state, payload) {
        // console.log('payload dari mutation ' + payload)
        state.loadedAbc = payload
        // console.log('getters dari mutation ' + getters)
        // getters.kkk
      },
      setUser (state, payload) {
        state.user = payload
      },
      setLoading (state, payload) {
        state.loading = payload
      },
      setError (state, payload) {
        state.error = payload
      },
      clearError (state, payload) {
        state.error = null
      }
    },
    actions: {
      // abc ({commit}) {
      abc ({commit, getters}, payload) {
        let id = payload.id
        // id = 'AR01'
        commit('setLoading', true) // panggil mutation
        firebase.database().ref('pantau/' + id).once('value')
          .then((data) => {
            const control = []
            const obj = data.val()
            for (let key in obj) {
              control.push({
                tgl: key,
                ph: obj[key].ph,
                ec: obj[key].ec,
                suhu: obj[key].suhu
                // aktif: obj[key].aktif,
                // tglprod: obj[key].tglprod
              })
            }
            commit('setloadAbc', control) // panggil mutation
            // getters.kkk
            // commit('setloadAbc', control)
            commit('setLoading', false) // panggil mutation
          })
          .catch((error) => {
            console.log(error)
            // commit('setLoading', true)
          })
      },
      signUserUp ({commit}, payload) {
        commit('setLoading', true)
        commit('setError')
        firebase.auth().createUserWithEmailAndPassword(payload.email, payload.password)
          .then(
            user => {
              commit('setLoading', false)
              const newUser = {
                id: user.uid,
                email: payload.email,
                registerMonitors: []
              }
              commit('setUser', newUser)
            }
          )
          .catch(
            error => {
              console.log(error)
              commit('setLoading', false)
              commit('setError', error)
            }
          )
      },
      signUserIn ({commit}, payload) {
        commit('setLoading', true)
        commit('setError')
        firebase.auth().signInWithEmailAndPassword(payload.email, payload.password)
        .then(
          user => {
            commit('setLoading', false)
            const newUser = {
              id: user.uid,
              registerMonitors: []
            }
            commit('setUser', newUser)
          }
        )
        .catch(
            error => {
              commit('setLoading', true)
              commit('setError')
              console.log(error)
            }
          )
      },
      autoSignIn ({commit}, payload) {
        firebase.database().ref('pengguna').orderByChild('email').equalTo(payload.email).once('value')
        .then((data) => {
          let users = []
          const obj = data.val()
          for (let key in obj) {
            if (obj[key].email.trim() === payload.email.trim() && obj[key].level.trim() === 'admin') {
              users.push({
                id: key,
                email: obj[key].email,
                idarduino: obj[key].idarduino,
                level: obj[key].level
              })
              commit('setUser', users)
            } else {
              firebase.auth().signOut()
              commit('setUser', null)
            }
          }
        })
      },
      logout ({commit}) {
        firebase.auth().signOut()
        commit('setUser', null)
      },
      clearError ({commit}) {
        commit('clearError')
      }
    },
    getters: {
      qqq (state) {
        return firebase.database().ref('pengguna').once('value')
      },
      kkk (state, getters) {
        return state.loadedAbc
      },
      user (state) {
        return state.user
      },
      error (state) {
        return state.error
      },
      loading (state) {
        return state.loading
      }
    }
  })
