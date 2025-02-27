package com.chef.foody.events

data class CartItem(
    val foodItem: FoodOrderObj,
    var quantity: Int,
    var objID: Int,
    val title: String,
)

// type veg or non veg
// title
// id
// quantity