package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import android.app.DatePickerDialog
import android.content.DialogInterface
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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.register.RegisterApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityDataDiriBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity
import java.util.Calendar

class DataDiriActivity : AppCompatActivity(),RegisterView {

    private lateinit var binding : ActivityDataDiriBinding

    private val presenter = RegisterPresenter(RegisterApi())
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
        presenter.onAttach(this)
        postRegister()

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
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target))   false
        else    Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun onLoading() {
        binding.progressBar.isVisible = true
    }

    override fun onFinishedLoading() {
        binding.progressBar.isVisible = false
    }

    override fun onError(code: Int, message: String) {
        binding.progressBar.isVisible = true
        Toast.makeText(this, "Error When Login", Toast.LENGTH_SHORT).show()
    }

    override fun onErrorPassword(visible: Boolean, message: String) {
        Toast.makeText(this, "Error Password When Login", Toast.LENGTH_SHORT).show()
    }

    override fun resetPasswordError() {
        TODO("Not yet implemented")
    }

    override fun onSuccessRegister() {
        startActivity(Intent(this, LoginActivity::class.java))
        presenter.register("","",0,"","","","",0,"","",)

        Toast.makeText(this, "Success Register", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessGetUser(user: RegisterDataItem) {
        AlertDialog.Builder(this)
            .setMessage("user -> $user")
            .setPositiveButton("Ok", this::dialogClickListener)
            .setNegativeButton("Cancel", this::dialogClickListener)
            .create()
            .show()
    }

    private fun postRegister(){
        binding.btnLanjut.setOnClickListener {
            presenter.register(
                binding.etEmail.text.toString(),
                binding.spinnerDokumen.toString(),
                binding.etMasukkanDokumen.text.toString().toInt(),
                binding.etNamaDepan.text.toString(),
                binding.etNamaBelakang.text.toString(),
                binding.etTempatlahir.text.toString(),
                binding.etAlamat.text.toString(),
                binding.etPhone.text.toString().toInt(),
                binding.etKatasandi.text.toString(),
                binding.rbPria.text.toString()

            )
        }
    }

    private fun dialogClickListener(dialogInterface: DialogInterface, button: Int) {
        when (button) {
            DialogInterface.BUTTON_NEGATIVE -> {}
            DialogInterface.BUTTON_POSITIVE -> {}
            DialogInterface.BUTTON_NEUTRAL -> {}
        }
    }
}