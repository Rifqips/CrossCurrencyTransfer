package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLoginBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword.LupaPasswordActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register.RegisterActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class LoginActivity : AppCompatActivity() {
    var password = "hidePassword"
    var enableButtonPassword = ""
    var enableButtonEmail = "l"
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLupaPassword.setOnClickListener {
            startActivity(Intent(this, LupaPasswordActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, PinActivity::class.java))
        }
        binding.tvPerDescSatu.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.etEmail.doOnTextChanged { text, start, before, count ->
            binding.btnLogin.isEnabled = false

                if (binding.etEmail.text.toString().isEmpty()) {
                    binding.tvWarningEmail.text = "Anda harus mengisi bagian ini"
                    binding.tvWarningEmail.isVisible = true
                    enableButtonEmail = "v"
                }else if (!isValidEmail(binding.etEmail.text.toString())) {
                    binding.tvWarningEmail.text = "Format email salah"
                    binding.tvWarningEmail.isVisible = true
                    enableButtonEmail = "q"
                }else if (isValidEmail(binding.etEmail.text.toString()) ) {
                    binding.tvWarningEmail.isVisible = false
                    enableButtonEmail = "berhasil"
                }
            if (enableButtonEmail == enableButtonPassword) {
                binding.btnLogin.isEnabled = true
                enableButtonEmail = "resetEmail"
            }
        }

        binding.ibShowPassword.setOnClickListener {
            if (password == "hidePassword")  {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                password = "showPassword"
            }else {
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                password = "hidePassword"
            }
        }

        binding.etPassword.doOnTextChanged { text, start, before, count ->
            binding.btnLogin.isEnabled = false

            if (binding.etPassword.text.toString().isEmpty()) {
                    binding.tvWarningKataSandi.text = "Anda harus mengisi bagian ini"
                binding.tvWarningKataSandi.isVisible = true
                enableButtonPassword = "p"
            }
            else if ( !(binding.etPassword.text.toString().length >= 8 &&
                        binding.etPassword.text.toString().contains("[A-Z]".toRegex()) &&
                        binding.etPassword.text.toString().contains("[a-z]".toRegex()) &&
                        binding.etPassword.text.toString().contains("[0-9]".toRegex()) &&
                        binding.etPassword.text.toString().contains("[@*#&]".toRegex()))
            ){
                binding.tvWarningKataSandi.text = "Kata sandi harus berisi huruf besar, angka dan simbol (@ * # &)"
                binding.tvWarningKataSandi.isVisible = true
            }
            else if (binding.etPassword.text.toString().length >= 8 &&
                binding.etPassword.text.toString().contains("[A-Z]".toRegex()) &&
                binding.etPassword.text.toString().contains("[a-z]".toRegex()) &&
                binding.etPassword.text.toString().contains("[0-9]".toRegex()) &&
                binding.etPassword.text.toString().contains("[@*#&]".toRegex()) ) {
                    binding.tvWarningKataSandi.isVisible = false
                    enableButtonPassword = "berhasil"
            }
            if (enableButtonEmail == enableButtonPassword){
                binding.btnLogin.isEnabled = true
                enableButtonPassword = "resetPassword"
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
