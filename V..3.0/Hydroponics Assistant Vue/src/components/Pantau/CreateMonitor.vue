<template>
<v-container>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<h4 class="primary--text">Create Monitoring Hidroponik</h4>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12>
<form @submit.prevent="onCreateMonitor">
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="ec" label="ec" id="ec" v-model="ec" required></v-text-field>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="ketinggianWadah" label="ketinggianWadah" id="ketinggianWadah"  v-model="ketinggianWadah" required></v-text-field>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="ph" label="Sensor PH" id="ph"  v-model="ph" required></v-text-field>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="pupukA" label="Sensor PH" id="pupukA"  v-model="pupukA"  required></v-text-field>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="pupukB" label="Sensor PH" id="pupukB"  v-model="pupukB" required></v-text-field>
</v-flex>
</v-layout>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-text-field name="suhu" label="Suhu" id="suhu"  v-model="suhu" required></v-text-field>
</v-flex>
</v-layout>
<v-spacer></v-spacer>
<v-layout row>
<v-flex xs12 sm6 offset-sm3>
<v-btn class="primary" :disabled="!formIsValid" type="submit"> Create Monitor</v-btn>
</v-flex>
</v-layout>
</form>
</v-flex>
</v-layout>
</v-container>
</template>

<script>
export default {
  data () {
    return {
      ec: '',
      ketinggianWadah: '',
      ph: '',
      pupukA: '',
      pupukB: '',
      suhu: ''
    }
  },
  computed: {
    formIsValid () {
      return this.ec !== '' &&
      this.ketinggianWadah !== '' &&
      this.ph !== '' &&
      this.pupukA !== '' &&
      this.pupukB !== '' &&
      this.suhu !== ''
    },
    submitTableDateTime () {
      const date = new Date(this.date)
      if (typeof this.time === 'string') {
        const hours = this.time.match(/^(\d+)/)[1]
        const minutes = this.time.match(/:(\d+)/)[1]
        date.setHours(hours)
        date.setMinutes(minutes)
      } else {
        date.setHours(this.time.getHours())
        date.setMinutes(this.time.getMinutes())
      }
      console.log(date)
      return date
    }
  },
  methods: {
    onCreateMonitor () {
      if (!this.formIsValid) {
        return
      }
      const monitorData = {
        ec: this.ec,
        ketinggianWadah: this.ketinggianWadah,
        ph: this.ph,
        pupukA: this.pupukA,
        pupukB: this.pupukB,
        suhu: this.suhu
      }
      this.$store.dispatch('createMonitor', monitorData)
      this.$router.push('/pantau')
    },
    onPickFile () {
      this.$refs.fileInput.click()
    },
    onFilePicked () {
      const files = event.target.files
      let filename = files[0].name
      if (filename.lastIndexOf('.') <= 0) {
        return alert('please add your file')
      }
      const fileReader = new FileReader()
      fileReader.addEventListener('load', () => {
        this.imageUrl = fileReader.result
      })
      fileReader.readAsDataURL(files[0])
      this.image = files[0]
    }
  }
}
</script>
