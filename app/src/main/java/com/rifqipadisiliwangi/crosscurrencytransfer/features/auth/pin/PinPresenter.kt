package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin

import android.app.Application
import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksi.PinApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PinPresenter (
    private val pinApi: PinApi,
    private val uiContext: CoroutineContext = Dispatchers.Main
) {
    companion object {
        const val PASSWORD_NOT_CONTAIN_LOWERCASE = 0
        const val PASSWORD_NOT_CONTAIN_NUMBER = 2
        const val PASSWORD_ERROR = 9
        const val USERNAME_ERROR = 10
    }
    lateinit var application: Application
    private var view: PinView? = null
    private var job = SupervisorJob()
    private var scope = CoroutineScope(job + uiContext)

    fun onAttach(view: PinView) {
        this.view = view
    }

    fun onDetach() {
        this.view = null
    }

    fun pin( pin: String) {
        view?.onFinishedLoading()
        scope.launch {
            pinApi
                .getPin(pin)
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    when (it) {
                        is ResponseStatus.Success -> view?.onSuccessPin(it.data)
                        is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                    }
                }
            view?.onFinishedLoading()
            Log.d("error","$pinApi")
        }
    }


}