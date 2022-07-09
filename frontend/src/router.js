import Vue from 'vue'
import Router from 'vue-router'
import HomePage from './page/HomePage'
import Search from "./page/Search";
import api from "./api";
import store from './store'
import BookDetails from "./page/BookDetails";
import Register from "./page/Register";
import Login from "./page/Login";
import Cart from "./page/Cart";
import AdminPanel from "./page/AdminPanel";
import OrderDetails from "./page/OrderDetails";
import Orders from "./page/Orders";

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

        {path: '/admin', name: 'admin', component: AdminPanel, meta: {requiresAuth: true, adminOnly: true}},

        {path: '/order/:id', name: 'order', component: OrderDetails},

        {path: '/order', name: 'orders', component: Orders, meta: {requiresAuth: true}}

    ]
});

router.beforeEach((to, from, next) => {
    let requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    let adminOnly = to.matched.some(record => record.meta.adminOnly);
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
                    if (adminOnly && store.getters.getUser.type.toLowerCase() !== 'admin') {
                        next({
                            path: '/'
                        });
                    } else {
                        next();
                    }
                }
            })
            .catch(() => {
                store.commit("no_user")
                if (requiresAuth) {
                    next({
                        path: '/login?next=' + to.path
                    });
                } else {
                    if (adminOnly && store.getters.getUser.type.toLowerCase() !== 'admin') {
                        next({
                            path: '/'
                        });
                    } else {
                        next();
                    }
                }
            })
    } else {
        next();
    }
});

export default router;