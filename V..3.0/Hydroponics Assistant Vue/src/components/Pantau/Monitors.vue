<template>
    <v-container>
    <v-layout row wrap  v-for="monitor in monitors" :key="monitor.id" class="mb-2">
        <v-flex xs12>
        <v-card class="info">
        <v-container fluid>
        <v-layout row>
        <v-flex xs12 sm8 md9>
          <v-card-title primary-title>
                <h5 class="white--text mb-0">{{ monitor.key }}</h5>
                <v-spacer></v-spacer>
                <h5 class="white--text mb-0">{{ monitor.ec }}</h5>
                <v-spacer></v-spacer>
              </v-card-title>
              <v-card-actions>
              <v-btn flat :to="'/Monitor/' + monitor.id">
                <v-icon left>arrow_forward</v-icon>
                View Monitoring
              </v-btn>
              </v-card-actions>
        </v-flex>
        </v-layout>
        </v-container>
        </v-card>
        </v-flex>
    </v-layout>
    </v-container> 
</template>

<script>
export default {
  computed: {
    monitors () {
      return this.$store.getters.loadedMonitors
    }
  }
}
</script>
