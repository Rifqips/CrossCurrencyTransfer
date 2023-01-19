package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem

interface HistoryView {
        interface Presenter{
                fun onAttach()
                fun onDetach()
        }

        interface View{
                fun onLoading()
                fun onFinishedLoading()
                fun onError(message: String)
                fun onSuccessGetHistory(user: List<HistorySchemeItem>){}
                fun onSuccessAddUser(){}
        }

}