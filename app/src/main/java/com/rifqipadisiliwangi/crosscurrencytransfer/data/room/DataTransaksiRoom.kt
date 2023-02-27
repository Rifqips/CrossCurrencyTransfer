package com.rifqipadisiliwangi.crosscurrencytransfer.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DataTransaksiRoom (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val jenisBank: String,
    val namaPenerima: String,
    val noRekening: String,
    val tipeTransaksi: String,
    val total: String
): Serializable