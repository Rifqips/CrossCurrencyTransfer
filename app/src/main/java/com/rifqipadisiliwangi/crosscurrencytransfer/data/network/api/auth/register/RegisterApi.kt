package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.register

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import kotlin.math.log

class RegisterApi {
    fun registerUser(
        email: String,
        docType: String,
        docNumber: Int,
        firstName: String,
        lastName: String,
        birthPlace: String,
        address: String,
        phoneNumber: String,
        password: String,
        sex: String,
    ): Flow<ResponseStatus<RegisterDataItem>> = flow {
        val model = RegisterDataItem(email, docType, docNumber, firstName, lastName, birthPlace,  address, phoneNumber, password, sex)

        try {
            val result = NetworkClient
                .makeCallApi("/register", NetworkClient.METHOD.POST, model.serialized())
                .execute()
            val response = if (result.isSuccessful) {
                val data: RegisterDataItem = deserializeJson<RegisterDataItem>(result.body?.string() ?: "") ?: RegisterDataItem()
                ResponseStatus.Success(data)
            } else {
                mapFailedResponse(result)
            }
            emit(response)
            result.body?.close()
        } catch (e: IOException) {
            emit(ResponseStatus.Failed(-1, e.message.toString(), e))
        }
    }
}