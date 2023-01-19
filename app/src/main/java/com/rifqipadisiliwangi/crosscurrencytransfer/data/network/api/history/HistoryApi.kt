package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkTransaksiClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class HistoryApi {

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
                            deserializeJson<ListScheme>(response.body?.string() ?: "")
                                ?: ListScheme()
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = getHistory.historySchemeItem ,
                                method = "POST",
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
        Log.d("requestservice", "baseurl-history $request ${PrivateData.accessToken}")
    }


    fun callHistory(
        adminFee: String? = null,
        bank: String? = null,
        expiredAt: String? = null,
        id: String? = null,
        isExpired: String? = null,
        nominal: String? = null,
        receipentName: String? = null,
        receipentNorek: String? = null,
        status: String? = null,
        total: String? = null,
        transactionDate: String? = null,
        typeCurrency: String? = null,
        typeTransaction: String? = null,
        virtualAccount: String? = null
    ): Flow<ResponseStatus<ListScheme>> = flow {
        val model = HistorySchemeItem(adminFee, bank, expiredAt, id, isExpired, nominal, receipentName, receipentNorek, status, total, transactionDate,typeCurrency, typeTransaction, virtualAccount)
        try {
            val result = NetworkTransaksiClient
                .executeCallHistory("/myTransaction", NetworkTransaksiClient.METHOD.POST, model.serialized())
            val response = if (result.isSuccessful) {
                val history : ListScheme =
                    deserializeJson<ListScheme>(result.body?.string() ?: "") ?: ListScheme()
                Log.d("requestservice", "cek-history $result ${PrivateData.accessToken}")
                ResponseStatus.Success(history)
            } else {
                mapFailedResponse(result)
            }
            emit(response)
            Log.d("error", "error-history ${response}")
            result.body?.close()
        } catch (e: IOException) {
            emit(ResponseStatus.Failed(-1, e.message.toString(), e))
        }


    }

}