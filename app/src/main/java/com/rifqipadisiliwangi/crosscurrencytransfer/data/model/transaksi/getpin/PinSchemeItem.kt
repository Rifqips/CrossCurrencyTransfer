package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.getpin


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PinSchemeItem(
    @Json(name = "pin")
    val pin: String? = null
)