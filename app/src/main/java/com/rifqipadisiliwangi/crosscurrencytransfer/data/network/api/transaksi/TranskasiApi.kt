package com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksi

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransaksiDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.NetworkTransaksiClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.deserializeJson
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.mapFailedResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.serialized
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class TranskasiApi {
    fun transaksiUser(
        id: String,
        jenisBank: String,
        namaPenerima: String,
        noRekening: String,
        tipeTransaksi: String,
        total: String
    ): Flow<ResponseStatus<TransaksiDataItem>> = flow {
        val model = TransaksiDataItem(id, jenisBank, namaPenerima,noRekening, tipeTransaksi, total )

        try {
            val result = NetworkTransaksiClient
                .makeCallApi("/transaksi", NetworkTransaksiClient.METHOD.POST, model.serialized())
                .execute()
            val response = if (result.isSuccessful) {
                val data: TransaksiDataItem = deserializeJson<TransaksiDataItem>(result.body?.string() ?: "") ?: TransaksiDataItem(id, jenisBank, namaPenerima, noRekening, tipeTransaksi, total)
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