package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransaksiDataItem

interface HistoryView {

    interface Presenter{
        fun onAttach()
        fun onDetach()
    }

    interface View{
        fun onLoading()
        fun onFinishedLoading()
        fun onError(message: String)
        fun onSuccessGetUser(user: List<TransaksiDataItem>){}
        fun onSuccessAddUser(){}
    }
}