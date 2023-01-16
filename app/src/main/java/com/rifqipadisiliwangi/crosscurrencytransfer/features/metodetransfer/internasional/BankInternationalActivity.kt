package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityBankInternationalBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BankInternationalActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityBankInternationalBinding
    private lateinit var dataTransaksi : DataStoreTransaksi
    var transaksiTotal = ""
    var pilihBank = ""
    var noRekeningTransaksi = ""
    var namaPenerima = ""

    var bankAmerika = arrayOf("Pilih Bank","Bank Of America", "JPMorgan Chase", "Wells Fargo", "Citigroup", "Goldman Sachs Group")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankInternationalBinding.inflate(layoutInflater)
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
                dataTransaksi.saveData(id = "", pilihBank, namaPenerima, noRekeningTransaksi, tipeTransaksi = "", transaksiTotal)
            }
            startActivity(Intent(this, PembayaranTransferActivity::class.java))
        }

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, InternationalTransferActivity::class.java))
        }

    }

    private fun userTranser(){
        binding.etNorekening.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when(binding.etNorekening.text.toString()){
                    "123" ->{
                        binding.etNamaPenerima.setText("Joko")
                        binding.btnInvisibleSelanjutnya.isVisible = false
                        binding.btnSelanjutnya.isVisible = true
                    }
                    "1234" ->{
                        binding.etNamaPenerima.setText("Paidi")
                        binding.btnInvisibleSelanjutnya.isVisible = false
                        binding.btnSelanjutnya.isVisible = true
                    }
                    "12345" ->{
                        binding.etNamaPenerima.setText("Sujatmiko")
                        binding.btnInvisibleSelanjutnya.isVisible = false
                        binding.btnSelanjutnya.isVisible = true
                    }else -> {
                    binding.etNamaPenerima.text.clear()
                    binding.btnInvisibleSelanjutnya.isVisible = true
                    binding.btnSelanjutnya.isVisible = false
                    Snackbar.make(binding.btnSelanjutnya, "Please Input Field", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun loadSpiner(){
        var aa = ArrayAdapter(this, R.layout.spinner_right_aligned, bankAmerika)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding.mySpinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@BankInternationalActivity
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

        binding.btnSelanjutnya.isVisible = false
        binding.btnInvisibleSelanjutnya.isVisible = true
    }

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()

    }
}