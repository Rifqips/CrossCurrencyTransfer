package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import android.app.Application
import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.otp.OtpApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksi.TranskasiApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class OtpPresenter (
    private val otpApi: OtpApi,
    private val transaksiApi: TranskasiApi,
    private val uiContext: CoroutineContext = Dispatchers.Main
) {
    companion object {
        const val PASSWORD_NOT_CONTAIN_LOWERCASE = 0
        const val PASSWORD_NOT_CONTAIN_NUMBER = 2
        const val PASSWORD_ERROR = 9
        const val USERNAME_ERROR = 10
    }
    lateinit var application: Application
    private var view: OtpView? = null
    private var job = SupervisorJob()
    private var scope = CoroutineScope(job + uiContext)

    fun onAttach(view: OtpView) {
        this.view = view
    }

    fun onDetach() {
        this.view = null
    }

    fun otp( otp: Int) {
        view?.onFinishedLoading()
        scope.launch {
            otpApi
                .otpUser(otp)
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    when (it) {
                        is ResponseStatus.Success -> view?.onSuccessOtp(0)
                        is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                    }
                }
            view?.onFinishedLoading()
            Log.d("error","$otpApi")
        }
    }


}