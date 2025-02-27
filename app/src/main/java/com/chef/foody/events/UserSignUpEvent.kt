package com.chef.foody.events

sealed class UserSignUpEvent {
    data class Register(val firstName: String,val lastName: String,val email: String, val newPassword: String, val confirmPassword: String):  UserSignUpEvent()
}