package com.rifqipadisiliwangi.crosscurrencytransfer.data.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object MoshiExtension {
    val moshi: Moshi = Moshi.Builder()
        .build()
}

// fungsinya mengubah string json ke object T (kata kunci untuk object yang kita panggil)
inline fun<reified T> deserializeJson(jsonString: String): T? {
    val adapter: JsonAdapter<T> = MoshiExtension.moshi.adapter(T::class.java)
    return adapter.fromJson(jsonString)
}

// fungsinya mengubah object ke string json T (Bisa apa saja/kata kunci untuk object yang kita panggil)
inline fun<reified T> T.serialized(): String {
    return MoshiExtension.moshi.adapter(T::class.java).toJson(this)
}