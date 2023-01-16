package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.HistoryDataItem

interface HistoryView {

    interface Presenter{
        fun onAttach()
        fun onDetach()
    }

    interface View{
        fun onLoading()
        fun onFinishedLoading()
        fun onError(message: String)
        fun onSuccessGetUser(user: List<HistoryDataItem>){}
        fun onSuccessAddUser(){}
    }
}