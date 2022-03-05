import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        user: null,
        subscription: null,
        categories: []
    },
    mutations: {
        user(state, user){
            state.user = user;
        },
        no_user(state){
            state.user = null;
        }
    },
    getters: {
        getUser: state => state.user
    }
})