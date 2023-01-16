package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.codepicker.CountryData
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityInternationalTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.codepicker.CountrySpinnerAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InternationalTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInternationalTransferBinding
    private var countryData : MutableList<CountryData> = mutableListOf()
    private lateinit var countryAdapter: CountrySpinnerAdapter
    private lateinit var dataTransaksi : DataStoreTransaksi
    var transaksiTotal = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternationalTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSpinner()
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
            startActivity(Intent(this, BankInternationalActivity::class.java))
        }

    }

    private fun loadSpinner() {
        countryData.addAll(
            listOf(
                CountryData( R.drawable.ic_us,"USD",14500 ),
                CountryData( R.drawable.ic_ausi,"AUD", 10639 ),
                CountryData( R.drawable.ic_sg,"SGD", 11638),
                CountryData( R.drawable.ic_jpn, "JPN", 1150 ),
            )
        )



        val spinner = binding.spinnerTujuan
        countryAdapter = CountrySpinnerAdapter(this, countryData){

            binding.tvDropTujuan.text = it?.currencyCode.toString()
            binding.etDropTujuan.text = it?.exchangedContract.toString()


        }
        spinner.adapter = countryAdapter

    }

    private fun exchangedSetup(){

        Snackbar.make(binding.swiperefresh, "Please Input Field", Snackbar.LENGTH_LONG).show()

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
                        Snackbar.make(binding.swiperefresh, "Please Input Field", Snackbar.LENGTH_LONG).show()
                    }
                    else -> {
                        val currencyAsal = binding.etDropAsal.text.toString().toFloat()
                        val currencyFrom = binding.etDropTujuan.text.toString().toFloat()
                        val biayaAdmin = 100000

                        val resultCurrency = currencyAsal  + biayaAdmin
                        binding.tvTotal.text = resultCurrency.toString()

                        val currencyExchange = currencyAsal / currencyFrom
                        binding.etResultCurrencyTujuan.text = currencyExchange.toString()

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