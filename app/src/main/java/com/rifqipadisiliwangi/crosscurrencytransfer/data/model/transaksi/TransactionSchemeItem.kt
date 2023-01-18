package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionSchemeItem(
    @Json(name = "bank_code")
    val bankCode: String? = null,
    @Json(name = "no_rekening")
    val noRekening: String? = null,
    @Json(name = "nominal")
    val nominal: String? = null,
    @Json(name = "pin")
    val pin: String? = null
)