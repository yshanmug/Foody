package com.chef.foody.presentation.user

import com.chef.foody.domain.model.ChefStatus
import com.chef.foody.domain.model.LoginStatus
import com.chef.foody.domain.model.User

data class UserState(
    val userInfo: User = User(firstName = "", lastName = "", authToken = "", email = "", address = ""),
    val chefStatus: ChefStatus? = null,
    val isUserLoggedIn: LoginStatus = LoginStatus.Logout,
)