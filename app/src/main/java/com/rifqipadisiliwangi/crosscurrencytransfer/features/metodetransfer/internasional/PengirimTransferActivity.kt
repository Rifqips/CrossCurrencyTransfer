package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPengirimTransferBinding

class PengirimTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPengirimTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengirimTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBayarSekarang.setOnClickListener {
            startActivity(Intent(this, SuksesTransferActivity::class.java))
        }

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, PembayaranTransferActivity::class.java))
        }
    }
}