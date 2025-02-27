package com.chef.foody.presentation.cart

import com.chef.foody.events.CartItem

data class CartState(
    val cartList: List<CartItem> = emptyList()
)