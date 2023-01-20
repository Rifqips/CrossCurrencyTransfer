package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.ListScheme

interface HistoryView {
        interface Presenter{
                fun onAttach()
                fun onDetach()
        }

        interface View{
                fun onLoading()
                fun onFinishedLoading()
                fun onError(message: String)
                fun onErrorHistory(code: Int, message: String)
                fun onSuccessGetHistory(user: List<HistorySchemeItem>){}
                fun onSuccessHistory(user: ListScheme)
                fun onSuccessAddUser(){}
        }

}