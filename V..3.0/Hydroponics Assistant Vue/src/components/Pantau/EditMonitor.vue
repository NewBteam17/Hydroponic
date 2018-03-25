  <template>
<v-dialog width="350px" persistent v-model="editDialog">
<v-btn fab accent slot="activator">
<v-icon>edit</v-icon>
</v-btn>
<v-card>
<v-container>
<v-layout row wrap>
<v-flex xs12>
<v-card-title>Edit Monitors</v-card-title>
</v-flex>
</v-layout>
<v-divider></v-divider>
<v-layout row wrap>
<v-flex xs12>
<v-card-text>
<v-text-field 
name="idarduino" 
label="idarduino" 
id="idarduino" 
v-model="editTitle" ></v-text-field>
<v-text-field 
name="statusPupuk" 
label="statusPupuk" 
id="statusPupuk" 
v-model="editDescription" ></v-text-field>
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
 export default {
   props: ['monitor'],
   data () {
     return {
       editDialog: false,
       editTitle: this.monitor.idarduino,
       editDescription: this.monitor.statusPupuk
     }
   },
   methods: {
     onSaveChange () {
       if (this.editTitle.trim() === '' || this.editDescription.trim() === '') {
         return
       }
       this.editDialog = false
       this.$store.dispatch('updateDataMonitor', {
         id: this.monitor.id,
         idarduino: this.editTitle,
         statusPupuk: this.editDescription
       })
     }
   }
 }
</script>
