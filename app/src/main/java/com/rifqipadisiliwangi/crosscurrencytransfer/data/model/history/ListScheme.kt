package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListScheme(
    var historySchemeItem: List<HistorySchemeItem> = listOf(),
)