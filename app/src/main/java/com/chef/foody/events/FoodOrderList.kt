package com.chef.foody.events

data class FoodOrderObj(
    val id: Int,
    val name: String,
    val description: String,
    val fileLocation: Int,
    val foodType: String,
    val quantity: Int
)
