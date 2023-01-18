package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.login

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.AuthDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.LoginData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class LoginApi {

    fun loginUser(email: String, password: String): Flow<ResponseStatus<LoginData>> = flow {
        val model = AuthDataItem(email, password)
        try {
            val result = NetworkClient
                .executeCall("/login", NetworkClient.METHOD.POST, model.serialized())
            val response = if (result.isSuccessful) {
                val data: LoginData = deserializeJson<LoginData>(result.body?.string() ?: "") ?: LoginData()
                //Digunakan sebagain simpan response token
                PrivateData.accessToken = data.accessToken.toString()
                Log.d("requestservice", "get-token ${PrivateData.accessToken}")
                // Sebetulnya ini return body dari respon tipe data yang dibutuhkan
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


