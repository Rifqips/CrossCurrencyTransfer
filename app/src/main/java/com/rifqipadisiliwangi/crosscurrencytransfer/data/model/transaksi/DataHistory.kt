package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi


data class DataHistory (
    val id: String,
    val jenisBank: String,
    val namaPenerima: String,
    val noRekening: String,
    val tipeTransaksi: String,
    val total: String
)