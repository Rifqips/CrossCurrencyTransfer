package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPasscoddeBinding

class PasscoddeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPasscoddeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasscoddeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}