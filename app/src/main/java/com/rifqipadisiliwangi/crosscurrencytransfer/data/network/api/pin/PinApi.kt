package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.pin

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.getpin.PinSchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.getpin.PinSchemeResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class PinApi {

    fun getPin(
        pin : String
    ): Flow<ResponseStatus<PinSchemeResponse>> = flow {
        val model = PinSchemeItem(pin)
        try {
            val result = NetworkClient
                .executeCall("/pin", NetworkClient.METHOD.POST, model.serialized())
            val response = if (result.isSuccessful) {
                val transaksi : PinSchemeResponse =
                    deserializeJson<PinSchemeResponse>(result.body?.string() ?: "") ?: PinSchemeResponse()
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