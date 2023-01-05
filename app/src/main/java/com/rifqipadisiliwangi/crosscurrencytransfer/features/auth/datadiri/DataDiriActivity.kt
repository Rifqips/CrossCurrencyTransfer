package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityDataDiriBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity

class DataDiriActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityDataDiriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataDiriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterDokumen = ArrayAdapter.createFromResource(this, R.array.listDokumen, android.R.layout.simple_spinner_item)
        adapterDokumen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDokumen.adapter = adapterDokumen
        binding.spinnerDokumen.onItemSelectedListener = this
        binding.etEmail.doOnTextChanged { text, start, before, count ->
            if ( binding.etEmail.text.toString().isEmpty() ) {
                binding.tvWarningEmail.text = "Anda harus mengisi bagian ini"
                binding.tvWarningEmail.isVisible = true
            }

            binding.tvWarningEmail.isVisible = !isValidEmail(binding.etEmail.text.toString())
        }

        binding.etMasukkanDokumen.doOnTextChanged { text, start, before, count ->
            if (binding.etMasukkanDokumen.text.toString().isEmpty())
                binding.tvWarningDokumen.isVisible = true
        }

        binding.etNamaDepan.doOnTextChanged { text, start, before, count ->
            if (binding.etNamaDepan.text.toString().isEmpty())
                binding.tvWarningNamaDepan.isVisible = true
        }

        binding.etNamaBelakang.doOnTextChanged { text, start, before, count ->
            if (binding.etNamaBelakang.text.toString().isEmpty())
                binding.tvWarningNamaBelakang.isVisible = true
        }

        binding.btnLanjut.setOnClickListener {
            startActivity(Intent(this,VerifikasiActivity::class.java))
        }
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target))   false
        else    Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        when (text) {
            "Passport" ->   binding.etMasukkanDokumen.filters = arrayOf(InputFilter.LengthFilter(16))
            "KTP" ->    binding.etMasukkanDokumen.filters = arrayOf(InputFilter.LengthFilter(16))
            "SIM" ->    binding.etMasukkanDokumen.filters = arrayOf(InputFilter.LengthFilter(12))
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}