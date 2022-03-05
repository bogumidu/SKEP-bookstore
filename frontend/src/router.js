import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/page/HomePage'
import Login from "@/page/Login";
import api from "@/api";
import store from '@/store'

Vue.use(Router);

const router = new Router({
    mode: 'history',
    routes: [
        {path: '/', component: HomePage},

        {path: '*', redirect: '/'}
    ]
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        api.getUser()
            .then(response => {
                if (response.data.errorMessage) {
                    store.commit("no_user")
                    next({
                        path: '/login?next=' + to.path
                    });
                } else {
                    store.commit("user", response.data)
                    next();
                }
            })
            .catch(() => {
                store.commit("no_user")
                next({
                    path: '/login?next=' + to.path
                });
            })
    } else {
        next();
    }
});

export default router;