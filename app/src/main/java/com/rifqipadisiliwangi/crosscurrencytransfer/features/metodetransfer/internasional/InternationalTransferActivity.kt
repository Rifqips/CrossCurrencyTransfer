package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.codepicker.CountryData
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityInternationalTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.codepicker.CountrySpinnerAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class InternationalTransferActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityInternationalTransferBinding
    private var countryData : MutableList<CountryData> = mutableListOf()
    private lateinit var countryAdapter: CountrySpinnerAdapter
    private lateinit var countryList: List<CountryData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInternationalTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinnerSetup()
//        setUpAdapter()
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, HomeBottomActivity::class.java))
        }

        binding.btnSelanjutnya.setOnClickListener {
            startActivity(Intent(this, BankInternationalActivity::class.java))
        }

    }

    private fun spinnerSetup() {

//        val images = intArrayOf(
//            R.drawable.ic_ind,
//            R.drawable.ic_us,
//            R.drawable.ic_ausi,
//            R.drawable.ic_sg,
//            R.drawable.ic_jpn,
//        )

        binding.ivAsalTf?.setFactory({
            val imgView = ImageView(applicationContext)
            imgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imgView })

        binding.ivTujuanTf?.setFactory({
            val imgView = ImageView(applicationContext)
            imgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imgView })

        val spinner = binding.spinnerAsal
//        val spinnerTujuan = binding.spinnerTujuan
        val currenciesResult = resources.getStringArray(R.array.currenciesResult)
//        val imgResult = resources.getStringArray(R.array.currenciesResult)
    countryAdapter = CountrySpinnerAdapter(this, countryList)
        spinner.adapter = countryAdapter
        spinner.onItemSelectedListener = this
//    spinner.adapter = countryAdapter
    countryData.addAll(
        listOf(
            CountryData( R.drawable.ic_ind ),
            CountryData( R.drawable.ic_us ),
            CountryData( R.drawable.ic_ausi ),
            CountryData( R.drawable.ic_sg ),
            CountryData( R.drawable.ic_jpn ),
        )
    )
//        if (spinner != null) {
//
//            val adapterResult = ArrayAdapter(
//                this,
//                R.layout.spinner_item, countryData,
//            )
//
//            spinner.adapter = adapterResult
//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
////                    binding.ivAsalTf.setImageResource(images[position])
//                    binding.etDropAsal.setText(currenciesResult[position])
//
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }

//        if (spinnerTujuan != null) {
//            val adapterResul = ArrayAdapter(
//                this,
//                android.R.layout.simple_spinner_item, currenciesResult,
//            )
//            spinnerTujuan.adapter = adapterResul
//            spinnerTujuan.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
//                    binding.ivTujuanTf.setImageResource(images[position])
//                    binding.etDropTujuan.setText(currenciesResult[position])
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        countryData
    }

//private fun spinnerSetup() {
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
}