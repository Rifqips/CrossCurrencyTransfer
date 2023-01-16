package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.codepicker.CountryData
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityBankLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLokalTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.codepicker.CountrySpinnerAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.BankInternationalActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LokalTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLokalTransferBinding
    private lateinit var dataTransaksi : DataStoreTransaksi
    private var countryData : MutableList<CountryData> = mutableListOf()
    var transaksiTotal = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokalTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exchangedSetup()
        dataTransaksi = DataStoreTransaksi(this)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, HomeBottomActivity::class.java))
        }

        binding.btnSelanjutnya.setOnClickListener {
            transaksiTotal = binding.tvTotal.text.toString()
            GlobalScope.launch {
                dataTransaksi.saveData(id = "", jenisBank = "", namaPenerima = "", noRekening = "", tipeTransaksi = "",transaksiTotal)
            }
            startActivity(Intent(this, BankLokalActivity::class.java))
        }

    }

    private fun exchangedSetup(){

        binding.btnSelanjutnya.isVisible = false
        binding.btnInvisibleSelanjutnya.isVisible = true
        binding.etDropAsal.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                val inputValue: String = binding.etDropAsal.text.toString()
                when (inputValue) {
                    "" -> {
                        binding.btnSelanjutnya.isVisible = false
                        binding.btnInvisibleSelanjutnya.isVisible = true
                        Snackbar.make(binding.btnSelanjutnya, "Please Input Field", Snackbar.LENGTH_LONG).show()
                    }
                    else -> {
                        val currencyAsal = binding.etDropAsal.text.toString().toInt()
                        val biayaAdmin = 100000

                        val resultCurrency = currencyAsal  + biayaAdmin
                        binding.tvTotal.text = resultCurrency.toString()

                        binding.btnSelanjutnya.isVisible = true
                        binding.btnInvisibleSelanjutnya.isVisible = false
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }
        })

    }
}