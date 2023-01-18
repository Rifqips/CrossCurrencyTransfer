package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem

interface HistoryView {
        fun onLoading()
        fun onFinishedLoading()
        fun onError(code: Int, message: String)
        fun onSuccessHistory(user: List<HistorySchemeItem>)
        fun onSuccessAddUser(){}

}