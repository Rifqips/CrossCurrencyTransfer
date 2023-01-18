package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.pin

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.AuthDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.LoginData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.pin.AuthPinItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.pin.PinData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class PinApi {
    fun pinUser(pin : String): Flow<ResponseStatus<PinData>> = flow {
        val model = AuthPinItem(pin)
        try {
            val result = NetworkClient
                .executeCall("/pin", NetworkClient.METHOD.POST, model.serialized())
            val response = if (result.isSuccessful) {
                val data: PinData = deserializeJson<PinData>(result.body?.string() ?: "") ?: PinData()
                ResponseStatus.Success(data)
            } else {
                mapFailedResponse(result)
            }
            emit(response)
            result.body?.close()
        } catch (e: IOException) {
            emit(ResponseStatus.Failed(-1, e.message.toString(), e))
        }

        Log.d("error","${model}")
    }
}