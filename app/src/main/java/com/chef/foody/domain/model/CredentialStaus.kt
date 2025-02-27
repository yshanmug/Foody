package com.chef.foody.domain.model


enum class LoginStatus {
    Logout,
    LoginSuccessful,
    LoginFailed,
}

data class SignupUiState(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
)