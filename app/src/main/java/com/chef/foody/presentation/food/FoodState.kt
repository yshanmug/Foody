package com.chef.foody.presentation.food

import com.chef.foody.events.FoodOrderObj

data class FoodState(
    val foodList: List<FoodOrderObj> = emptyList()
)