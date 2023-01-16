package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthDataItem(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)