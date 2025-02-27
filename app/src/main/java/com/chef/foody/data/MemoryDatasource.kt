package com.chef.foody.data

import com.chef.foody.data.remote.OrderStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryDatasource @Inject constructor() {
    private var order: OrderStatus? = null
    fun setActiveOrder(order: OrderStatus?) {
        this.order = order
    }
    fun getActiveOrder(): OrderStatus? {
        return this.order
    }
    fun resetActiveOrder() {
        this.order = null
    }
}