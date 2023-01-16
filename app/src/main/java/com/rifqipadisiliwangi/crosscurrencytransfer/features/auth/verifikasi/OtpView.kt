package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.otp.OtpDataItem

interface OtpView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onSuccessOtp(otp : Int)
    fun onSuccessTransaksi()
}