package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import android.app.Application
import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.HistoryApi
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin.PinView
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history.HistoryView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class HistoryPresenter(
    private val historyApi: HistoryApi,
    private val uiContext: CoroutineContext = Dispatchers.Main

){
    companion object {
        const val PASSWORD_NOT_CONTAIN_LOWERCASE = 0
        const val PASSWORD_NOT_CONTAIN_NUMBER = 2
        const val PASSWORD_ERROR = 9
        const val USERNAME_ERROR = 10
    }

    lateinit var application: Application
    private var view: HistoryView? = null
    private var job = SupervisorJob()
    private var scope = CoroutineScope(job + uiContext)

    fun onAttach(view: HistoryView) {
        this.view = view
        getHistories()
    }

    fun onDetach() {
        this.view = null
    }

//    fun getHistory() {
//        view?.onFinishedLoading()
//        scope.launch {
//            historyApi
//                .getHistoryResponse()
//                .flowOn(Dispatchers.Default)
//                .collectLatest {
//                    when (it) {
//                        is ResponseStatus.Success -> view?.onSuccessHistory(it.data)
//                        is ResponseStatus.Failed -> view?.onError(it.code, it.message)
//                    }
//                }
//            view?.onFinishedLoading()
//            Log.d("error","$historyApi")
//        }
//    }

    fun getHistories(){
        view?.onLoading()
        historyApi.getHistoryResponse {
            scope.launch {
                when (it){
                    is ResponseStatus.Success -> view?.onSuccessHistory(it.data)
                    is ResponseStatus.Failed -> view?.onError(it.code, it.message)
                }
                view?.onFinishedLoading()
            }
        }
    }
}