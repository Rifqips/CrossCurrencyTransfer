package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.HistoryData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.HistoryDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.*
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkHistoryClient
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class HistoryApi {
    private val usersEndpoint ="/transaksi"

//    fun transaksiHistoryUser(
//        id: String,
//        jenisBank: String,
//        namaPenerima: String,
//        noRekening: String,
//        tipeTransaksi: String,
//        total: String
//    ): Flow<ResponseStatus<ArrayList<HistoryDataItem>>> = flow {
//        val model = TransaksiDataItem(id, jenisBank, namaPenerima,noRekening, tipeTransaksi, total )
//
//        try {
//            val result = NetworkTransaksiClient
//                .makeCallApiHistory("/transaksi", NetworkTransaksiClient.METHOD.GET, model.serialized())
//                .execute()
//            val response = if (result.isSuccessful) {
//                val data: HistoryDataItem = deserializeJson<HistoryDataItem>(result.body?.string() ?: "") ?: HistoryDataItem()
//                ResponseStatus.Success(data)
//            } else {
//                mapFailedResponse(result)
//            }
//            result.body?.close()
//        } catch (e: IOException) {
//            emit(ResponseStatus.Failed(-1, e.message.toString(), e))
//        }
//    }


    fun getUserPagination(onResponse: (ResponseStatus<List<HistoryDataItem>>) -> Unit){
        val endpoint = usersEndpoint
        val request = NetworkHistoryClient.requestBuilder(endpoint)
        NetworkHistoryClient
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

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = listOf(),
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