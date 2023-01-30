package com.rifqipadisiliwangi.crosscurrencytransfer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransferItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksimvvm.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelHistory: ViewModel() {
    private var liveDataTransaksi : MutableLiveData<List<TransaksiTransferItem>> = MutableLiveData()
    private var addTransaksi : MutableLiveData<TransaksiTransferItem> = MutableLiveData()
    fun getLiveDataTransaksi() : MutableLiveData<List<TransaksiTransferItem>>{
        return liveDataTransaksi
    }


    fun addPostApiFilm(
        jenisBank: String,
        namaPenerima: String,
        noRekening: String,
        tipeTransaksi: String,
        total: String
    ){
        ApiClient.instance.addTransaksi(TransaksiTransferItem(jenisBank, jenisBank, namaPenerima, noRekening, tipeTransaksi, total))
            .enqueue(object  : Callback<TransaksiTransferItem> {
                override fun onResponse(
                    call: Call<TransaksiTransferItem>,
                    response: Response<TransaksiTransferItem>
                ) {
                    if (response.isSuccessful){
                        addTransaksi.postValue(response.body())
                    }else{
                        addTransaksi.postValue(null)
                    }
                }

                override fun onFailure(call: Call<TransaksiTransferItem>, t: Throwable) {
                    addTransaksi.postValue(null)
                }

            })
    }
}