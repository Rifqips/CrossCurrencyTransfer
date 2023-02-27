package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksimvvm

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.DataTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransfer
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransferItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("transaksi")
    fun getTransaksi(): Call<TransaksiTransferItem>

    @POST("transaksi")
    fun addTransaksi(@Body request: DataTransaksi): Call<TransaksiTransferItem>

}