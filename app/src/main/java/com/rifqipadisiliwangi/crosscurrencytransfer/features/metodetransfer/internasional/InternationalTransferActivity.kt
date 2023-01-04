package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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
    private lateinit var countryList: List<CountryData>
    private val clickListener: (CountryData?) -> Unit
        get() {
            TODO()
        }

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

//    private fun spinnerSetup() {
//    val spinner = binding.spinnerAsal
//    countryAdapter = CountrySpinnerAdapter(this, countryList)
//    spinner.adapter = countryAdapter
//    countryData.addAll(
//        listOf(
//            CountryData( R.drawable.ic_ind ),
//            CountryData( R.drawable.ic_us ),
//            CountryData( R.drawable.ic_ausi ),
//            CountryData( R.drawable.ic_sg ),
//            CountryData( R.drawable.ic_jpn ),
//        )
//    )
//    val adapter = CountrySpinnerAdapter(this, countryData){ data ->
//        binding.ivAsalTf.setImageResource(0)
//    }
//
//    binding.ivAsalTf?.setFactory({
//        val imgView = ImageView(applicationContext)
//        imgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
//        imgView })
//
//    binding.ivTujuanTf?.setFactory({
//        val imgView = ImageView(applicationContext)
//        imgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
//        imgView })
//
//
//    val currenciesResult = resources.getStringArray(R.array.currenciesResult)
//
//    if (spinner != null) {
//        spinner.adapter = adapter
//
//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
//                        binding.ivTujuanTf.setImageResource(images[position])
//                    binding.etDropAsal.setText(currenciesResult[position])
//
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }
//
//    }
//}

    private fun loadSpinner()    {
        countryList = listOf()
        countryData.addAll(
            listOf(
                CountryData( R.drawable.ic_ind ),
                CountryData( R.drawable.ic_us ),
                CountryData( R.drawable.ic_ausi ),
                CountryData( R.drawable.ic_sg ),
                CountryData( R.drawable.ic_jpn ),
            )
        )

        binding.spinnerAsal.apply {
            adapter = CountrySpinnerAdapter(applicationContext, countryData, clickListener)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener
            {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long)
                {
                    countryData[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }
}