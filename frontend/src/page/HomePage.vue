<template>
  <Wrapper ref="wrapper">
    <v-row>
      <v-col cols="12">
        <h2>Horror</h2>
      </v-col>
    </v-row>
    <v-row>
      <v-col v-for="(book, index) in horror" v-bind:key="index" cols="2">
        <v-card
            class="mx-auto"
        >
          <v-img
              height="400"
              style="cursor: pointer"
              :src="'https://picsum.photos/seed/' + encodeURIComponent(book.title) + '/200/300'"
              v-on:click="$router.push('/book/' + book.id)"
          >
            <div>
              <v-card-title
                  style="position: absolute;bottom: 0; width: 100%; background-color: rgba(0,0,0,0.41);color: white">
                {{ book.title }}
              </v-card-title>
            </div>
          </v-img>
          <v-card-text>
            <div class="text-subtitle-1">
              {{ book.author.firstName + ' ' + book.author.lastName }}
            </div>
            {{ book.description }}
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-btn
                color="deep-purple lighten-2 float-right"
                text
                @click="addToCart(book)"
            >
              <v-icon left>
                mdi-cart
              </v-icon>
              Add to cart
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col cols="2">
        <v-card
            class="mx-auto fill-height"
        >
          <v-card-text class="text-center" style="position:relative; top:45%">
            <v-btn class="mx-auto" color="deep-purple lighten-2" style="color:white">Show more</v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </Wrapper>
</template>

<script>
import Wrapper from "./Wrapper";
import api from "../api";
import cart from "../cart";

export default {
  name: "HomePage",
  components: {Wrapper},
  data: function () {
    return {
      horror: [],
      comedy: [],
      drama: [],
      action: [],
    };
  },
  mounted() {
    api.getBooksByGenre("horror").then(response => {
      this.horror = response.data.data.books.slice(0, 5);
      return api.getBooksByGenre("comedy");
    }).then(response => {
      this.comedy = response.data.data.books.slice(0, 5);
      return api.getBooksByGenre("drama");
    }).then(response => {
      this.drama = response.data.data.books.slice(0, 5);
      return api.getBooksByGenre("action");
    }).then(response => {
      this.action = response.data.data.books.slice(0, 5);
    });
  },
  methods: {
    addToCart(book) {
      cart.getInstance().then(cart => {
        console.log(book);
        cart.addItem(book);
      });
    }
  },
}
</script>

<style scoped>

</style>