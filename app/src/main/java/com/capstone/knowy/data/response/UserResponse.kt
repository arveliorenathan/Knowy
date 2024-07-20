package com.capstone.knowy.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("status")
    val status: String
)

data class User(

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)

data class EditProfileResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class RegisterResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class LoginResponse(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("status")
    val status: String
)

data class LoginResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)

data class UserScoreResponse(

    @field:SerializedName("scores")
    val scores: List<Scores>,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("status")
    val status: String
)

data class Scores(

    @field:SerializedName("score")
    val score: String,

    @field:SerializedName("documentName")
    val documentName: String
)

