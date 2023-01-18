package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.getpin.PinSchemeResponse

interface PinView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onSuccessPin(otp : PinSchemeResponse)
}