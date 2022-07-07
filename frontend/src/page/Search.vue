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
                <v-btn color="deep-purple lighten-2 float-right" text>
                  <v-icon left>
                    mdi-cart
                  </v-icon>
                  Add to cart
                </v-btn>
                <v-btn color="deep-purple lighten-2 float-right" text @click="$router.push('/book/' + book.id)">
                  Details
                </v-btn>
              </v-card-actions>
            </v-col>
          </v-row>
        </v-card>
      </v-row>
    </v-col>
  </Wrapper>
</template>

<script>
import Wrapper from "./Wrapper";
import api from "../api";

export default {
  name: "Search",
  components: {Wrapper},
  data: function () {
    return {
      books: []
    };
  },
  mounted() {
    api.searchBooks(this.$route.params.query).then(response => {
      this.books = response.data.data.books;
    });
  },
  created() {
    this.$watch(() => this.$route.params.query, () => {
      api.searchBooks(this.$route.params.query).then(response => {
        this.books = response.data.data.books;
      });
    });
  }
}
</script>

<style scoped>

</style>