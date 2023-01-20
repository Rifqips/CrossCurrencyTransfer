package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import android.util.Log
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.HistoryApi
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
//        getHistory()
        history()
    }

    override fun onDetach() {
        scope.cancel()
    }

    fun history() {
        view?.onFinishedLoading()
        scope.launch {
            api
                .callHistory()
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    when (it) {
                        is ResponseStatus.Success -> view?.onSuccessGetHistory(it.data)
                        is ResponseStatus.Failed -> view?.onErrorHistory(it.code, it.message)
                    }
                }
            view?.onFinishedLoading()
            Log.d("error","$api")
        }
    }

}