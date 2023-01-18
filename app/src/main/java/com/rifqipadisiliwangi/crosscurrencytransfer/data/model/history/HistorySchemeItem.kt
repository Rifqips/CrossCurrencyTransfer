package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistorySchemeItem(
    @Json(name = "admin_fee")
    val adminFee: String? = null,
    @Json(name = "bank")
    val bank: String? = null,
    @Json(name = "expired_at")
    val expiredAt: String? = null,
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "isExpired")
    val isExpired: String? = null,
    @Json(name = "nominal")
    val nominal: String? = null,
    @Json(name = "receipent_name")
    val receipentName: String? = null,
    @Json(name = "receipent_norek")
    val receipentNorek: String? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "total")
    val total: String? = null,
    @Json(name = "transaction_date")
    val transactionDate: String? = null,
    @Json(name = "type_currency")
    val typeCurrency: String? = null,
    @Json(name = "type_transaction")
    val typeTransaction: String? = null,
    @Json(name = "virtual_account")
    val virtualAccount: String? = null
)