import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/page/HomePage'
import Search from "@/page/Search";
import api from "@/api";
import store from '@/store'
import BookDetails from "@/page/BookDetails";
import Register from "@/page/Register";
import Login from "@/page/Login";
import Cart from "./page/Cart";

Vue.use(Router);

const router = new Router({
    mode: 'history',
    routes: [
        {path: '/', component: HomePage},

        {path: '*', redirect: '/'},

        {path: '/register', name: 'register', component: Register},

        {path: '/login', name: 'login', component: Login},

        {path: '/search/:query', name: 'search', component: Search},

        {path: '/book/:id', name: 'book', component: BookDetails},

        {path: '/cart', name: 'cart', component: Cart, meta: {requiresAuth: true}},


    ]
});

router.beforeEach((to, from, next) => {
    let requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    if (!store.getters.getUser) {
        api.getUser()
            .then(response => {
                if (response.data.errorMessage) {
                    store.commit("no_user")
                    if (requiresAuth) {
                        next({
                            path: '/login?next=' + to.path
                        });
                    } else {
                        next();
                    }
                } else {
                    store.commit("user", response.data.data)
                    next();
                }
            })
            .catch(() => {
                store.commit("no_user")
                if (requiresAuth) {
                    next({
                        path: '/login?next=' + to.path
                    });
                } else {
                    next();
                }
            })
    } else {
        next();
    }
});

export default router;