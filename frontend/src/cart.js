import api from "./api";

const Cart = (function () {
    function Cart(items) {
        if (items === void 0) { items = []; }
        this._assets = items;
    }
    Cart.prototype.getItems = function () {
        return this._assets;
    };
    Cart.prototype.isEmpty = function () {
        return this._assets.length === 0;
    };
    Cart.prototype.addItem = function (item) {
        let existingItem = this._assets.find(function (i) { return i.id === item.id; });
        if (existingItem) {
            existingItem.quantity += item.quantity;
            return;
        }
        this._assets.push(item);
        this.updateStorage();
    };
    Cart.prototype.removeItem = function (item) {
        this._assets = this._assets.filter(function (i) { return i.id !== item; });
        this.updateStorage();
    };
    Cart.prototype.clear = function () {
        this._assets = [];
        this.updateStorage();
    };
    Cart.getInstance = function () {
        return new Promise(function (resolve) {
            if (!Cart.instance) {
                // Load cart from localstorage
                if (window.localStorage.getItem('cart')) {
                    let items = JSON.parse(window.localStorage.getItem('cart'));
                    api.getBooksByIds(items).then(function (assets) {
                        Cart.instance = new Cart(assets.data);
                        Cart.instance.updateStorage();
                        resolve(Cart.instance);
                    });
                }
                else {
                    Cart.instance = new Cart();
                    resolve(Cart.instance);
                }
            }
            else if (window.localStorage.getItem('cart')) {
                let items_1 = JSON.parse(window.localStorage.getItem('cart'));
                // Check whether items have changed between local and localstorage
                if (
                    // If the number of items in the cart has changed
                    Cart.instance._assets.length !== items_1.length ||
                    // Or if local cart has items that are not in the localstorage cart
                    Cart.instance._assets.some(function (i) { return !items_1.includes(i.id); }) ||
                    // Or if localstorage cart has items that are not in the local cart
                    items_1.some(function (i) { return !Cart.instance._assets.map(function (asset) { return asset.id; }).includes(i); })) {
                    // Get the new items from the localstorage
                    api.getBooksByIds(items_1).then(function (assets) {
                        Cart.instance._assets = assets.data;
                        Cart.instance.updateStorage();
                        resolve(Cart.instance);
                    });
                }
                else {
                    resolve(Cart.instance);
                }
            }
            else {
                resolve(Cart.instance);
            }
        });
    };
    Cart.prototype.updateStorage = function () {
        window.localStorage.setItem('cart', JSON.stringify(this._assets.map(function (i) { return i.id; })));
    };
    return Cart;
}());

export default Cart;