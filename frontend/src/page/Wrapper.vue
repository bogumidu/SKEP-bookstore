<template>
  <v-app :style="{background: $vuetify.theme.themes[theme].background}">
    <v-app-bar app flat fixed class="top-menu secondary">
      <v-toolbar-items>
        <v-btn text class="menu-btn"
               @click="$router.push('/')">
          <v-icon>mdi-home</v-icon>
        </v-btn>
      </v-toolbar-items>
      <v-toolbar-items>
        <v-text-field class="secondary pt-3 pl-4" single-line v-model="query" label="Search"></v-text-field>
        <v-btn icon class="secondary" @click="$router.push({name: 'search', params: {query: createQuery(query)}})">
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar-items>
      <v-spacer></v-spacer>
      <v-btn icon @click="$router.push('/cart')">
        <v-icon>mdi-cart</v-icon>
      </v-btn>
      <v-toolbar-items v-if="!authenticated">
        <v-btn text class="menu-btn" @click="$router.push('/register')">
          Register
        </v-btn>
        <v-btn text class="menu-btn" @click="$router.push('/login')">
          Login
        </v-btn>
      </v-toolbar-items>
      <v-toolbar-items v-else-if="authenticated">
        <v-btn text class="menu-btn secondary" @click="$router.push('/order')">
          My orders
        </v-btn>
        <v-btn text class="menu-btn" @click="logout">
          Logout
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>
    <v-main>
      <v-container v-if="error">
        <v-alert dense outlined type="error">
          {{ error }}
        </v-alert>
      </v-container>
      <v-container>
        <slot/>
      </v-container>
      <v-footer class="font-weight-medium  secondary" style="margin-top: 100px">
        <v-col class="text-center" cols="12">
          {{ new Date().getFullYear() }} — <strong>Bogumił Sokołowski-Duda</strong>
        </v-col>
      </v-footer>
    </v-main>
  </v-app>
</template>

<style>
</style>

<script>
import api from "../api";
import Lucene from "../lucene";

export default {
  components: {},
  name: 'Wrapper',
  data() {
    return {
      authenticated: false,
      query: '',
      error: null
    }
  },
  mounted() {
    this.isAuthenticated();
  },
  methods: {
    isAuthenticated() {
      this.authenticated = !!this.$store.getters.getUser;
    },
    logout() {
      this.authenticated = false;
      api.logout();
      if (this.$route.fullPath !== '/') {
        this.$router.push('/');
      }
      this.$store.commit('no_user');
    },
    createQuery(query) {
      return Lucene.createQueryAll(query);
    }
  },
  computed: {
    theme() {
      return this.$vuetify.theme.dark ? 'dark' : 'light'
    },
  },
}
</script>