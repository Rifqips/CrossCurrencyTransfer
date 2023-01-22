package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api

import android.util.Log
import androidx.viewbinding.BuildConfig
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkUpdateClient {
    companion object {
        const val BASE_URL = "https://red-gifted-squid.cyclic.app/api/v1"
        private val headerInterceptor: Interceptor = Interceptor {
            val request = it.request().newBuilder()
            request
                .addHeader("Content-Type", "application/json")
            return@Interceptor it.proceed(request.build())
        }

        val client: OkHttpClient by lazy {
            OkHttpClient
                .Builder()
                .addInterceptor(NetworkUpdateClient.headerInterceptor)
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

        fun requestBuilder(
            endpoint: String,
            method: NetworkUpdateClient.METHOD = NetworkUpdateClient.METHOD.PUT,
            jsonBody: String? = null
        ): Request {

            val request = Request
                .Builder()
                .url("${NetworkClient.BASE_URL}$endpoint")
            if (jsonBody != null)
                request.method(method.name, jsonBody.toRequestBody())

            return request.build()
        }

        fun makeCallApi(endpoint: String, method: METHOD = METHOD.PUT, jsonBody: String? = null): Call {
            val request = requestBuilder(endpoint, method, jsonBody)
//                val response = client.newCall(request).execute()
            return client.newCall(request)

        }
    }

    enum class METHOD {
        PUT
    }
}