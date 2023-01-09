package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword

import android.content.Intent
<<<<<<< HEAD
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
=======
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.rifqipadisiliwangi.crosscurrencytransfer.R
>>>>>>> dev-roni
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
            val builder = AlertDialog.Builder(this).create()
            val view = layoutInflater.inflate(R.layout.dialog_email_berhasil, null)
            val button = view.findViewById<Button>(R.id.btnCekEmail)

            builder.setView(view)
            button.setOnClickListener {
                builder.dismiss()
                startActivity(Intent(this,LupaPasswordActivityPw::class.java))
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
//            startActivity(Intent(this, LupaPasswordActivityPw::class.java))
        }
        binding.etEmail.doOnTextChanged { text, start, before, count ->
            if (binding.etEmail.text.toString().isEmpty()){
                binding.tvWarningEmail.text = "Anda harus mengisi bagian ini"
                binding.tvWarningEmail.isVisible = true
                binding.btnKirim.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnKirim.isEnabled = false

            }else if ( !isValidEmail(binding.etEmail.text.toString()) ) {
                binding.tvWarningEmail.text = "Format email salah"
                binding.tvWarningEmail.isVisible = true
                binding.btnKirim.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnKirim.isEnabled = false
            }
            else {
                binding.tvWarningEmail.isInvisible = true
                binding.btnKirim.isEnabled = true
                binding.btnKirim.setBackgroundColor(Color.rgb(32,117,243) )
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