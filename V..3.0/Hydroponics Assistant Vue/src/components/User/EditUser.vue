  <template>
<v-dialog width="350px" persistent v-model="editDialog">
<v-btn accent slot="activator">
<v-icon>edit</v-icon>
</v-btn>
<v-card>
<v-container>
<v-layout row wrap>
<v-flex xs12>
<v-card-title>Edit Users</v-card-title>
</v-flex>
</v-layout>
<v-divider></v-divider>
<v-layout row wrap>
<v-flex xs12>
<v-card-text>
<v-text-field 
name="email" 
label="email" 
id="email" 
v-model="editEmail" ></v-text-field>
<v-text-field 
name="level" 
label="level" 
id="level" 
v-model="editlevel" ></v-text-field>
<v-text-field 
name="idarduino" 
label="idarduino" 
id="idarduino" 
v-model="editIdArduino" ></v-text-field>
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
   props: ['users', 'idu'],
   data () {
     // let list = []
     // console.log(this.users.idarduino.indexOf(','))
     // console.log(this.users.idarduino)
     /*
     if (this.users.idarduino.indexOf(',') === -1) {
       list = this.users.idarduino
     } else {
       list = this.users.idarduino.split(',')
     }
     this.users.idarduino = list
     */
     return {
       editDialog: false,
       editlevel: this.users[2]['.value'], // this.users.level,
       editEmail: this.users[0]['.value'], // this.users.email,
       editIdArduino: this.users[1]['.value'] // this.users.idarduino
     }
   },
   methods: {
     onSaveChange () {
       if (this.editlevel.trim() === '' || this.editEmail.trim() === '') {
         return
       }
       this.editDialog = false
       let list = []
       if (this.editIdArduino.indexOf(',') === -1) {
         if (this.editIdArduino[0].length === 1) {
           list = [this.editIdArduino]
         } else {
           list = this.editIdArduino
         }
       } else {
         list = this.editIdArduino.split(',')
       }
       this.editIdArduino = list
       console.log(list)
       this.users.idarduino = list
       // simpan
       // console.log('iniid' + this.idu)
       firebase.database().ref('pengguna').child(this.idu).update({
         email: this.editEmail,
         level: this.editlevel,
         idarduino: this.editIdArduino
       })
       alert('Success di Ubah')
     }
   }
 }
</script>
