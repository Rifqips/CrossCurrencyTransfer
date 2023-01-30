package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm

import com.google.gson.annotations.SerializedName

data class TransaksiTransferItem(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("jenis_bank")
    val jenisBank: String? = null,
    @SerializedName("nama_penerima")
    val namaPenerima: String? = null,
    @SerializedName("no_rekening")
    val noRekening: String? = null,
    @SerializedName("tipe_transaksi")
    val tipeTransaksi: String? = null,
    @SerializedName("total")
    val total: String? = null
)