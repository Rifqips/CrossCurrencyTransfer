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
    private val view: HistoryView.View,
    private val api: HistoryApi,
    uiContext: CoroutineContext = Dispatchers.Main
): HistoryView.Presenter{
    private val supervisorJob: Job = SupervisorJob()
    private val scope = CoroutineScope(supervisorJob + uiContext)

    override fun onAttach() {
        getUsers()
    }

    override fun onDetach() {
        scope.cancel()
    }

    fun getUsers(page: Int = 1){
        view.onLoading()
        api.getHistoryResponse {
            scope.launch {
                when (it){
                    is ResponseStatus.Success -> view.onSuccessGetHistory(it.data)
                    is ResponseStatus.Failed -> view.onError(it.message)
                }
                view.onFinishedLoading()
            }
        }
    }
}