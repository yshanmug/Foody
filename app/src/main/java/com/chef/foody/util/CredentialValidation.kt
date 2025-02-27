package com.chef.foody.util

import android.util.Patterns

object CredentialValidation {
    fun emailValid(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun passwordValid(password: String): Boolean {
        return "[a-zA-Z0-9@#_]{8,16}".toRegex().matchEntire(password) != null
    }

    fun newPasswordValid(newPassword: String): Boolean {
        return "[a-zA-Z0-9@#_]{8,16}".toRegex().matchEntire(newPassword) != null
    }
    fun confirmPasswordValid(confirmPassword: String): Boolean {
        return "[a-zA-Z0-9@#_]{8,16}".toRegex().matchEntire(confirmPassword) != null
    }
}