package com.chef.foody.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AppConstants {
    const val USER_PREFERENCES = "user_preferences"
    const val TEST_USER_PREFERENCES = "test_user_preferences"
    val USER_FIRST_NAME_KEY = stringPreferencesKey("user_first_name")
    val USER_LAST_NAME_KEY = stringPreferencesKey("user_last_name")
    val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    val USER_AUTH_TOKEN_KEY = stringPreferencesKey("user_auth_token")
    val USER_LOGIN_STATUS_KEY = stringPreferencesKey("user_login_status")
    val USER_ADDRESS_KEY = stringPreferencesKey("user_address")
    val USER_CHEF_STATUS = stringPreferencesKey("user_chef_status")
    const val CURRENT_ACTIVE_ORDER_ID = "current_active_order_id"
}