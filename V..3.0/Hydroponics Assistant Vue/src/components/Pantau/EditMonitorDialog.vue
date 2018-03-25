  <template>
<v-dialog width="150px" persistent v-model="editDialog">
<v-btn large info accent slot="activator">
<v-icon>edit</v-icon>
Date
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
<v-date-picker v-model="editDate" style="width: 100%" actions>
<template scope="{ save, cancel }">
<v-btn class="blue--text" flat @click.native="editDialog = false">Close</v-btn>
<v-btn class="blue--text" flat @click.native="onSaveDialogs">Save</v-btn>
</template>
</v-date-picker>
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
      editDate: null,
      editDialog: false
    }
  },
  methods: {
    onSaveDialogs () {
      const newDate = new Date(this.monitor.date)
      const newDay = new Date(this.editDate).getUTCDate()
      const newMonth = new Date(this.editDate).getUTCMonth()
      const newYear = new Date(this.editDate).getUTCFullYear()
      newDate.setUTCDate(newDay)
      newDate.setUTCMonth(newMonth)
      newDate.setUTCFullYear(newYear)
      this.$store.dispatch('updateDataMonitor', {
        id: this.monitor.id,
        tanggal: newDate
      })
    }
  },
  created () {
    this.editDate = new Date(this.monitor.tanggal)
  }
}
</script>
