<template>
    <v-main>
      <v-container class="text-center" center fill-height>
        <v-card class="mx-auto text-center" outlined rounded="xl" style="padding: 1em 5em;">
          <v-card-text class="welcome">Create free account</v-card-text>
          <v-form style="padding: 1em 0">
            <v-card-text>
              <v-text-field type="text" v-model="email" placeholder="Email"></v-text-field>
              <v-text-field type="password" v-model="password" placeholder="Password"></v-text-field>
              <v-list-item>
                <v-list-item-content class="text-sm-left">
                  <v-list-item-subtitle>Password must contain:</v-list-item-subtitle>
                  <v-list-item-subtitle >
                     at least 8 characters
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    at least 1 lower and upper case letter
                  </v-list-item-subtitle>
                  <v-list-item-subtitle>
                    at least 1 number
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
              <v-text-field type="password" v-model="password1" placeholder="Confirm password"></v-text-field>
              <v-card-text class="text-sm-left text--secondary">By clicking Register you accept </v-card-text>
              <v-btn @click="register">Register</v-btn>
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

export default {
  components: {},
  name: 'App',
  data() {
    return {
      email: '',
      password: '',
      password1: ''
    }
  },
  methods: {
    register() {
      if (this.validateEmail(this.email) === true) {
        if (this.password === '') {
          window.alert('Password is missing')
        } else if (this.password !== this.password1) {
          window.alert("Password doesn't match confirmation")
        } else if (this.password.search(/[a-z]/) === -1 || this.password.search(/[A-Z]/) === -1) {
          window.alert('Password must contain at least 1 small and capital letter')
        } else if (this.password.search(/\d/) === -1) {
          window.alert('Password must contain at least 1 number')
        } else if (this.password === this.password1) {
          api.register(this.email, this.password).then(response => {
            this.$store.commit('user', response.data.data);
            this.$router.replace('/dashboard')
          }).catch(err => {
            window.alert(JSON.stringify(err.response.data));
          })
        }
      } else {
        window.alert("Invalid email address")
      }
    },
    validateEmail(email) {
      return /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(email);
    }
  }
}
</script>