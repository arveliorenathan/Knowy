package com.capstone.knowy.data.preference

data class UserModel(
    val userId: String,
    val token: String,
    val isLogin: Boolean = false
)