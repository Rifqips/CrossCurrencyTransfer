package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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

        binding.btnKirim.setOnClickListener {
            startActivity(Intent(this, LupaPasswordActivityPw::class.java))
        }
        binding.etEmail.doOnTextChanged { text, start, before, count ->
            if (binding.etEmail.text.toString().isEmpty()){
                binding.tvWarningEmail.text = "Anda harus mengisi bagian ini"
                binding.tvWarningEmail.isVisible = true
            }else if ( !isValidEmail(binding.etEmail.text.toString()) ) {
                binding.tvWarningEmail.text = "Format email salah"
                binding.tvWarningEmail.isVisible = true
                binding.btnKirim.isEnabled = false
            }
            else {
                binding.tvWarningEmail.isInvisible = true
                binding.btnKirim.isEnabled = true
            }

        }

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}