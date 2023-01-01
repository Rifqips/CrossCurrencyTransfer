package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        binding.etKonfirmasiKatasandi.doOnTextChanged { text, start, before, count ->
            if (binding.etKonfirmasiKatasandi.text.toString().length > 8 &&
                (binding.etKonfirmasiKatasandi.text.toString() == binding.etKatasandi.text.toString()) &&
                binding.etKonfirmasiKatasandi.text.toString().length < 16 ){
                    binding.btnKirim.isEnabled = true
                binding.tvWarningKataSandiBaru.isVisible = false
            }else {
                binding.tvWarningKataSandiBaru.isVisible = true
                binding.btnKirim.isEnabled = false
            }
        }
    }
}