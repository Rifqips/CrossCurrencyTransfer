package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityBankLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.InternationalTransferActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.PembayaranTransferActivity

class BankLokalActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBankLokalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, LokalTransferActivity::class.java))
        }

        binding.btnSelanjutnya.setOnClickListener {
            startActivity(Intent(this, PembayaranTransferLokalActivity::class.java))
        }
    }
}