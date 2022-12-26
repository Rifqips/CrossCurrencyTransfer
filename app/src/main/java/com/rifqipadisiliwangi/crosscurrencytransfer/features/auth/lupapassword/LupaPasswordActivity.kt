package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLupaPasswordBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity

class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLupaPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}