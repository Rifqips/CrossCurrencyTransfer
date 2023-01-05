package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLupaPasswordPwBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity

class LupaPasswordActivityPw : AppCompatActivity() {
    private lateinit var binding : ActivityLupaPasswordPwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKirim.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.etKatasandi.doOnTextChanged { text, start, before, count ->
            if (binding.etKatasandi.text.toString().isEmpty()) {
                binding.tvWarningKataSandi.text = "Anda harus mengisi bagian ini"
                binding.tvWarningKataSandi.isVisible = true
            }else if (binding.etKatasandi.text.toString().length > 7 &&
                binding.etKatasandi.text.toString().contains("[A-Z]".toRegex()) &&
                binding.etKatasandi.text.toString().contains("[a-z]".toRegex()) &&
                binding.etKatasandi.text.toString().contains("[0-9]".toRegex()) &&
                binding.etKatasandi.text.toString().contains("[@*#]".toRegex()) ) {

                binding.tvWarningKataSandi.text = "Kata sandi harus berisi huruf besar, angka dan simbol (@ * # &)"
                binding.tvWarningKataSandi.isVisible = true
            }else
                binding.tvWarningKataSandi.isVisible = false

        }
        binding.etKonfirmasiKatasandi.doOnTextChanged { text, start, before, count ->
            if ( (binding.etKonfirmasiKatasandi.text.toString() == binding.etKatasandi.text.toString()) ){
                binding.btnKirim.isEnabled = true
                binding.tvWarningKataSandiBaru.isVisible = false
            }else {
                binding.tvWarningKataSandiBaru.isVisible = true
                binding.btnKirim.isEnabled = false
            }
        }
    }
}