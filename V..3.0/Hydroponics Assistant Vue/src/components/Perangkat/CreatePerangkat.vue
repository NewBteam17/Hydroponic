  <template>
  <v-layout row justify-center>
    <v-dialog v-model="dialog" persistent width="45%" >
      <v-btn dark slot="activator">Tambah Perangkat</v-btn>
      <v-card>
        <v-card-title>
          <span class="headline">Perangkat baru</span>
        </v-card-title>
        <v-card-text>
          <v-container grid>
            <v-layout wrap>
            <v-flex xs12>
                <v-text-field id="idarduino" label="ID Arduino" name="idarduino" v-model="idarduino" required></v-text-field>
              </v-flex>
              <v-flex xs12>
                <v-select
                id="aktif"
                name="aktif"
                  label="Status Perangkat"
                  required
                  :items="['on','off']"
                  v-model="aktif"
                ></v-select>
              </v-flex>
              <v-flex xs10>
<h4>Date</h4>
 <v-date-picker v-model="tglprod" horizontal></v-date-picker>
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
  data () {
    return {
      dialog: false,
      aktif: '',
      tglprod: new Date(),
      idarduino: ''
    }
  },
  computed: {
    formIsValid () {
      return this.aktif !== '' &&
      this.idarduino !== ''
    }
  },
  submitTableDateTime () {
    const tglprod = new Date(this.tglprod)
    if (typeof this.time === 'string') {
      const hours = this.time.match(/^(\d+)/)[1]
      tglprod.setHours(hours)
    }
    console.log(tglprod)
    return tglprod
  },
  methods: {
    onCreateUser () {
      if (!this.formIsValid) {
        return
      }
      const perangkatData = {
        aktif: this.aktif,
        tglprod: this.tglprod
      }
      firebase.database().ref('perangkat/' + this.idarduino).set(perangkatData)
      this.dialog = false
      this.$router.push('/Perangkat')
    }
  }
}
</script>
