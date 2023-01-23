package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.asLiveData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.dataStore
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivitySuksesTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SuksesTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySuksesTransferBinding
    private lateinit var dataTransaksi : DataStoreTransaksi
    var transaksiTotal = ""
    var metodePembayaran = ""
    var pilihBank = ""
    var noRekeningTransaksi = ""
    var namaPenerima = ""
    var codeSwift = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuksesTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataTransaksi = DataStoreTransaksi(this)
        getDataStore()

        binding.btnBack.setOnClickListener {
            GlobalScope.launch {
                dataTransaksi.clearData()
            }
            startActivity(Intent(this, HomeBottomActivity::class.java))
            finish()
        }
    }

    private fun getDataStore(){
        dataTransaksi = DataStoreTransaksi(this)
        dataTransaksi.transaksiTotal.asLiveData().observe(this) {
            transaksiTotal = it
            binding.tvTotal.text = it.toString()
        }
        dataTransaksi.transaksiJenisBank.asLiveData().observe(this) {
            pilihBank = it
            binding.tvJenisBank.text = it.toString()
        }

        dataTransaksi.transaksiNoRekening.asLiveData().observe(this) {
            noRekeningTransaksi = it
            binding.tvRekening.text = it.toString()
        }

        dataTransaksi.transaksiTipeTransaksi.asLiveData().observe(this) {
            metodePembayaran = it
//            binding.tvGetTipeTransaksi.text = it.toString()
        }

        dataTransaksi.transaksiNamaPenerima.asLiveData().observe(this) {
            namaPenerima = it
            binding.tvNamaPenerima.text = it.toString()
        }

        dataTransaksi.codeSwift.asLiveData().observe(this) {
            codeSwift = it
//            binding.tvGetSwift.text = it.toString()
        }
    }
}