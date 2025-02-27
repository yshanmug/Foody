package com.chef.foody.events

import com.chef.foody.data.remote.FoodInfo
import com.chef.foody.data.remote.deliveryMode

sealed class OrderFoodEvent {
    data class OrderFood(val email: String, val cartList: List<FoodInfo>, val deliveryMode: deliveryMode): OrderFoodEvent()
}