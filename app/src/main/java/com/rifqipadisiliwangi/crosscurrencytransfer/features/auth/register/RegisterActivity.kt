package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register

import android.content.Intent
import android.graphics.Color

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.asLiveData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreUser
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityRegisterBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    lateinit var dataStoreUser : DataStoreUser
    var phoneNumber = ""

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreUser = DataStoreUser(this)
        validate()
        dataStore()

    }
    private fun dataStore(){

        dataStoreUser.noPhone.asLiveData().observe(this){
            phoneNumber = it
            binding.etNoHp.hint = it.toString()
        }

        binding.btnKirim.setOnClickListener {
            phoneNumber = "62${binding.etNoHp.text.toString()}"
            GlobalScope.launch {
                dataStoreUser.saveData(phoneNumber,"","")
            }
            startActivity(Intent(this, DataDiriActivity::class.java))
        }

    }

    private fun validate(){

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.etNoHp.doOnTextChanged { text, start, before, count ->
            if (binding.etNoHp.text.toString().isEmpty() ) {
                binding.tvWarningNohp.text = "Anda harus mengisi bagian ini"
                binding.btnKirim.setBackgroundColor(Color.rgb(216,216,216))
            }else if (binding.etNoHp.text.toString().length < 10) {
                binding.tvWarningNohp.isVisible = true
                binding.btnKirim.setBackgroundColor(Color.rgb(216,216,216))
            }else {
                binding.tvWarningNohp.isVisible = false
                binding.btnKirim.setBackgroundColor(Color.rgb(32, 117, 243))
                binding.btnKirim.isEnabled = true
            }

        }
    }
}