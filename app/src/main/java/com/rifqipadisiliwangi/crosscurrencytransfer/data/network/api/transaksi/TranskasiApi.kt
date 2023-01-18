package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksi

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransactionSchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransactionSchemeResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class TranskasiApi {

    fun transaksiUser(
        bankCode: String,
        noRekening: String,
        nominal: String,
        pin: String
    ): Flow<ResponseStatus<TransactionSchemeResponse>> = flow {
        val model = TransactionSchemeItem(bankCode, noRekening, nominal, pin)
        try {
            val result = NetworkClient
                .executeCall("/transactions", NetworkClient.METHOD.POST, model.serialized())
            val response = if (result.isSuccessful) {
                val transaksi : TransactionSchemeResponse =
                    deserializeJson<TransactionSchemeResponse>(result.body?.string() ?: "") ?: TransactionSchemeResponse()
                Log.d("requestservice", "token-pin $result ${PrivateData.accessToken}")
                ResponseStatus.Success(transaksi)
            } else {
                mapFailedResponse(result)
            }
            emit(response)
            result.body?.close()
        } catch (e: IOException) {
            emit(ResponseStatus.Failed(-1, e.message.toString(), e))
        }

        Log.d("error", "${model}")
    }




}