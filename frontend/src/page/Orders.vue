<template>
  <Wrapper ref="wrapper">
    <h1>
      Orders
    </h1>
    <v-data-table :headers="headersOrder" :items="orders" :items-per-page="10" class="elevation-1">
      <template v-slot:item.creationDate="{ item }">
        <span>{{ new Date(item.creationDate).toLocaleString() }}</span>
      </template>
      <template v-slot:item.total="{ item }">
        <span>{{ (item.total / 100).toFixed(2) + ' PLN' }}</span>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-btn @click="$router.push('/order/' + item.id)">
          Show
        </v-btn>
      </template>
    </v-data-table>
  </Wrapper>
</template>

<script>
import Wrapper from "./Wrapper";
import api from "../api";

export default {
  name: "HomePage",
  components: {Wrapper},
  data: function () {
    return {
      headersOrder: [
        {
          text: 'Order status',
          align: 'start',
          value: 'orderStatus',
          sortable: false
        },
        {
          text: 'Order date',
          align: 'start',
          value: 'creationDate',
          sortable: true
        },
        {
          text: 'Order total',
          align: 'start',
          value: 'total',
          sortable: false
        },
        {
          text: 'Actions',
          align: 'center',
          value: 'actions',
          sortable: false
        }
      ],
      orders: []
    };
  },
  mounted() {
    api.getAllUserOrders().then(response => {
      this.orders = response.data.data.orders;
    });
  },
  methods: {
  }
}
</script>
<style scoped>

</style>