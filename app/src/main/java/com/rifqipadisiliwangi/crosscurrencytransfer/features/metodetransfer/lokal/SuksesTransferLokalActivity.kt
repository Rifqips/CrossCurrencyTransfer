package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivitySuksesTransferLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class SuksesTransferLokalActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySuksesTransferLokalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuksesTransferLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, HomeBottomActivity::class.java))
            finish()
        }
    }
}