package com.rifqipadisiliwangi.crosscurrencytransfer.data.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object MoshiExtension {
    val moshi: Moshi = Moshi.Builder()
        .build()
}

inline fun<reified T> deserializeJson(string: String): T? {
    val adapter: JsonAdapter<T> = MoshiExtension.moshi.adapter(T::class.java)
    return adapter.fromJson(string)
}

inline fun<reified T> T.serialized(): String {
    return MoshiExtension.moshi.adapter(T::class.java).toJson(this)
}