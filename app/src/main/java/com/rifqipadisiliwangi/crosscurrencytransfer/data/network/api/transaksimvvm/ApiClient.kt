package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksimvvm

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://63c16c9999c0a15d28e966cf.mockapi.io/transevilz/v1/"
    val instance : ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        retrofit.create(ApiService::class.java)
    }
}
