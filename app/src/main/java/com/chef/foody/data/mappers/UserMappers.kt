package com.chef.foody.data.mappers

import com.chef.foody.domain.model.User
import com.chef.foody.data.remote.UserLoginResponse
import com.chef.foody.data.remote.UserRegistrationResponse


fun UserLoginResponse.toUser(): User {
    val userInfo = this.response.userInfo
    return User(
        firstName = userInfo?.firstName ?: "",
        lastName = userInfo?.lastName ?: "",
        email = userInfo?.email ?: "",
        address = userInfo?.address?:"",
        authToken = this.response.authToken ?: ""
    )
}

fun UserRegistrationResponse.toUser(): User {
    val userInfo = this.response.userInfo
    return User(
        firstName = userInfo?.firstName ?: "",
        lastName = userInfo?.lastName ?: "",
        email = userInfo?.email ?: "",
        address = userInfo?.address?:"",
        authToken = this.response.authToken ?: ""
    )
}