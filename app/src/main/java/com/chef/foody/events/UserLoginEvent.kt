package com.chef.foody.events

sealed class UserLoginEvent {
    data object ResetUserState: UserLoginEvent()
    data class Login(val email: String,val password: String): UserLoginEvent()

}