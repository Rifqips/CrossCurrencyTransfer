package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.otp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OtpDataItem(
    @Json(name = "otp_code")
    val otp : Int? = null,
)
