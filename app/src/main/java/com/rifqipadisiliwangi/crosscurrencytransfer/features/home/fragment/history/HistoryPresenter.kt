package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.ResponseStatus
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.HistoryApi
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history.HistoryView
import kotlinx.coroutines.*
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
        api.getHistory {
            scope.launch {
                when (it){
                    is ResponseStatus.Success -> view.onSuccessGetUser(it.data)
                    is ResponseStatus.Failed -> view.onError(it.message)
                    else -> {}
                }
                view.onFinishedLoading()
            }
        }
    }


//    fun historyTransaksi() {
//        view?.onLoading()
//        scope.launch {
//            api
//                .getUserPagination()
//                .flowOn(Dispatchers.Default)
//                .collectLatest {
//                    when (it) {
//                        is ResponseStatus.Success<*> -> view?.onSuccessGetUser(it.data)
//                        is ResponseStatus.Failed -> view?.onError( it.message)
//                    }
//                }
//            view?.onFinishedLoading()
//            Log.d("error","$api")
//        }
//    }

//    fun getUsers(){
//        view.onLoading()
//        api.getUserPagination (HistoryDataItem(id = null)) {
//            scope.launch {
//                when (it){
//                    is ResponseStatusHistory.Success -> view.onSuccessGetUser(it.data)
//                    is ResponseStatusHistory.Failed -> view.onError(it.message)
//                }
//                view.onFinishedLoading()
//            }
//        }
//    }
}