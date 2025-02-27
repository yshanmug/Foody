package com.chef.foody.events

import com.chef.foody.domain.model.DeliveryType

sealed class AcceptOfferQuotationEvent {
    data class AcceptOfferQuotation(val quotationId:Int ,val isAccepted: Boolean, val deliveryType: DeliveryType): AcceptOfferQuotationEvent()
}