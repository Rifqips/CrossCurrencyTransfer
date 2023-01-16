package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginDataItem(
    val users: List<RegisterDataItem> = listOf()
)
