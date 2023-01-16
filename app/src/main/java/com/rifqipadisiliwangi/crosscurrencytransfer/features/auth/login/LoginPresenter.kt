package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.app.Application
import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.login.LoginApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginPresenter(
    private val loginApi : LoginApi,
    uiContext: CoroutineContext = Dispatchers.Main
) {
    lateinit var application: Application
    private var view: LoginView? = null
    private var job = SupervisorJob()
    private var scope = CoroutineScope(job + uiContext)

    fun onAttach(view: LoginView) {
        this.view = view
        getUserList()
    }

    fun onDetach() {
        this.view = null
    }

    fun validasiPassword (password : String) {
        view?.onLoading()
        val isPasswordValid =
            password.contains("[a-z]".toRegex())
                    && password.contains("[A-Z]".toRegex())
                    && password.contains("[0-9]".toRegex())
                    && password.length >= 8

        if (!isPasswordValid)   { view?.onError(1,"Kata sandi harus berisi huruf besar, angka, simbol \n" +"(@ * # &), dan 8 karakter")}
        if (isPasswordValid)    { view?.onError(2, "Password sukses")}

        view?.onFinishedLoading()
    }

    fun validateEmail(email: String) {
        view?.onLoading()
        val isEmailValid =
            email.contains("[a-zA-Z0-9._-]+@[a-z]+[.]+com+".toRegex()) ||
                    email.contains("[a-zA-Z0-9._-]+@[a-z]+[.]+co+[.]+id".toRegex())

        if (!isEmailValid) { view?.onErrorEmail(1,"Email sudah terdaftar") }
        if (!isEmailValid) { view?.onErrorEmail(2,"Format email salah") }
        if (isEmailValid) { view?.onErrorEmail(3,"Email sudah benar") }
        view?.onFinishedLoading()
    }

//    fun login (
//        email : String,
//        password : String
//    ){
//        view?.onLoading()
//        scope.launch {
//            loginApi
//                .loginUser(email,password)
//                .flowOn(Dispatchers.Default)
//                .collectLatest {
//                    when (it) {
//                        is ResponseStatus.Success -> view?.onSuccessLogin()
//                        is ResponseStatus.Failed -> view?.onError(it.code, it.message)
//                    }
//                }
//            view?.onFinishedLoading()
//            Log.d("error", "$loginApi")
//        }
//    }
    fun getUserList() {
        loginApi.getListApi {
            scope.launch {
                when (it) {
                    is ResponseStatus.Success -> view?.onSuccessGetUser(it.data.toMutableList())
                    is ResponseStatus.Failed -> view?.onError(1, it.message)
                }
            }
        }
    }
}