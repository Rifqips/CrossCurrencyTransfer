package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginData(
    @Json(name = "accessToken")
    val accessToken: String? = null,
    @Json(name = "expiresIn")
    val expiresIn: Int? = null,
    @Json(name = "user")
    val user: User? = null
) {
    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "email")
        val email: String,
        @Json(name = "fullname")
        val fullname: String,
        @Json(name = "id")
        val id: String? = null,
        @Json(name = "userPin")
        val userPin: Boolean? = null
    )
}