<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style>
</style>

<script>

import Cart from './cart.js'

export default {
  name: 'App',
  methods: {
    test() {
      Cart.getInstance().then(cart => {
        cart.addItem({
          id: 'uuid',
          quantity: 1,
          data: {
            name: 'test',
            price: 1
          }
        })
      })
      Cart.getInstance().then(cart => {
        console.log(cart.getItems());
      })
    }
  }
}
</script>