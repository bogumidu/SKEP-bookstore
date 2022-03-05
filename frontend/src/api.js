import axios from 'axios'

const AXIOS = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
    timeout: 20000
});

export default {
    login(tokenID) {
        return AXIOS.post(`/login`, JSON.stringify({
            'tokenID': tokenID
        }), {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    logout() {
        return AXIOS.post(`/logout`, null, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    }
}