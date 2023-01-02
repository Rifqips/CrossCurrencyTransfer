package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPembayaranTransferBinding

class PembayaranTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPembayaranTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelanjutnya.setOnClickListener {
            startActivity(Intent(this, PengirimTransferActivity::class.java))
        }

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, BankInternationalActivity::class.java))
        }
    }
}