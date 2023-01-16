package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login

import com.google.gson.annotations.SerializedName

data class LoginResponse2(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("expiresIn")
    val expiresIn: Int,
    @SerializedName("user")
    val user: User
) {
    data class User(
        @SerializedName("email")
        val email: String,
        @SerializedName("fullname")
        val fullname: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("userPin")
        val userPin: Boolean
    )
}