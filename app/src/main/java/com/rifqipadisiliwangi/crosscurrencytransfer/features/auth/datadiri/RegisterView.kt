package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem

interface RegisterView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onErrorPassword(visible: Boolean, message: String)
    fun resetPasswordError()
    fun onSuccessGetUser(user: RegisterDataItem)
    fun onSuccessRegister()
}