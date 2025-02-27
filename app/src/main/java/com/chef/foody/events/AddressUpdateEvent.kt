package com.chef.foody.events

sealed class AddressUpdateEvent {
//    data object ResetUserState: UserProfileEvent()
    data class AddressUpdate(val address: String): AddressUpdateEvent()

}