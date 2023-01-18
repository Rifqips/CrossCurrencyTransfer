package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.getpin.PinSchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.getpin.PinSchemeResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransactionSchemeResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.*
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkTransaksiClient
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class HistoryApi {


//    fun getHistory(): Flow<ResponseStatus<List<HistorySchemeItem>>> = flow {
//        try {
//            val result = NetworkClient
//                .executeCallHistory("/myTransaction", NetworkClient.METHOD.GET, serialized())
//            val response = if (result.isSuccessful) {
//                val history : List<HistorySchemeItem> =
//                    deserializeJson<List<HistorySchemeItem>>(result.body?.string() ?: "") ?: listOf()
//                Log.d("requestservice", "token-history $result ${PrivateData.accessToken}")
//                ResponseStatus.Success(history)
//            } else {
//                mapFailedResponse(result)
//            }
//            emit(response)
//            result.body?.close()
//        } catch (e: IOException) {
//            emit(ResponseStatus.Failed(-1, e.message.toString(), e))
//        }
//
//
//    }
    private val usersEndpoint ="/myTransaction"

    fun getHistoryResponse(onResponse: (ResponseStatus<List<HistorySchemeItem>>) -> Unit){
        val endpoint = usersEndpoint
        val request = NetworkTransaksiClient.requestResponse(endpoint)
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

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val getHistory =
                            deserializeJson<List<HistorySchemeItem>>(response.body?.string() ?: "")
                                ?: listOf()
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = getHistory ,
                                method = "GET",
                                status = true
                            )
                        )
                        Log.d("requestservice", "token-history $request ${PrivateData.accessToken}")
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