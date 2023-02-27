package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataTransaksi (
    val jenisBank: String? = null,
    val namaPenerima: String? = null,
    val noRekening: String? = null,
    val tipeTransaksi: String? = null,
    val total: String? = null
): Serializable