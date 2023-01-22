package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.NewPassword

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaswdResp(
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "status")
    val status: String? = null
)
