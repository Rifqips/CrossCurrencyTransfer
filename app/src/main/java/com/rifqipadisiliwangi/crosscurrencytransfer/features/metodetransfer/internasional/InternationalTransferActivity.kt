package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.codepicker.CountryData
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityInternationalTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.codepicker.CountrySpinnerAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class InternationalTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInternationalTransferBinding
    private var countryData : MutableList<CountryData> = mutableListOf()
    private lateinit var countryAdapter: CountrySpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternationalTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSpinner()
        exchangedSetup()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, HomeBottomActivity::class.java))
        }

        binding.btnSelanjutnya.setOnClickListener {
            startActivity(Intent(this, BankInternationalActivity::class.java))
        }

    }

    private fun loadSpinner() {
        countryData.addAll(
            listOf(
                CountryData( R.drawable.ic_ind ,"IND", 100),
                CountryData( R.drawable.ic_us,"USA",200 ),
                CountryData( R.drawable.ic_ausi,"AUD", 300 ),
                CountryData( R.drawable.ic_sg,"SGP", 400 ),
                CountryData( R.drawable.ic_jpn, "JPN", 500 ),
            )
        )

        val spinner = binding.spinnerTujuan
        countryAdapter = CountrySpinnerAdapter(this, countryData){

            binding.etDropTujuan.text = it?.exchangedContract.toString()

        }
        spinner.adapter = countryAdapter

    }

    private fun exchangedSetup(){

        Snackbar.make(binding.swiperefresh, "Swipe to see the result", Snackbar.LENGTH_LONG).show()
        binding.swiperefresh.isRefreshing = false

        binding.btnSelanjutnya.isVisible = false
        binding.btnInvisibleSelanjutnya.isVisible = true

        binding.swiperefresh.setOnRefreshListener{

            val currencyFrom = binding.etDropAsal.text.toString().toInt()
            if (currencyFrom == null){
                binding.btnSelanjutnya.isVisible = false
                binding.btnInvisibleSelanjutnya.isVisible = true
                binding.etDropAsal.text.toString()
                Toast.makeText(this,"Field must be contained", Toast.LENGTH_LONG).show()
                binding.swiperefresh.isRefreshing = true
            }else{
                val biayaAdmin = 100000
                val currencySpinner = binding.etDropTujuan.text.toString().toInt()
                val resultCurrency = (currencyFrom * currencySpinner) + biayaAdmin
                binding.tvTotal.text = resultCurrency.toString() + " " + "IDR"
                binding.btnSelanjutnya.isVisible = true
                binding.btnInvisibleSelanjutnya.isVisible = false
                binding.swiperefresh.isRefreshing = false
            }
        }

    }


}