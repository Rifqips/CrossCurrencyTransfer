package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivitySuksesTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class SuksesTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySuksesTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuksesTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, HomeBottomActivity::class.java))
            finish()
        }
    }
}