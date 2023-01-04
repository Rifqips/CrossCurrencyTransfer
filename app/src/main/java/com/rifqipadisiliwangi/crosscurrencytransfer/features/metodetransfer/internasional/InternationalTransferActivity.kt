package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
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

    private fun exchangedSetup(){

            if (binding.tvTotal.text.toString() == null || binding.tvTotal.text.toString() ==" "){
                Toast.makeText(this,"clicked on reset textView,\n output textView already reset",Toast.LENGTH_LONG).show()
            }else{
                binding.tvTotal.setText("").toString()
            }
               binding.etDropAsal.addTextChangedListener(object: TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        val inputValue: String = binding.etDropAsal.text.toString()+ " " + "IDR"
                        if (inputValue == null || inputValue ==" "){
                            Toast.makeText(applicationContext,"please input data, edit text cannot be blank",Toast.LENGTH_LONG).show()
                        }else{
                            binding.tvTotal.setText(inputValue).toString()
                        }
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                        Toast.makeText(applicationContext,"executed while making any change over EditText",Toast.LENGTH_SHORT).show()
                    }
                    override fun afterTextChanged(p0: Editable?) {
                        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        val inputValue: String = binding.etDropAsal.text.toString()+ " " + "IDR"
                        if (inputValue == null || inputValue ==" "){
                            Toast.makeText(applicationContext,"please input data, edit text cannot be blank",Toast.LENGTH_LONG).show()
                        }else{
                            binding.tvTotal.setText(inputValue).toString()
                        }
                    }
                })


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

        val spinner = binding.spinnerTujuan
        countryAdapter = CountrySpinnerAdapter(this, countryData){
            binding.etDropTujuan.hint = it?.currencyCode
        }
        spinner.adapter = countryAdapter

    }
}