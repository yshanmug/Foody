package com.chef.foody.events



sealed class AcceptToRentalQuotationEvent {
    data class AcceptToRentalQuotation(val quotationId:Int ,val isAccepted: Boolean): AcceptToRentalQuotationEvent()
}