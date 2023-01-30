package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api

import android.util.Log
import androidx.viewbinding.BuildConfig
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreLogin
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import kotlin.math.log

class NetworkClient {
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

        // digunakan response post
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

        // dipake request post (Pin, Login)
        fun executeCall(endpoint: String, method: METHOD = METHOD.POST, jsonBody: String? = null): Response {
            //request builder base url
            val request = requestBuilder(endpoint, method, jsonBody)
            return try {
                val response = client.newCall(request).execute()
                interceptResponse(response)
            } catch (e: Exception) {
                throw e
            }
            Log.d("requestservice", "save ${PrivateData.accessToken}")
        }


        private fun interceptResponse(response: Response): Response {
            PrivateData.accessToken = response.header("Bearer", PrivateData.accessToken).toString()
            return response
        }

        // dipake request register (karena tidak membutuhkan token)
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