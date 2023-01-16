package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransaksiDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.*
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkTransaksiClient
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class HistoryApi {
    private val usersEndpoint ="/transaksi"


    fun getHistory(onResponse: (ResponseStatus<List<TransaksiDataItem>>) -> Unit){
        val endpoint = usersEndpoint
        val request = NetworkTransaksiClient.requestBuilder(endpoint)
        NetworkTransaksiClient
            .client
            .newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onResponse.invoke(
                        ResponseStatus.Failed(
                            code = -1,
                            message = e.message.toString(),
                            throwable = e
                        )
                    )
                }
                // ketika on respon dia memerlukan tipe data yang ingin diperoleh (HistoryDataItem)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        // deserializeJson untuk mengubah string json to object
                        val userPagination : List<TransaksiDataItem> =
                            deserializeJson<List<TransaksiDataItem>>(response.body?.string() ?: "")
                            // deserializeJson kembaliannya bisa null, jika null mengembbalikan list of
                                ?: listOf()
                            //val users : HistoryDataItem =
                            //deserializeJson<HistoryDataItem>(response.body?.string() ?:"") ?: HistoryDataItem()
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = userPagination,
                                method = "GET",
                                status = true
                            )
                        )
                        Log.d("data",response.body.toString())
                    } else {
                        onResponse.invoke(
                            ResponseStatus.Failed(response.code, "Failed")
                        )
                    }
                    response.body?.close()
                }
            })
    }

}