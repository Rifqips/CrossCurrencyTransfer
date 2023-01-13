package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.otp

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.otp.OtpDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class OtpApi {
    fun otpUser(
        otp: Int,
    ): Flow<ResponseStatus<OtpDataItem>> = flow {
        val model = OtpDataItem(otp)

        try {
            val result = NetworkClient
                .makeCallApi("/otp_verification", NetworkClient.METHOD.POST, model.serialized())
                .execute()
            val response = if (result.isSuccessful) {
                val data: OtpDataItem = deserializeJson<OtpDataItem>(result.body?.string() ?: "") ?: OtpDataItem()
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