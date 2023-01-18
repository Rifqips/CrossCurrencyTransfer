package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.pin


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthPinItem(
    @Json(name = "pin")
    val pin : String
)
