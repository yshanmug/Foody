package com.chef.foody.events

import com.chef.foody.data.remote.ChefSendQuotationParam

sealed class ChefQuotationEvent {
    data class Quotation(val orderId: Int, val price: Float, val time: Int): ChefQuotationEvent()
    data class QuotationBeta(val orderId: Int, val price: Float, val time: Int, val type: String, val menu: String): ChefQuotationEvent()
}