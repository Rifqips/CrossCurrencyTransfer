package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.edit
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.dataStore
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivitySuksesTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SuksesTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySuksesTransferBinding
    private lateinit var dataTransaksi : DataStoreTransaksi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuksesTransferBinding.inflate(layoutInflater)
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