package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityDataDiriBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity
import java.util.Calendar

class DataDiriActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDataDiriBinding
    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DATE)
    var validasiButton = false
    var validasiEmail = ""
    var validasiDokumen = ""
    var validasiNamaDepan = ""
    var validasiNamaBelakang = ""
    var validasiTglLahir = ""
    var validasiAlamat = ""
    var katasandi = "hidePassword"
    var konfirmasiKatasandi = "hidePassword"
    var validasiKatasandi = ""
    var validasiKonfirmasiKatasandi = ""
    var ceklisSyaratdanKetentuan = "benar"
    internal val arrayDokumen = arrayOf("Pilih Tipe Dokumen", "Passport", "KTP", "SIM")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataDiriBinding.inflate(layoutInflater)
        setContentView(binding.root)
                // menggunakan simple spinner
//        binding.spinnerDokumen.onItemSelectedListener = this
//        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayDokumen)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinnerDokumen.adapter = adapter
                // selesai
        binding.etEmail.doOnTextChanged { text, start, before, count ->
            if ( binding.etEmail.text.toString().isEmpty() ) {
                binding.tvWarningEmail.text = "Anda harus mengisi bagian ini"
                binding.tvWarningEmail.isVisible = true
                validasiEmail = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false
            }else if (!isValidEmail(binding.etEmail.text.toString()) ) {
                binding.tvWarningEmail.text = "Format email salah"
                binding.tvWarningEmail.isVisible = true
                validasiEmail = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false

            }else if (isValidEmail(binding.etEmail.text.toString()) ) {
                binding.tvWarningEmail.isVisible = false
                validasiEmail = "benar"
                validasiButton = true

                if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
                    validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
                    && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
                    binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
                    binding.btnLanjut.isEnabled = true
                }
            }
        }

        binding.spinnerDokumen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val validasiTipeDokumen = arrayDokumen[position]
                if (validasiTipeDokumen == "Passport" || validasiTipeDokumen == "KTP" ) {
                    binding.etMasukkanDokumen.isEnabled = true
                    binding.etMasukkanDokumen.text.clear()
                    binding.etMasukkanDokumen.filters = arrayOf(InputFilter.LengthFilter(16))
                }else if (validasiTipeDokumen == "SIM") {
                    binding.etMasukkanDokumen.isEnabled = true
                    binding.etMasukkanDokumen.text.clear()
                    binding.etMasukkanDokumen.filters = arrayOf(InputFilter.LengthFilter(12))
                }else if (validasiTipeDokumen == "Pilih Tipe Dokumen") {
                    binding.etMasukkanDokumen.isEnabled = false
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        val customAdapter = AdapterDokumen(applicationContext, arrayDokumen)
        binding.spinnerDokumen.adapter = customAdapter

        binding.etMasukkanDokumen.doOnTextChanged { text, start, before, count ->
            if (binding.etMasukkanDokumen.text.toString().isEmpty()) {
                binding.tvWarningDokumen.isVisible = true
                validasiDokumen = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false
            }else {
                binding.tvWarningDokumen.isVisible = true
                validasiDokumen = "benar"
                validasiButton = true

                if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
                    validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
                    && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
                    binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
                    binding.btnLanjut.isEnabled = true
                }
            }
        }

        binding.etNamaDepan.doOnTextChanged { text, start, before, count ->
            if (binding.etNamaDepan.text.toString().isEmpty()) {
                binding.tvWarningNamaDepan.isVisible = true
                validasiNamaDepan = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false
            }else {
                binding.tvWarningNamaDepan.isVisible = false
                validasiNamaDepan = "benar"
                validasiButton = true

                if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
                    validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
                    && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
                    binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
                    binding.btnLanjut.isEnabled = true
                }
            }
        }

        binding.etNamaBelakang.doOnTextChanged { text, start, before, count ->
            if (binding.etNamaBelakang.text.toString().isEmpty()) {
                binding.tvWarningNamaBelakang.isVisible = true
                validasiNamaBelakang = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false
            }else {
                binding.tvWarningNamaBelakang.isVisible = false
                validasiNamaBelakang = "benar"
                validasiButton = true

                if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
                    validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
                    && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
                    binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
                    binding.btnLanjut.isEnabled = true
                }
            }
        }

        binding.ibCalender.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                var bulan = monthOfYear + 1
                binding.tvIsiTgllahir.text = "$dayOfMonth/$bulan/$year"
                validasiTglLahir = "benar"
            }, year,month,day )

            val minDay = 1
            val minMonth = 0
            val minYear = 2004

            calender.set(minYear, minMonth, minDay)
            dpd.datePicker.minDate = calender.timeInMillis
            dpd.show()
        }

        binding.etAlamat.setOnClickListener {
            if (binding.etAlamat.text.toString().isEmpty() ) {
                binding.tvWarningAlamat.isVisible = true
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false
            } else {
                binding.tvWarningAlamat.isVisible = true
                validasiAlamat = "benar"
                validasiButton = true

                if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
                    validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
                    && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
                    binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
                    binding.btnLanjut.isEnabled = true
                }
            }
        }

        binding.ibShowPassword.setOnClickListener {
            if (katasandi == "hidePassword")  {
                binding.etKatasandi.transformationMethod = PasswordTransformationMethod.getInstance()
                katasandi = "showPassword"
            }else {
                binding.etKatasandi.transformationMethod = HideReturnsTransformationMethod.getInstance()
                katasandi = "hidePassword"
            }
        }

        binding.etKatasandi.doOnTextChanged { text, start, before, count ->
            if (binding.etKatasandi.text.toString().isEmpty() ) {
                binding.tvWarningKatasandiDatadiri.isVisible = true
                validasiKatasandi = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false
            }else if ( !(binding.etKatasandi.text.toString().length > 7 &&
                        binding.etKatasandi.text.toString().contains("[A-Z]".toRegex()) &&
                        binding.etKatasandi.text.toString().contains("[a-z]".toRegex()) &&
                        binding.etKatasandi.text.toString().contains("[0-9]".toRegex()) &&
                        binding.etKatasandi.text.toString().contains("[@*#]".toRegex()) ) ) {
                binding.tvWarningKatasandiDatadiri.text = "Kata sandi harus berisi huruf besar, angka dan simbol \n" +
                        "(@ * # &)"
                binding.tvWarningKatasandiDatadiri.isVisible = true
                validasiKatasandi = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false

            }else if (binding.etKatasandi.text.toString() == binding.etkonfirmasiKatasandiDatadiri.text.toString() ) {
                binding.tvWarningKatasandiDatadiri.isVisible = false
                validasiKatasandi = "benar"
                validasiButton = true

                if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
                    validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
                    && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
                    binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
                    binding.btnLanjut.isEnabled = true
                }
            }
        }

        binding.ibShowKonfirmasiKatasandiDatadiri.setOnClickListener {
            if (konfirmasiKatasandi == "hidePassword")  {
                binding.etkonfirmasiKatasandiDatadiri.transformationMethod = PasswordTransformationMethod.getInstance()
                konfirmasiKatasandi = "showPassword"
            }else {
                binding.etkonfirmasiKatasandiDatadiri.transformationMethod = HideReturnsTransformationMethod.getInstance()
                konfirmasiKatasandi = "hidePassword"
            }
        }

        binding.etkonfirmasiKatasandiDatadiri.doOnTextChanged { text, start, before, count ->
            if (binding.etkonfirmasiKatasandiDatadiri.text.toString().isEmpty() ) {
                binding.tvWarningkonfirmasiKatasandiDatadiri.isVisible = true
                validasiKonfirmasiKatasandi = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false

            }else if ( !(binding.etKatasandi.text.toString().length > 7 &&
                        binding.etKatasandi.text.toString().contains("[A-Z]".toRegex()) &&
                        binding.etKatasandi.text.toString().contains("[a-z]".toRegex()) &&
                        binding.etKatasandi.text.toString().contains("[0-9]".toRegex()) &&
                        binding.etKatasandi.text.toString().contains("[@*#]".toRegex()) ) ) {
                binding.tvWarningkonfirmasiKatasandiDatadiri.text = "Kata sandi harus berisi huruf besar, angka dan simbol \n" +
                        "(@ * # &)"
                binding.tvWarningkonfirmasiKatasandiDatadiri.isVisible = true
                validasiKonfirmasiKatasandi = "salah"
                validasiButton = false

                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false

            }else if (binding.etKatasandi.text.toString() == binding.etkonfirmasiKatasandiDatadiri.text.toString() ) {
                binding.tvWarningkonfirmasiKatasandiDatadiri.isVisible = false
                validasiKonfirmasiKatasandi = "benar"
                validasiButton = true

                if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
                    validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
                    && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
                    binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
                    binding.btnLanjut.isEnabled = true
                }
            }
        }

        binding.rbPrivacyPolicy.setOnClickListener {
            if (ceklisSyaratdanKetentuan == "benar") {
                ceklisSyaratdanKetentuan = "salah"
                if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
                    validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
                    && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
                    binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
                    binding.btnLanjut.isEnabled = true
                }

            }else if (ceklisSyaratdanKetentuan == "salah") {
                binding.rgSyaratdanKetentuan.clearCheck()
                binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
                binding.btnLanjut.isEnabled = false
                ceklisSyaratdanKetentuan = "benar"
            }
        }
