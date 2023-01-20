package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityBankLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.InternationalTransferActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.PembayaranTransferActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BankLokalActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityBankLokalBinding
    var bankAmerika = arrayOf("Pilih Bank","Mandiri", "BCA", "Cimb Niaga", "BRI", "BNI")
    private lateinit var dataTransaksi : DataStoreTransaksi
    var transaksiTotal = ""
    var pilihBank = ""
    var noRekeningTransaksi = ""
    var namaPenerima = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSpiner()
        userTranser()
        dataTransaksi = DataStoreTransaksi(this)

        dataTransaksi.transaksiTotal.asLiveData().observe(this) {
            transaksiTotal = it
            binding.tvSaldoTotal.text = it.toString()
        }

        binding.btnSelanjutnya.setOnClickListener {
            transaksiTotal = binding.tvSaldoTotal.text.toString()
            pilihBank = binding.mySpinner.selectedItem.toString()
            noRekeningTransaksi = binding.etNorekening.text.toString()
            namaPenerima = binding.etNamaPenerima.text.toString()
            GlobalScope.launch {
                dataTransaksi.saveData(id = "", pilihBank, namaPenerima, noRekeningTransaksi, tipeTransaksi = "", transaksiTotal,"")
            }
            startActivity(Intent(this, PembayaranTransferLokalActivity::class.java))
        }

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, LokalTransferActivity::class.java))
        }
    }

    private fun userTranser(){
        binding.etNorekening.addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when(binding.etNorekening.text.toString()){
                    "123" ->{
                        binding.etNamaPenerima.setText("Joko")
                    }
                    "1234" ->{
                        binding.etNamaPenerima.setText("Paidi")
                    }
                    "12345" ->{
                        binding.etNamaPenerima.setText("Sujatmiko")
                    }else -> {
                    binding.etNamaPenerima.text.clear()
                    Snackbar.make(binding.btnSelanjutnya, "Please Input Field", Snackbar.LENGTH_LONG).show()
                }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun loadSpiner(){

        binding.btnSelanjutnya.isVisible = false
        binding.btnInvisibleSelanjutnya.isVisible = true
        var aa = ArrayAdapter(this, R.layout.spinner_right_aligned, bankAmerika)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding.mySpinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@BankLokalActivity
            prompt = "Bank Amerika"
            gravity = Gravity.CENTER

        }

        val ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        ll.setMargins(10, 40, 10, 10)

        aa = ArrayAdapter(this, R.layout.spinner_right_aligned, bankAmerika)
        aa.setDropDownViewResource(R.layout.spinner_right_aligned)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (view?.id) {
            1 -> showToast(message = "")
            else -> {
                Snackbar.make(binding.btnSelanjutnya, "Bank Tujuan ${bankAmerika[position]}", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.btnSelanjutnya.isVisible = true
        binding.btnInvisibleSelanjutnya.isVisible = false
    }

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()

    }
}