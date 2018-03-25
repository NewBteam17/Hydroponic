<template>
<v-container>
  <v-data-table
      v-bind:headers="headers"
      :items="control"
      class="elevation-1"
    >
    <template slot="headerCell" scope="props">
      <span v-tooltip:bottom="{ 'html': props.header.text }">
        {{ props.header.text }}
      </span>
    </template>
    <template slot="items" scope="props">
    <td>{{ props.item.id }}</td>
      <td>{{ props.item.diffTime }}</td>
      <td class="text-xs-right">{{ props.item.lastOn }}</td>
      <td class="text-xs-right">{{ props.item.manual }}</td>
      <td class="text-xs-right">{{ props.item.status }}</td>
      <v-btn :to="'/controls/' + props.item.id">View</v-btn>
    </template>
  </v-data-table>
  </v-container>
</template>
<script>
  export default {
    data () {
      return {
        headers: [
          {
            text: 'Control Arduino',
            align: 'left',
            sortable: false,
            value: 'name'
          },
          { text: ' DiffTime', value: 'diffTime' },
          { text: 'Last ON', value: 'lastOn' },
          { text: 'Manual', value: 'manual' },
          { text: 'Status', value: 'status' },
          { text: 'Action', value: 'Action' }
        ],
        items: [
          {
            value: false,
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            sodium: 87,
            calcium: '14%',
            iron: '1%'
          }
        ]
      }
    },
    computed: {
      control () {
        return this.$store.getters.loadedControls
      },
      loading () {
        return this.$store.getters.loading
      },
      items () {
        let items = [
          { text: 'face', title: 'Sign up', link: '/signup' },
          { text: 'lock_open', title: 'Sign in', link: '/signin' }
        ]
        return items
      }
    }
  }
</script>
