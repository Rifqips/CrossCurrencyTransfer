package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPengirimTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPengirimTransferLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.SuksesTransferActivity

class PengirimTransferLokalActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPengirimTransferLokalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengirimTransferLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBayarSekarang.setOnClickListener {
            startActivity(Intent(this, SuksesTransferLokalActivity::class.java))
        }
    }
}