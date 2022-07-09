<template>
  <Wrapper ref="wrapper">
    <v-card class="mx-auto">
      <v-card-title>
        <v-col :cols="7" class="pa-4">
          <v-row class="text-h4">
            {{ order.id }}
          </v-row>
          <v-row>
            <h4>
              Created: {{ new Date(order.creationDate).toLocaleString() }}
            </h4>
          </v-row>
          <v-row>
            <h4>
              Status: {{ order.orderStatus }}
            </h4>
          </v-row>
          <v-row>
            <h4>
              Total: {{ (order.total / 100).toFixed(2) }} PLN
            </h4>
          </v-row>
            <v-row v-for="(book, index) in order.cart" v-bind:key="index">
              <v-card class="mx-auto" width="80%">
                <v-row>
                  <v-col cols="3" class="mx-2 my-2">
                    <v-img height="200" width="200" style="cursor: pointer"
                           :src="'https://picsum.photos/seed/' + encodeURIComponent(book.book.title) + '/200/300'"></v-img>
                  </v-col>
                  <v-col>
                    <v-card-title>{{ book.book.title }}</v-card-title>
                    <v-card-text>
                      <div class="text-subtitle-1">
                        {{ book.book.author.firstName + ' ' + book.book.author.lastName }}
                      </div>
                      {{ book.book.description }}
                    </v-card-text>
                    <v-card-title>
                      <div>
                        {{ (book.price / 100).toFixed(2) }} PLN
                      </div>
                    </v-card-title>
                  </v-col>
                  <v-col>
                    <v-card-actions class="justify-end">
                      <h4>
                        Quantity: {{ book.quantity }}
                      </h4>
                    </v-card-actions>
                  </v-col>
                </v-row>
              </v-card>
            </v-row>
        </v-col>
      </v-card-title>

    </v-card>
  </Wrapper>
</template>

<script>
import api from "../api";
import Wrapper from "./Wrapper";

export default {
  name: "OrderDetails",
  components: {Wrapper},
  data: function () {
    return {
      order: {}
    };
  },
  mounted() {
    api.getOrder(this.$route.params.id).then(response => {
      this.order = response.data.data;
    });
  },
}
</script>

<style scoped>

</style>