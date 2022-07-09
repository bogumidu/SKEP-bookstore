import axios from 'axios'

const AXIOS = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
    timeout: 20000
});

export default {
    // Authorization
    login(username, password) {
        return AXIOS.post(`/auth/login`, JSON.stringify({
            'username': username,
            'password': password
        }), {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    register(username, password) {
        return AXIOS.post(`/auth/register`, JSON.stringify({
            'username': username,
            'password': password
        }), {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    logout() {
        return AXIOS.post(`auth/logout`, null, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getUser() {
        return AXIOS.get(`auth/user`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    // Author
    createAuthor(firstName, lastName) {
        return AXIOS.post(`/author`, JSON.stringify({
            'firstName': firstName,
            'lastName': lastName
        }), {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getAuthor(id) {
        return AXIOS.get(`/author/${id}`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getAuthors() {
        return AXIOS.get(`/author`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    // Books
    createBook(title, description, genre, price, authorId) {
        return AXIOS.post(`/book`, JSON.stringify({
            'title': title,
            'description': description,
            'genre': genre,
            'price': price,
            'author': {
                'id': authorId
            }
        }), {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    updateBook(id, title, description, price, genre, cover, authorId) {
        return AXIOS.put(`/book/${id}`, JSON.stringify({
            'title': title,
            'description': description,
            'genre': genre,
            'cover': cover,
            'price': price,
            'author': {
                'id': authorId
            }
        }), {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    updateBookPrice(id, price) {
        return AXIOS.patch(`/book/${id}`, JSON.stringify({
            'price': price
        }), {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}

        })
    },
    getBook(id) {
        return AXIOS.get(`/book/${id}`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getBooksByIds(ids) {
        if (ids.length === 0) {
            return Promise.resolve({
                data: {
                    data: []
                }
            })
        }
        let idsString = ids.join(',')
        return AXIOS.get(`/book/list/${idsString}`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'},
        })
    },
    getBooksByGenre(genre) {
        return AXIOS.get(`/book/genre/${genre}`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getBooksLast10() {
        return AXIOS.get(`/book/last10`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    searchBooks(query) {
        if (query === '') {
            return AXIOS.get(`/book/all`, {
                withCredentials: true,
                headers: {'Content-Type': 'application/json'}
            })
        }
        return AXIOS.get(`/book?query=${encodeURIComponent(query)}`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getAllBooks() {
        return AXIOS.get(`/book/all`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    // Order
    createOrder(cartList) {
        return AXIOS.post(`/order/create`, JSON.stringify({
            'cart': cartList
        }), {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getOrder(id) {
        return AXIOS.get(`/order/${id}`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    updateOrder(id, status) {
        return AXIOS.patch(`/order/${id}?status=${status}`, null, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getAllOrders() {
        return AXIOS.get(`/order/all`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    },
    getAllUserOrders() {
        return AXIOS.get(`/order/user`, {
            withCredentials: true,
            headers: {'Content-Type': 'application/json'}
        })
    }
}