package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterDataItem(
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "doc_type")
    val docType: String? = null,
    @Json(name = "doc_number")
    val docNumber: Int? = null,
    @Json(name = "firstname")
    val firstName: String? = null,
    @Json(name = "lastname")
    val lastName: String? = null,
    @Json(name = "birth_place")
    val birthPlace: String? = null,
    @Json(name = "address")
    val address: String? = null,
    @Json(name = "phone_number")
    val phoneNumber: Int? = null,
    @Json(name = "password")
    val password: String? = null,
    @Json(name = "sex")
    val sex: String? = null,
    @Json(name = "birth_date")
    val birthDate: String? = null
)
