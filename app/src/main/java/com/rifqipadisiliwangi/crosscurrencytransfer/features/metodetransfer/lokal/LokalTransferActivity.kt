package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityBankLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLokalTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.BankInternationalActivity

class LokalTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLokalTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokalTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, HomeBottomActivity::class.java))
        }

        binding.btnSelanjutnya.setOnClickListener {
            startActivity(Intent(this, BankLokalActivity::class.java))
        }
    }
}