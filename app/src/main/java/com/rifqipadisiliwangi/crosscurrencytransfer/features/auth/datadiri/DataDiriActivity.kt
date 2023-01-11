package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.register.RegisterApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityDataDiriBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.helper.makeClearableEditText
import org.w3c.dom.Text
import java.util.Calendar

class DataDiriActivity : AppCompatActivity(),RegisterView, AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityDataDiriBinding

    var tipeDokumen = arrayOf("Passport", "KTP", "SIM")
    private val presenter = RegisterPresenter(RegisterApi())
    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DATE)
    var validasiTglLahir = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataDiriBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onAttach(this)
        postRegister()
        loadSpiner()
        clearValidasi()
        validasiEditText()

        binding.ibCalender.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val bulan = monthOfYear + 1
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

    override fun onLoading() {
        binding.progressBar.isVisible = true
    }

    override fun onFinishedLoading() {
        binding.progressBar.isVisible = false
    }

    override fun onError(code: Int, message: String) {
        when(code){
            1 -> {
                binding.tvWarningKatasandiDatadiri.text = message
            }
            2 -> {
                binding.tvWarningKatasandiDatadiri.text = message
                binding.tvWarningKatasandiDatadiri.isVisible = false
            }
            else -> {binding.tvWarningKatasandiDatadiri.isVisible = false}
        }

    }

    override fun onErrorEmail(code: Int, message: String) {
        when (code){
            2 -> {
                binding.tvWarningEmail.text = message
            }
            3 -> {
                binding.tvWarningEmail.text = message
                binding.tvWarningEmail.isVisible = false
            }else ->{
                binding.tvWarningEmail.text = "Format email salah"

            }
        }
    }

    override fun onErrorPassword(visible: Boolean, message: String) {
        Toast.makeText(this, "Error Password When Login", Toast.LENGTH_SHORT).show()
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
    private fun clearValidasi(){

        addRightCancelDrawable(binding.etEmail)
        addRightCancelDrawable(binding.etMasukkanDokumen)
        addRightCancelDrawable(binding.etNamaDepan)
        addRightCancelDrawable(binding.etNamaBelakang)
        addRightCancelDrawable(binding.etTempatlahir)
        addRightCancelDrawable(binding.etAlamat)
        binding.etEmail.makeClearableEditText(null, null)
        binding.etMasukkanDokumen.makeClearableEditText(null, null)
        binding.etNamaDepan.makeClearableEditText(null, null)
        binding.etNamaBelakang.makeClearableEditText(null, null)
        binding.etTempatlahir.makeClearableEditText(null, null)
        binding.etAlamat.makeClearableEditText(null, null)

    }

    private fun validasiEditText(){

        binding.etKatasandi.addTextChangedListener {
            presenter.validateCredential(
                it.toString()
            )
        }

        binding.etEmail.addTextChangedListener {
            presenter.validateEmail(
                it.toString()
            )
        }

        binding.etMasukkanDokumen.addTextChangedListener{ binding.tvWarningDokumen.isVisible = it == null }
        binding.etNamaDepan.addTextChangedListener { binding.tvWarningNamaDepan.isVisible = it == null }
        binding.etNamaBelakang.addTextChangedListener { binding.tvWarningNamaBelakang.isVisible = it == null }
        binding.etTempatlahir.addTextChangedListener { binding.tvWarningTtl.isVisible = it == null }
        binding.etAlamat.addTextChangedListener { binding.tvWarningAlamat.isVisible = it == null }
    }

    private fun loadSpiner(){
        binding.btnLanjut.isVisible = false
        binding.btnLanjutInvisible.isVisible = true
        var aa = ArrayAdapter(this, R.layout.spinner_right_aligned, tipeDokumen)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding.mySpinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@DataDiriActivity
            prompt = "Pilih Dokumen"
            gravity = Gravity.CENTER

        }

        val ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        ll.setMargins(10, 40, 10, 10)

        aa = ArrayAdapter(this, R.layout.spinner_right_aligned, tipeDokumen)
        aa.setDropDownViewResource(R.layout.spinner_right_aligned)
    }

    private fun postRegister(){
        binding.btnLanjut.setOnClickListener {
            presenter.register(
                binding.etEmail.text.toString(),
                binding.mySpinner.toString(),
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (view?.id) {
            1 -> showToast(message = "")
            else -> {
                Snackbar.make(binding.btnLanjut, "Dokumen Yang dipilih : ${tipeDokumen[position]}", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.btnLanjut.isVisible = true
        binding.btnLanjutInvisible.isVisible = false
    }

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()

    }

    private fun addRightCancelDrawable(editText: EditText) {
        val cancel = ContextCompat.getDrawable(this, R.drawable.ic_baseline_cancel_24)
        cancel?.setBounds(0,0, cancel.intrinsicWidth, cancel.intrinsicHeight)
        editText.setCompoundDrawables(null, null, cancel, null)
    }
}