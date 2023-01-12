package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import android.app.Application
import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.register.RegisterApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterPresenter (
    private val registerApi: RegisterApi,
    private val uiContext: CoroutineContext = Dispatchers.Main
) {
    companion object {
        const val PASSWORD_NOT_CONTAIN_LOWERCASE = 0
        const val PASSWORD_NOT_CONTAIN_NUMBER = 2
        const val PASSWORD_ERROR = 9
        const val USERNAME_ERROR = 10
    }
    lateinit var application: Application
    private var view: RegisterView? = null
    private var job = SupervisorJob()
    private var scope = CoroutineScope(job + uiContext)



    fun onAttach(view: RegisterView) {
        this.view = view
    }

    fun onDetach() {
        this.view = null
    }

    fun validateCredential( password: String) {
        view?.onLoading()
        val isPasswordValid =
            password.contains("[a-z]".toRegex())
                    && password.contains("[A-Z]".toRegex())
                    && password.contains("[0-9]".toRegex())
                    && password.length >= 8

        if (!isPasswordValid) { view?.onError(1,"Kata sandi harus berisi huruf besar, angka, simbol \n" +"(@ * # &), dan 8 karakter") }
        if (isPasswordValid) { view?.onError(2,"dah bener nih gaes") }

        view?.onFinishedLoading()

    }

    fun validateEmail(email: String) {
        view?.onLoading()
        val isEmailValid =
            email.contains("[a-zA-Z0-9._-]+@[a-z]+[.]+com+".toRegex()) ||
            email.contains("[a-zA-Z0-9._-]+@[a-z]+[.]+co+[.]+id".toRegex())

        if (!isEmailValid) { view?.onErrorEmail(1,"Email sudah terdaftar") }
        if (!isEmailValid) { view?.onErrorEmail(2,"Format email salah") }
        if (isEmailValid) { view?.onErrorEmail(3,"Sudah benar ges") }
        view?.onFinishedLoading()
    }

    fun register(
        email: String,
        docType: String,
        docNumber: Int,
        firstName: String,
        lastName: String,
        birthPlace: String,
        address: String,
        phoneNumber: Int,
        password: String,
        sex: String,
    ) {
        view?.onLoading()
        scope.launch {
            registerApi
                .registerUser(email, docType, docNumber, firstName, lastName, birthPlace, address, phoneNumber, password, sex)
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    when (it) {
                        is ResponseStatus.Success -> view?.onSuccessRegister()
                        is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                    }
                }
            view?.onFinishedLoading()
            Log.d("error","$registerApi")
        }
    }


}