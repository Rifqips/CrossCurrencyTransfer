package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api

import androidx.viewbinding.BuildConfig
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkClient {
    companion object {
        const val BASE_URL = "https://red-gifted-squid.cyclic.app"
//        const val BASE_URL2 = "http://103.152.119.157:5555/api/backoffice/"
        private val headerInterceptor: Interceptor = Interceptor {
            val request = it.request().newBuilder()
            request
                .addHeader("Content-Type", "application/json")
                .addHeader("Bearer", PrivateData.accessToken)
//                .addHeader(0.toString(), PrivateData.expiresIn.toString())
            return@Interceptor it.proceed(request.build())
        }

        val client: OkHttpClient by lazy {
            OkHttpClient
                .Builder()
                .addInterceptor(NetworkClient.headerInterceptor)
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
            method: METHOD = METHOD.POST,
            jsonBody: String? = null
        ): Request {
            val request = Request
                .Builder()
                .url("$BASE_URL$endpoint")

            if (jsonBody != null)
                request.method(method.name, jsonBody.toRequestBody())

            return request.build()
        }

        fun executeCall(endpoint: String, method: METHOD = METHOD.POST, jsonBody: String? = null): Response {
            val request = requestBuilder(endpoint, method, jsonBody)
            return try {
                val response = client.newCall(request).execute()
                interceptResponse(response)
            } catch (e: Exception) {
                throw e
            }
        }

        private fun interceptResponse(response: Response): Response {
            PrivateData.accessToken = response.header("Bearer", "").toString()
//            PrivateData.expiresIn = response.header(0, 0).toString()
            return response
        }

//        fun requestBuilder2(
//            endpoint: String,
//            method: METHOD = METHOD.GET,
//            jsonBody: String? = null
//        ): Request {
//            val request = Request
//                .Builder()
//                .url("$BASE_URL2$endpoint")
//
//            if (jsonBody != null)
//                request.method(method.name, jsonBody.toRequestBody())
//
//            return request.build()
//        }

        fun makeCallApi(
            endpoint: String,
            method: METHOD = METHOD.POST,
            jsonBody: String? = null
        ): Call {
            val request = requestBuilder(endpoint, method, jsonBody)
            return client.newCall(request)
        }
    }

    enum class METHOD {
        GET,
        POST
    }
}