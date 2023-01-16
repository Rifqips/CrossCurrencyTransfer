package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryDataItem(
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "jenis_bank")
    val jenisBank: String? = null,
    @Json(name = "nama_penerima")
    val namaPenerima: String? = null,
    @Json(name = "no_rekening")
    val noRekening: String? = null,
    @Json(name = "tipe_transaksi")
    val tipeTransaksi: String? = null,
    @Json(name = "total")
    val total: String? = null
)