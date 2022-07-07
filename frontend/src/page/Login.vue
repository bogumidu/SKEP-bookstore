<template>
    <v-main style="margin-top: 10%">
      <v-container fluid fill-height>
        <v-card class="mx-auto text-center c-card" outlined rounded="xl" style="padding: 1em 5em;">
          <v-card-text class="welcome">Welcome!</v-card-text>
          <v-form style="padding: 1em 0" v-on:submit.prevent="login">
            <v-card-text>
              <v-text-field type="text" v-model="email" placeholder="Email"></v-text-field>
              <v-text-field type="password" v-model="password" placeholder="Password"></v-text-field>
              <v-card-text class="text-sm-left text--secondary"> Dont have account? <a v-on:click="$router.replace('/register')">Create one here!</a> </v-card-text>
              <v-btn color="primary" @click="login">Login</v-btn>
            </v-card-text>
          </v-form>
        </v-card>
      </v-container>
    </v-main>
</template>

<style>
</style>

<script>

import api from "@/api";
import store from "@/store";

export default {
  components: {},
  name: 'App',
  data() {
    return {
      email: '',
      password: ''
    }
  },
  mounted() {
    api.getUser()
        .then(response => {
          store.commit("user", response.data)
          if (this.$route.params.next) {
            this.$router.replace(this.$route.params.next)
          } else {
            this.$router.replace("/dashboard")
          }
        })
        .catch(() => {
          store.commit("no_user");
        })
  },
  methods: {
    login() {
      api.login(this.email, this.password).then(response => {
        this.$store.commit("user", response.data.data)
        this.$router.replace('/');
      }).catch(err => {
        window.alert(JSON.stringify(err.response.data));

      })
    }
  }
}
</script>