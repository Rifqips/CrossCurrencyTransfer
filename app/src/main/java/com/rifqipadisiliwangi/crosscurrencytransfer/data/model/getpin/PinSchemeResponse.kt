package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.getpin


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class PinSchemeResponse(
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "status")
    val status: String? = null
)