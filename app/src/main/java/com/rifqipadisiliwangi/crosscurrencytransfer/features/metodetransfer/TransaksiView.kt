package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransaksiDataItem

interface TransaksiView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onErrorTransasksi(code: Int, message: String)
    fun onSuccessTransaksi(transaksi: TransaksiDataItem)
    fun onSuccessRegister()
}