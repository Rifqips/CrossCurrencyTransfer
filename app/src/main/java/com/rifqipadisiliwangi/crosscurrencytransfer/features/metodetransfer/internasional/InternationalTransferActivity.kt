package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityBankInternationalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityInternationalTransferBinding
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

    }

    private fun loadSpinner() {
        countryData.addAll(
            listOf(
                CountryData( R.drawable.ic_ind ,"IND", 1),
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

        binding.etDropAsal.addTextChangedListener(object: TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                val inputValue: String = binding.etDropAsal.text.toString()
                if (inputValue == ""){
                    Toast.makeText(applicationContext,"please input Field",Toast.LENGTH_LONG).show()
                }else{
                    binding.tvResultDropAsal.setText(inputValue).toString()

                    val currencyAsal = binding.etDropAsal.text.toString().toInt()
                    val currencyFrom = binding.etDropTujuan.text.toString().toInt()
                    val biayaAdmin = 100000

                    val resultCurrency = currencyAsal  + biayaAdmin
                    binding.tvTotal.text = resultCurrency.toString()

                    val currencyExchange = currencyAsal / currencyFrom
                    binding.etResultCurrencyTujuan.text = currencyExchange.toString()



                }
            }
            override fun afterTextChanged(p0: Editable?) {
                //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }
        })


//        Snackbar.make(binding.swiperefresh, "Swipe to see the result", Snackbar.LENGTH_LONG).show()
//        binding.swiperefresh.isRefreshing = false
//
//        binding.btnSelanjutnya.isVisible = false
//        binding.btnInvisibleSelanjutnya.isVisible = true
//
//        binding.swiperefresh.setOnRefreshListener{
//
//            val currencyFrom = binding.etDropAsal.text.toString().toInt()
//            if (currencyFrom == null){
//                binding.btnSelanjutnya.isVisible = false
//                binding.btnInvisibleSelanjutnya.isVisible = true
//                binding.etDropAsal.text.toString()
//                Toast.makeText(this,"Field must be contained", Toast.LENGTH_LONG).show()
//                binding.swiperefresh.isRefreshing = true
//            }else{
//                val biayaAdmin = 100000
//                val currencySpinner = binding.etDropTujuan.text.toString().toInt()
//                val resultCurrency = (currencyFrom / currencySpinner) + biayaAdmin
//                binding.tvTotal.text = resultCurrency.toString() + " " + "IDR"
//                binding.btnSelanjutnya.isVisible = true
//                binding.btnInvisibleSelanjutnya.isVisible = false
//                binding.swiperefresh.isRefreshing = false
//            }
//        }

    }

}