package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityVerifikasiBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity

class VerifikasiActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVerifikasiBinding
    var kodeOtp = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cd0.setOnClickListener {
            kodeOtp += "0"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd1.setOnClickListener {
            kodeOtp += "1"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd2.setOnClickListener {
            kodeOtp += "2"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd3.setOnClickListener {
            kodeOtp += "3"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd4.setOnClickListener {
            kodeOtp += "4"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd5.setOnClickListener {
            kodeOtp += "5"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd6.setOnClickListener {
            kodeOtp += "6"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd7.setOnClickListener {
            kodeOtp += "7"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd8.setOnClickListener {
            kodeOtp += "8"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.cd9.setOnClickListener {
            kodeOtp += "9"
            binding.tvKodeOtp.text = kodeOtp
            if (kodeOtp.length == 6)    startActivity(Intent(this,DataDiriActivity::class.java))
        }
        binding.ivDeleteOtp.setOnClickListener {
            kodeOtp = kodeOtp.substring(0,kodeOtp.length - 1)
            binding.tvKodeOtp.text = kodeOtp
        }

    }
}