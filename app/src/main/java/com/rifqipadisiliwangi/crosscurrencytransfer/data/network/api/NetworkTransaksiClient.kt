package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api

import android.util.Log
import androidx.viewbinding.BuildConfig
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkTransaksiClient {
    companion object {
        const val BASE_URL = "https://red-gifted-squid.cyclic.app/api/v1"
        private val headerInterceptor: Interceptor = Interceptor {
            val request = it.request().newBuilder()
            request
                .addHeader("Content-Type", "application/json")
                // Digunakan sebagai get akses token   Value diset menyesuaikan dengan headers response yang ada di service/api
                .addHeader("Authorization","Bearer ${PrivateData.accessToken}")
            Log.d("requestservice", "get ${PrivateData.accessToken}")
            return@Interceptor it.proceed(request.build())
        }

        val client: OkHttpClient by lazy {
            OkHttpClient
                .Builder()
                .addInterceptor(NetworkTransaksiClient.headerInterceptor)
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level =
                            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE
                    }
                )
                .callTimeout(timeout = 5L, unit = TimeUnit.SECONDS)
                .connectTimeout(timeout = 2L, unit = TimeUnit.SECONDS)
                .build()
        }

        // digunakan response
        fun requestResponse(
            endpoint: String,
            method: METHOD = METHOD.GET,
            jsonBody: String? = null,
        ): Request {
            val request = Request
                .Builder()
                .url("$BASE_URL$endpoint")
                .removeHeader("Bearer ${PrivateData.accessToken}")
//                .header("Authorization","Bearer ${PrivateData.accessToken}")
            if (jsonBody != null)
                request.method(method.name, jsonBody.toRequestBody())
            return request.build()
        }

        private fun interceptRequest(request: Request): Request {
            PrivateData.accessToken = request.header("Bearer ${PrivateData.accessToken}").toString()
            return request
        }

    }

    enum class METHOD {
        GET,
        POST
    }
}