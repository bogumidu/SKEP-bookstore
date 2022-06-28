<template>
  <v-app :style="{background: $vuetify.theme.themes[theme].background}">
    <v-app-bar app flat fixed class="top-menu secondary">
      <v-toolbar-items>
        <v-btn text class="menu-btn"
               @click="$router.push('/')">
          <v-icon>mdi-home</v-icon>
        </v-btn>
        <v-menu top class="primary" :close-on-click="true">
          <template v-slot:activator="{on, attrs}">
            <v-btn text v-bind="attrs" v-on="on">
              Categories
              <v-icon>mdi-menu-down</v-icon>
            </v-btn>
          </template>
          <v-list class="secondary">
            <v-list-item v-for="(category, index) in categories" :key="index">
              <v-list-item-title>{{ category.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar-items>
      <v-toolbar-items>
        <v-text-field class="secondary pt-3 pl-4" single-line></v-text-field>
        <v-btn icon class="secondary">
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar-items>
      <v-spacer></v-spacer>
      <v-toolbar-items>
        <v-btn icon @click="$router.push('*')">
          <v-icon>mdi-cart</v-icon>
        </v-btn>
        <v-btn text class="menu-btn secondary"
               @click="$router.push('/register')">Register
        </v-btn>
        <v-btn text class="menu-btn"
               @click="$router.push('/login')">Login
        </v-btn>
      </v-toolbar-items>
    </v-app-bar>
    <v-main>
      <v-container v-if="error">
        <v-alert
            dense
            outlined
            type="error"
        >
          {{ error }}
        </v-alert>
      </v-container>
      <v-container>
        <slot/>
      </v-container>
      <v-footer
          class="font-weight-medium  secondary"
          style="margin-top: 100px"
      >
        <v-col
            class="text-center"
            cols="12"
        >
          {{ new Date().getFullYear() }} — <strong>Bogumił Sokołowski-Duda</strong>
        </v-col>
      </v-footer>
    </v-main>
  </v-app>
</template>

<style>
</style>

<script>

export default {
  components: {},
  name: 'Wrapper',
  data() {
    return {
      error: null,
      categories: [
        {title: 'Horror'},
        {title: 'Comedy'},
        {title: 'Drama'},
        {title: 'Action'},
      ],
    }
  },
  mounted() {
  },
  methods: {},
  computed: {
    theme() {
      return this.$vuetify.theme.dark ? 'dark' : 'light'
    },
  },
}
</script>