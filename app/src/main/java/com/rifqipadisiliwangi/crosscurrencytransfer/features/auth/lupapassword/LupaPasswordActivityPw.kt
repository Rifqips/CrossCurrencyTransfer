package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLupaPasswordPwBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity

class LupaPasswordActivityPw : AppCompatActivity() {
    var katasandi = "hidePassword"
    var konfirmasiKatasandi = "hidePassword"
    private lateinit var binding : ActivityLupaPasswordPwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibShowKataSandiBaru.setOnClickListener {
            if (katasandi == "hidePassword")  {
                binding.etKatasandi.transformationMethod = PasswordTransformationMethod.getInstance()
                katasandi = "showPassword"
            }else {
                binding.etKatasandi.transformationMethod = HideReturnsTransformationMethod.getInstance()
                katasandi = "hidePassword"
            }
        }

        binding.ibShowKonfirmasiKataSandi.setOnClickListener {
            if (konfirmasiKatasandi == "hidePassword")  {
                binding.etKonfirmasiKatasandiBaru.transformationMethod = PasswordTransformationMethod.getInstance()
                konfirmasiKatasandi = "showPassword"
            }else {
                binding.etKonfirmasiKatasandiBaru.transformationMethod = HideReturnsTransformationMethod.getInstance()
                konfirmasiKatasandi = "hidePassword"
            }
        }

        binding.btnKirim.setOnClickListener {
            val builder = AlertDialog.Builder(this).create()
            val view = layoutInflater.inflate(R.layout.dialog_katasandi_berhasil, null)
            val button = view.findViewById<Button>(R.id.btnMasukSekarang)
//            startActivity(Intent(this, LoginActivity::class.java))
            builder.setView(view)
            button.setOnClickListener {
                builder.dismiss()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
        binding.etKatasandi.doOnTextChanged { text, start, before, count ->

            if (binding.etKatasandi.text.toString().isEmpty()) {
                binding.tvWarningKataSandi.text = "Anda harus mengisi bagian ini"
                binding.btnKirim.setBackgroundColor(Color.rgb(216,216,216))
                binding.tvWarningKataSandi.isVisible = true
                binding.btnKirim.isEnabled = false

            }else if ( !(binding.etKatasandi.text.toString().length > 7 &&
                binding.etKatasandi.text.toString().contains("[A-Z]".toRegex()) &&
                binding.etKatasandi.text.toString().contains("[a-z]".toRegex()) &&
                binding.etKatasandi.text.toString().contains("[0-9]".toRegex()) &&
                binding.etKatasandi.text.toString().contains("[@*#]".toRegex()) ) ) {

                binding.tvWarningKataSandi.text = "Kata sandi harus berisi huruf besar, angka dan simbol \n" +
                        "(@ * # &)"
                binding.btnKirim.setBackgroundColor(Color.rgb(216,216,216))
                binding.tvWarningKataSandi.isVisible = true
                binding.btnKirim.isEnabled = false
            }else if (binding.etKonfirmasiKatasandiBaru.text.toString() == binding.etKatasandi.text.toString()) {
                binding.tvWarningKataSandiBaru.isVisible = false
                binding.btnKirim.setBackgroundColor(Color.rgb(32, 117, 243))
                binding.btnKirim.isEnabled = true

            }else
                binding.tvWarningKataSandi.isVisible = false

        }
        binding.etKonfirmasiKatasandiBaru.doOnTextChanged { text, start, before, count ->
            if ( binding.etKonfirmasiKatasandiBaru.text.toString().isEmpty() ){
                binding.tvWarningKataSandiBaru.text = "Anda harus mengisi bagian ini"
                binding.btnKirim.setBackgroundColor(Color.rgb(216,216,216))
                binding.tvWarningKataSandiBaru.isVisible = true
                binding.btnKirim.isEnabled = false

            }else if (binding.etKonfirmasiKatasandiBaru.text.toString() == binding.etKatasandi.text.toString()) {
                binding.tvWarningKataSandiBaru.isVisible = false
                binding.btnKirim.setBackgroundColor(Color.rgb(32,117,243) )
                binding.btnKirim.isEnabled = true
            }
            else {
                binding.tvWarningKataSandiBaru.text = "Kata sandi harus sama"
                binding.btnKirim.setBackgroundColor(Color.rgb(216,216,216))
                binding.tvWarningKataSandiBaru.isVisible = true
                binding.btnKirim.isEnabled = false
            }
        }
    }
}
