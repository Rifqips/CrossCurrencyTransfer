package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPembayaranTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPembayaranTransferLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.PengirimTransferActivity

class PembayaranTransferLokalActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPembayaranTransferLokalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranTransferLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelanjutnya.setOnClickListener {
            startActivity(Intent(this, PengirimTransferLokalActivity::class.java))
        }
    }
}