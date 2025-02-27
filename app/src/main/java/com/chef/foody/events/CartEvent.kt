package com.chef.foody.events

sealed class CartEvent {
    data class AddToCart(val item: FoodOrderObj): CartEvent()
    data class ChangeQuantity(val item: FoodOrderObj, val change: String): CartEvent()
    data class ChangeCartQuantity(val item: CartItem, val change: String): CartEvent()
    data class DeleteCartItem(val item: CartItem): CartEvent()
}
