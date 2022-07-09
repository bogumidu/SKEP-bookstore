import api from "./api";

export default class Cart {

    static instance;
    cart;

    constructor(cart) {
        if (cart === void 0) { this.cart = []; }
        else {
            this.cart = cart.map(x => Cart.toLocalItem(x, 1));
        }
    }

    /**
     * Add an item to the cart
     * @param product
     * @param quantity
     */
    addItem(product, quantity) {
        if (quantity === void 0) { quantity = 1; }
        if (this.cart.some(x => x.id === product.id)) {
            this.cart.find(x => x.id === product.id).quantity+=quantity;
        } else {
            this.cart.push(Cart.toLocalItem(product, quantity));
        }
        this.updateStorage();
    }

    /**
     * Remove an item from the cart
     * @param product
     */
    remove(product) {
        this.cart = this.cart.filter(item => item.id !== product.id);
        this.updateStorage();
    }

    /**
     * Remove one piece of an item from the cart
     * @param product
     */
    removeOne(product) {
        if (this.cart.find(x => x.id === product.id).quantity > 1) {
            this.cart.find(x => x.id === product.id).quantity--;
            this.updateStorage();
        } else {
            this.remove(product);
        }
    }

    getItems() {
        return this.cart;
    }

    clear() {
        this.cart = [];
        this.updateStorage();
    }

    /**
     * Update the localstorage with the current cart
     */
    updateStorage() {
        window.localStorage.setItem('cart', JSON.stringify(this.cart));
    }

    /**
     * Convert a product to a local item
     * @param item
     * @param quantity
     * @returns {{cover: *, quantity, author: {firstName, lastName}, price: (number|string|*), id, title}}
     */
    static toLocalItem(item, quantity) {
        return {
            id: item.id,
            author: {
                firstName: item.author.firstName,
                lastName: item.author.lastName
            },
            title: item.title,
            price: item.price,
            quantity: quantity,
            cover: item.cover
        }
    }

    /**
     * Get the cart instance
     * @returns {Promise<*>}
     */
    static async getInstance() {
        if (!Cart.instance) {
            // Load cart from localstorage
            if (window.localStorage.getItem('cart')) {
                let items = JSON.parse(window.localStorage.getItem('cart'));
                let response = await api.getBooksByIds(items.map(x => x.id));
                Cart.instance = new Cart(response.data.data.books);
                Cart.instance.updateStorage();
                return Cart.instance;
            }
            else {
                Cart.instance = new Cart();
                return Cart.instance;
            }
        }
        else if (window.localStorage.getItem('cart')) {
            let cart = JSON.parse(window.localStorage.getItem('cart')) ?? [];
            // Check whether items have changed between local and localstorage
            if (
                // If the number of items in the cart has changed
                Cart.instance.cart.length !== cart.length ||
                // Or if local cart has items that are not in the localstorage cart
                Cart.instance.cart.some(function (i) { return !cart.map(x => x.id).includes(i.id); }) ||
                // Or if localstorage cart has items that are not in the local cart
                cart.some(function (i) { return !Cart.instance.cart.map(function (asset) { return asset.id; }).includes(i.id); })) {
                // Get the new items from the localstorage
                let response = await api.getBooksByIds(cart.map(x => x.id));
                let cart = response.data.data.books.map(x => {
                    let item = cart.find(y => y.id === x.id);
                    return Cart.toLocalItem(x, item.quantity);
                });
                Cart.instance.cart = cart;
                Cart.instance.updateStorage();
                return Cart.instance;
            }
            else {
                return Cart.instance;
            }
        }
        else {
            return Cart.instance;
        }
    }
    static clear() {
        Cart.instance = new Cart();
        Cart.instance.updateStorage();
    }
}
