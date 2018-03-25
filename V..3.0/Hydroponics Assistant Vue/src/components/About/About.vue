<template>
<v-container>
  <v-layout>
    <v-flex xs12 sm6 offset-sm3>
      <v-card>
        <v-card-media src="/static/logo.png" height="400px">
        </v-card-media>
        <v-card-title primary-title>
          <div>
            <h3 class="headline mb-12">Development Team</h3>
            <div>Dini Hamidin <br>Mohamad Nurkamal Fauzan</div>
            <div>Agung Suryana <br>Eki Kesuma Muhammad</div>Ilyas Yasin</div>
            <v-spacer></v-spacer>
          </div>
        </v-card-title>
      </v-card>
    </v-flex>
  </v-layout>
  <v-layout>
    <v-flex xs12 sm6 offset-sm3>
      <v-card>
        <v-card-media src="/static/barcode.jpeg" height="400px">
        </v-card-media>
        <v-card-title primary-title>
          <div> 
            <h3 class="headline mb-0">Barcode Scan Android</h3>
             <img src="/assets/aa.jpeg" height="500">
          </div>
        </v-card-title>
        <v-card-actions>
          <v-btn flat primary link large color="blue">
      <v-icon>android</v-icon>
    </v-btn><v-text>Download Application Hydroponics Assistant</v-text>
    <v-btn
      :loading="loading3"
      @click.native="loader = 'loading3'"
      :disabled="loading3"
      color="blue-grey"
      dark
      href="https://play.google.com/store/apps/details?id=com.kxland.hydrodonoremote"
    >
      Download
      <v-icon right dark>cloud_upload</v-icon>
    </v-btn>

        </v-card-actions>
      </v-card>
    </v-flex>
  </v-layout>
  </v-container>
  
</template>
