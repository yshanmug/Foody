package com.chef.foody.domain.model

data class User(
    val firstName:String,
    val lastName:String,
    val email:String,
    val address: String,
    val authToken: String,
)