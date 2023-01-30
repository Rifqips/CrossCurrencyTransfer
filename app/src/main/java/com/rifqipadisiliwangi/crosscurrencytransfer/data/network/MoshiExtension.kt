package com.rifqipadisiliwangi.crosscurrencytransfer.data.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object MoshiExtension {
    val moshi: Moshi = Moshi.Builder()
        .build()
}

// fungsinya mengubah string json ke object T (kata kunci untuk object yang kita panggil)biasanya digunakan pada saat method get
inline fun<reified T> deserializeJson(jsonString: String): T? {
    val adapter: JsonAdapter<T> = MoshiExtension.moshi.adapter(T::class.java)
    return adapter.fromJson(jsonString)
}

//inline fun <reified T> parseList(jsonString: String): List<T>{
//    val adapter: JsonAdapter<List<T>> = MoshiExtension.moshi.adapter(List::class.java, T::class.java)
//    return adapter.fromJson(jsonString.toList())
//    return adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).fromJson(jsonString)!!
//}


// fungsinya mengubah object ke string json T (Bisa apa saja/kata kunci untuk object yang kita panggil) biasanya digunakan pada saat method post
inline fun<reified T> T.serialized(): String {
    return MoshiExtension.moshi.adapter(T::class.java).toJson(this)
}