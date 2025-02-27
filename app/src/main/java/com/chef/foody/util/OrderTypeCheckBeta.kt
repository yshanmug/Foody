package com.chef.foody.util

import com.chef.foody.data.remote.CustomerInfo
import com.chef.foody.domain.model.DeliveryType

sealed class OrderBetaType {
    data object ChefCook: OrderBetaType()
    data object ChefRental: OrderBetaType()
}

object OrderTypeCheckBeta {
    fun orderTypeCheckCustomerInfoBeta(c: CustomerInfo): OrderBetaType {
        return if (c.menu==null) OrderBetaType.ChefCook else OrderBetaType.ChefRental
    }
//    fun orderTypeCheckQuotation(q: )
}