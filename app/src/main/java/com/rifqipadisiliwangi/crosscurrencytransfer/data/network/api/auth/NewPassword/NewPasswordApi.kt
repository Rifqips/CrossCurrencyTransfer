package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.NewPassword

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.NewPassword.UpdateItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkUpdateClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class NewPasswordApi {

    fun updatePassword(password: String, email: String): Flow<ResponseStatus<UpdateItem>> = flow {
        val model = UpdateItem(password,email )
        try {
            val result = NetworkUpdateClient
                .makeCallApi("/new_password", NetworkUpdateClient.METHOD.PUT, model.serialized())
                .execute()
            val response = if (result.isSuccessful) {
                val data: UpdateItem = deserializeJson<UpdateItem>(result.body?.string() ?: "") ?: UpdateItem()
                ResponseStatus.Success(data)
            } else {
                mapFailedResponse(result)
            }
            emit(response)
            result.body?.close()
        } catch (e : IOException) {
            emit(ResponseStatus.Failed(-1, e.message.toString(), e))
        }
    }
}