package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityRegisterBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnkirim.setOnClickListener {
            startActivity(Intent(this, VerifikasiActivity::class.java))
        }
        binding.etNoHp.doOnTextChanged { text, start, before, count ->
            binding.tvWarningNohp.isVisible = binding.etNoHp.text.toString().length < 10
            binding.btnkirim.isEnabled = binding.etNoHp.text.toString().length >= 10

        }
    }
}

//val adapter = ArrayAdapter.createFromResource(this, R.array.listNegara, android.R.layout.simple_spinner_item)
//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//binding.spinnerNegara.adapter = adapter