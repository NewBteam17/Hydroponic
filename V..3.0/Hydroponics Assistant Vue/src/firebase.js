// Konfigurasi Console firebase 
import * as firebase from 'firebase'

let config = {
  apiKey: '',
  authDomain: '',
  databaseURL: '',
  projectId: '',
  storageBucket: '',
  messagingSenderId: ''
}

export default !firebase.apps.length ? firebase.initializeApp(config) : firebase.app()

