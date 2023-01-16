package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivitySuksesTransferLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SuksesTransferLokalActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySuksesTransferLokalBinding
    private lateinit var dataTransaksi : DataStoreTransaksi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuksesTransferLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataTransaksi = DataStoreTransaksi(this)

        binding.btnBack.setOnClickListener {
            GlobalScope.launch {
                dataTransaksi.clearData()
            }
            startActivity(Intent(this, HomeBottomActivity::class.java))
            finish()
        }
    }
}