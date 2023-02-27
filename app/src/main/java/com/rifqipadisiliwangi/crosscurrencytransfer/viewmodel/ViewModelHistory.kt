package com.rifqipadisiliwangi.crosscurrencytransfer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransfer
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransferItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksimvvm.ApiClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.room.DataTransaksiRoom
import com.rifqipadisiliwangi.crosscurrencytransfer.data.room.TransaksiDatabase
import dagger.hilt.android.internal.Contexts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelHistory(app: Application) : AndroidViewModel(app) {

    lateinit var allNote : MutableLiveData<List<DataTransaksiRoom>>

    init {
        allNote = MutableLiveData()
        getAllTransaksi()
    }

    fun getAllNoteObservers(): MutableLiveData<List<DataTransaksiRoom>>{
        return allNote
    }

    fun getAllTransaksi(){
        GlobalScope.launch {
            val userDao = TransaksiDatabase.getInstance(getApplication())!!.noteDao()
            val listnote = userDao.getDataTransaksi()
            allNote.postValue(listnote)
        }

    }
}