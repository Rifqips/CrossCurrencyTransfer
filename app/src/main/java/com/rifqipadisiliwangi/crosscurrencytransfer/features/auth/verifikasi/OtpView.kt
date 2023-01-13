package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem

interface OtpView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onSuccessOtp()
}