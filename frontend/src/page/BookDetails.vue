<template>
  <Wrapper ref="wrapper">
    <v-card class="mx-auto">
      <v-card-title>
        <v-col :cols="3">
          <v-img
              height="700"
              width="460"
              style="cursor: pointer"
              :src="'https://picsum.photos/seed/' + encodeURIComponent(book.title) + '/200/300'"
          >
          </v-img>
        </v-col>
        <v-col :cols="7" class="pa-4">
          <v-row class="text-h2">
            {{ book.title }}
          </v-row>
          <v-row>
            <v-card-subtitle class="text-h4">
            {{ book.author.firstName + ' ' + book.author.lastName }}
            </v-card-subtitle>
          </v-row>
          <v-row>
            <v-card-text>
              {{ book.description }}
              Morbi mattis ullamcorper velit. Donec orci lectus, aliquam ut, faucibus non, euismod id, nulla. Fusce convallis metus id felis luctus adipiscing. Aenean massa. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nulla consequat massa quis enim. Praesent venenatis metus at tortor pulvinar varius. Donec venenatis vulputate lorem. Phasellus accumsan cursus velit. Pellentesque ut neque.
            </v-card-text>
          </v-row>
          <v-row>
            <v-card-actions class="justify-end">
              <v-card-subtitle class="text-h4">
                {{ (book.price / 100).toFixed(2) }} PLN
              </v-card-subtitle>
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
          </v-row>
        </v-col>
      </v-card-title>

    </v-card>
  </Wrapper>
</template>

<script>
import api from "../api";
import Wrapper from "./Wrapper";
import cart from "../cart";

export default {
  name: "BookDetails",
  components: {Wrapper},
  data: function () {
    return {
      book: {}
    };
  },
  mounted() {
    api.getBook(this.$route.params.id).then(response => {
      this.book = response.data.data;
    });
  },
  methods: {
    addToCart(book) {
      cart.getInstance().then(cart => {
        console.log(book);
        cart.addItem(book);
      });
    }
  }
}
</script>

<style scoped>

</style>