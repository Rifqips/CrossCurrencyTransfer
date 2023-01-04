package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
//        spinnerSetup()

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
                CountryData( R.drawable.ic_ind ,"IND"),
                CountryData( R.drawable.ic_us,"USA" ),
                CountryData( R.drawable.ic_ausi,"AUD" ),
                CountryData( R.drawable.ic_sg,"SGP" ),
                CountryData( R.drawable.ic_jpn, "JPN" ),
            )
        )

        val spinner = binding.spinnerAsal
        countryAdapter = CountrySpinnerAdapter(this, countryData){
            binding.etDropAsal.hint = it?.currencyCode
        }
        spinner.adapter = countryAdapter

    }
}