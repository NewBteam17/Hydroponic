  <template>
<v-dialog width="350px" persistent v-model="editDialog">
<v-btn fab accent slot="activator">
<v-icon>edit</v-icon>
</v-btn>
<v-card>
<v-container>{{idc}}
<v-layout row wrap>
<v-flex xs12>
<v-card-title></v-card-title>
</v-flex>
</v-layout>
<v-divider></v-divider>
<v-layout row wrap>
<v-flex xs12>
<v-card-text>
<v-select
                id="diffTime"
                name="diffTime"
                  label="Waktu dalam hari"
                  required
                  :items="['3','4','5','7']"
                  v-model="editTitle"
                ></v-select>
                <v-select
                id="manual"
                name="manual"
                  label="Control Aruino"
                  required
                  :items="['on','off']"
                  v-model="editlevel"
                ></v-select>
</v-card-text>
</v-flex>
</v-layout>
<v-divider></v-divider>
<v-layout row wrap>
<v-flex xs12>
<v-card-actions>
<v-btn flat class="blue--text darken-1" @click="editDialog = false">Close</v-btn>
<v-btn flat class="blue--text darken-1" @click="onSaveChange">Save</v-btn>
</v-card-actions>
</v-flex>
</v-layout>
</v-container>
</v-card>
</v-dialog>
</template>

<script>
 import Vue from 'vue'
 import firebase from '../../firebase.js'
 import VueFire from 'vuefire'

 Vue.use(VueFire)

 export default {
   props: ['control', 'idc'],
   data () {
     // console.log(this.control[0]['.value'])
     return {
       editDialog: false,
       editTitle: this.control[0]['.value'], // this.control.diffTime,
       editlevel: this.control[3]['.value'] // this.control.manual
     }
   },
   methods: {
     onSaveChange () {
       if (this.editTitle.trim() === '' || this.editlevel.trim() === '') {
         return
       }
       this.editDialog = false
       firebase.database().ref('kendali').child(this.idc).update({
         diffTime: this.editTitle,
         manual: this.editlevel
       })
     },
     checkhere: function (varName) {
       switch (varName) {
         case '3':
           varName = '259200'
           alert('3 hari')
           break
         case '4':
           varName = '345600'
           alert('4 hari')
           break
         case '2':
           varName = '172880'
           alert('2 hari')
           break
         case '1':
           varName = '16400'
           alert('1 hari')
           break
       }
     }
   }
 }
</script>
