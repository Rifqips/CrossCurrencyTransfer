package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.login

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.LoginDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response

class LoginApi {
    fun getListApi(onResponse: (ResponseStatus<List<RegisterDataItem>>) -> Unit){
        val request = NetworkClient.requestBuilder("/login")
        NetworkClient.
        client.newCall(request)
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
                        val data = deserializeJson<LoginDataItem>(response.body?.string() ?: "") ?: LoginDataItem()
                        onResponse.invoke(
                            ResponseStatus.Success(
                                data = data.users,
                                method = "GET",
                                status = true
                            )
                        )
                    } else {
                        onResponse.invoke(
                            mapFailedResponse(response)
                        )
                    }
                    response.body?.close()
                }
            })
    }

//    fun loginUser (
//        email : String,
//        password : String
//    ): Flow<ResponseStatus<LoginDataItem>> = flow {
//        val model = LoginDataItem(email,password)
//
//        try {
//            val result = NetworkClient
//                .makeCallApi("/login", NetworkClient.METHOD.POST, model.serialized())
//                .execute()
//            val response = if (result.isSuccessful) {
//                val data : LoginDataItem = deserializeJson<LoginDataItem>(result.body?.string() ?: "") ?: LoginDataItem()
//                ResponseStatus.Success(data)
//            } else {
//                mapFailedResponse(result)
//            }
//            emit(response)
//            result.body?.close()
//        } catch (e : IOException) {
//            emit(ResponseStatus.Failed(-1, e.message.toString(), e) )
//        }
//    }
}
//    fun onResponse (call: Call<List<LoginDataItem>>, response: Response<List<LoginDataItem>>) {
//        Log.d("Response", "onResponse: ${response.body()}")
//        fun loginUser (
//            email : String,
//            password : String
//        ): Flow<ResponseStatus<LoginDataItem>> = flow {
//            val model = LoginDataItem(email,password)
//
//            try {
//                val result = NetworkClient
//                    .makeCallApi("/login", NetworkClient.METHOD.POST, model.serialized())
//                    .execute()
//                val response = if (result.isSuccessful) {
//                    val data : LoginDataItem = deserializeJson<LoginDataItem>(result.body?.string() ?: "") ?: LoginDataItem()
//                    ResponseStatus.Success(data)
//                    Log.d("Response", "onResponse: ${response.body()}")
//                } else {
//                    mapFailedResponse(result)
//                }
////                emit(response)
//                result.body?.close()
//            } catch (e : IOException) {
//                emit(ResponseStatus.Failed(-1, e.message.toString(), e) )
//            }
//        }
//    }


