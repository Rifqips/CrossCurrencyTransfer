package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "transaction_id")
    val transactionId: String? = null
)