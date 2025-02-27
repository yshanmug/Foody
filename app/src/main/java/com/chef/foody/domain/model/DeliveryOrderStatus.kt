package com.chef.foody.domain.model

enum class DeliveryOrderStatus {
    NOT_ASSIGNED,
    ORDER_CONFIRMED,
    PREPARING_FOOD,
    FOOD_IS_READY_FOR_PICKUP,
    FOOD_IS_READY_FOR_DELIVERY,
    DELIVERED,
}