package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksimvvm

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransfer
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransferItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    @GET("film")
//    fun getAllFilm(): Call<List<ResponseFilmItem>>

//    @GET("transaksi")
//    fun getTransaksi(@Path("id") id: String, @Body transaksi: TransaksiTransferItem): Call<TransaksiTransfer>

    @GET("transaksi")
    fun getTransaksi(): Call<List<TransaksiTransfer>>

    @POST("transaksi")
    fun addTransaksi(): Call<List<TransaksiTransfer>>

//    @DELETE("film/{id}")
//    fun deleteFilm(@Path("id") id: String): Call<ResponseFilmItem>
//
//    @POST("film")
//    fun addFilm(@Body request: Film) : Call<ResponseFilmItem>
}