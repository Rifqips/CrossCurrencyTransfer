package com.rifqipadisiliwangi.crosscurrencytransfer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.DataTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransfer
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransferItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksimvvm.ApiClient
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksimvvm.ApiService
import com.rifqipadisiliwangi.crosscurrencytransfer.data.room.DataTransaksiRoom
import com.rifqipadisiliwangi.crosscurrencytransfer.data.room.TransaksiDatabase
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelTransaksi : ViewModel() {

    var liveDataTransaksi: MutableLiveData<TransaksiTransferItem> = MutableLiveData()
    var loading = MutableLiveData<Boolean>()

    init {
        liveDataTransaksi = MutableLiveData()
        loading = MutableLiveData()
    }


    fun postLiveDataTransaksi():MutableLiveData<TransaksiTransferItem>{
        return liveDataTransaksi
    }

    fun callPostTransaksi (
        jenisBank: String,
        namaPenerima: String,
        noRekening: String,
        tipeTransaksi: String,
        total: String
    ){
        ApiClient.instance.addTransaksi(DataTransaksi(jenisBank, namaPenerima, noRekening, tipeTransaksi, total))
            .enqueue(object : Callback<TransaksiTransferItem>{
                override fun onResponse(
                    call: Call<TransaksiTransferItem>,
                    response: Response<TransaksiTransferItem>
                ) {
                    if (response.isSuccessful){
                        liveDataTransaksi.postValue(response.body())
                    }else{
                        Log.d("data", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<TransaksiTransferItem>, t: Throwable) {
                    Log.d("data", call.toString())
                }

            })
    }fun callGetHistory (){
        ApiClient.instance.getTransaksi()
            .enqueue(object : Callback<TransaksiTransferItem>{
                override fun onResponse(
                    call: Call<TransaksiTransferItem>,
                    response: Response<TransaksiTransferItem>
                ) {
                    if (response.isSuccessful){
                        liveDataTransaksi.postValue(response.body())
                    }else{
                        Log.d("data", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<TransaksiTransferItem>, t: Throwable) {
                    Log.d("data", call.toString())
                }

            })
    }
}