package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransaksiDataItem(
    @Json(name = "id")
    val id: String,
    @Json(name = "jenis_bank")
    val jenisBank: String,
    @Json(name = "nama_penerima")
    val namaPenerima: String,
    @Json(name = "no_rekening")
    val noRekening: String,
    @Json(name = "tipe_transaksi")
    val tipeTransaksi: String,
    @Json(name = "total")
    val total: String
)