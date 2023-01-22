package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.NewPassword

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateItem(
    @Json(name = "password")
    val password : String? = null,
    @Json(name = "email")
    val email : String? = null
)