//        if (validasiEmail == "benar" && validasiDokumen == "benar" && validasiNamaDepan == "benar" &&
//            validasiNamaBelakang == "benar" && validasiTglLahir == "benar" && validasiAlamat == "benar"
//            && validasiKatasandi == "benar" && validasiKonfirmasiKatasandi == "benar") {
//            binding.btnLanjut.setBackgroundColor(Color.rgb(32,117,243))
//            binding.btnLanjut.isEnabled = true
//        }else {
//            binding.btnLanjut.setBackgroundColor(Color.rgb(216,216,216))
//            binding.btnLanjut.isEnabled = false
//        }
//            validasiEmail = ""
//        var validasiDokumen = ""
//        var validasiNamaDepan = ""
//        var validasiNamaBelakang = ""
//        var validasiTglLahir = ""
//        var validasiAlamat = ""
//        var katasandi = "hidePassword"
//        var konfirmasiKatasandi = "hidePassword"
//        var validasiKatasandi = ""
//        var validasiKonfirmasiKatasandi

        binding.btnLanjut.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target))   false
        else    Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
            // menggunakan simple spinner
//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        val text: String = parent?.getItemAtPosition(position).toString()
//        when (text) {
//            "Passport" ->   binding.etMasukkanDokumen.filters = arrayOf(InputFilter.LengthFilter(16))
//            "KTP" ->    binding.etMasukkanDokumen.filters = arrayOf(InputFilter.LengthFilter(16))
//            "SIM" ->    binding.etMasukkanDokumen.filters = arrayOf(InputFilter.LengthFilter(12))
//        }
//    }
        // selesai
}