<template>
  <Wrapper ref="wrapper">
    <v-col>
      <v-row v-for="(book, index) in books" v-bind:key="index">
        <v-card class="mx-auto" width="80%">
          <v-row>
            <v-col cols="3" class="mx-2 my-2">
              <v-img height="200" width="200" style="cursor: pointer"
                     :src="'https://picsum.photos/seed/' + encodeURIComponent(book.title) + '/200/300'"></v-img>
            </v-col>
            <v-col>
              <v-card-title>{{ book.title }}</v-card-title>
              <v-card-text>
                <div class="text-subtitle-1">
                  {{ book.author.firstName + ' ' + book.author.lastName }}
                </div>
                {{ book.description }}
              </v-card-text>
              <v-card-title>
                <div>
                  {{ (book.price / 100).toFixed(2) }} PLN
                </div>
              </v-card-title>
            </v-col>
            <v-col>
              <v-card-actions class="justify-end">
                <v-btn color="deep-purple lighten-2 float-right" @click="removeBook(book)">
                  Remove from cart
                </v-btn>
              </v-card-actions>
              <v-card-actions class="justify-end">
                <v-btn :disabled="book.quantity === 1" @click="decrementQ(book)">
                  -
                </v-btn>
                  <h4 class="px-4">
                    {{ book.quantity }}
                  </h4>
                <v-btn @click="incrementQ(book)">
                  +
                </v-btn>
              </v-card-actions>
            </v-col>
          </v-row>
        </v-card>
      </v-row>
      <v-row justify="center" class="py-4">
        <template v-if="books.length > 0">
          <v-btn color="lighten-2" @click="clearCart">
            Clear cart
          </v-btn>
          <v-spacer></v-spacer>
        <v-btn color="deep-purple lighten-2" @click="checkout">
          Checkout
        </v-btn>
        </template>
        <h1 v-else>
          Cart is empty
        </h1>
      </v-row>
    </v-col>
  </Wrapper>
</template>

<script>
import Wrapper from "./Wrapper";
import cart from "../cart";
import Cart from "../cart";
import api from "../api";

export default {
  name: "Cart",
  components: {Wrapper},
  data: function () {
    return {
      books: []
    };
  },
  mounted() {
    cart.getInstance().then(cart => {
      this.books = cart.getItems();
    });
  },
  methods: {
    checkout: function () {
      Cart.getInstance().then(cart => {
        let modifiedCart = cart.getItems().map(book => {
          return {
            book: {
              id: book.id
            },
            quantity: book.quantity
          }
        });
        return api.createOrder(modifiedCart);
      }).then(response => {
        Cart.clear();
        if (response.data.success) {
          this.$router.push("/order/" + response.data.data.id);
        } else {
          this.$refs.wrapper.error = response.data.errorMessage;
        }
      });
    },
    incrementQ: function (book) {
      Cart.getInstance().then(cart => {
        cart.addItem(book);
        this.books = cart.getItems();
      });
    },
    decrementQ: function (book) {
      Cart.getInstance().then(cart => {
        cart.removeOne(book);
        this.books = cart.getItems();
      });
    },
    removeBook: function (book) {
      Cart.getInstance().then(cart => {
        cart.remove(book);
        this.books = cart.getItems();
      });
    },
    clearCart: function () {
      Cart.clear();
      this.books = [];
    }
  }
}
</script>

<style scoped>

</style>