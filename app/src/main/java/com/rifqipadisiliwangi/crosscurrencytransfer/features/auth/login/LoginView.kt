package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.AuthDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.LoginData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem

interface LoginView {
    fun onLoading()
    fun onFinishedLoading()
    fun onError(code: Int, message: String)
    fun onErrorEmail(code: Int, message: String)
    fun onErrorPassword(visible: Boolean, message: String)
    fun onSuccessGetUser(username: String, password: String)
    fun onSuccessLogin()
}