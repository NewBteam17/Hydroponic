  <template>
<v-dialog width="350px" persistent v-model="editDialog">
<v-btn large info accent slot="activator">
<v-icon>edit</v-icon>
Time
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
<v-time-picker v-model="editTime" style="width: 100%" actions format="24hr">
<template scope="{ save, cancel }">
<v-btn class="blue--text" flat @click.native="editDialog = false">Close</v-btn>
<v-btn class="blue--text" flat @click.native="onSaveDialogs">Save</v-btn>
</template>
</v-time-picker>
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
      editTime: null,
      editDialog: false
    }
  },
  methods: {
    onSaveDialogs () {
      const newTime = new Date(this.monitor.date)
      const hours = this.editTime.match(/^(\d+)/)[1]
      const minutes = this.editTime.match(/:(\d+)/)[1]
      newTime.setHours(hours)
      newTime.setMinutes(minutes)
      this.$store.dispatch('updateDataMonitor', {
        id: this.monitor.id,
        tanggal: newTime
      })
    }
  },
  created () {
    this.editTime = new Date(this.monitor.tanggal).toTimeString()
  }
}
</script>
