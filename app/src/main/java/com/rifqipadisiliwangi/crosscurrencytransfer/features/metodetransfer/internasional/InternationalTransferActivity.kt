package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityInternationalTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class InternationalTransferActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInternationalTransferBinding
    private val images = intArrayOf(R.drawable.ic_ind,
        R.drawable.ic_us,
        R.drawable.ic_ausi,
        R.drawable.ic_sg,
        R.drawable.ic_jpn,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternationalTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinnerSetup()
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, HomeBottomActivity::class.java))
        }

    }

    private fun spinnerSetup() {
        binding.ivAsalTf?.setFactory({
            val imgView = ImageView(applicationContext)
            imgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imgView })

        binding.ivTujuanTf?.setFactory({
            val imgView = ImageView(applicationContext)
            imgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imgView })

        val spinner = binding.spinnerAsal
        val spinnerTujuan = binding.spinnerTujuan
        val currencies = resources.getStringArray(R.array.currencies)
        val currenciesResult = resources.getStringArray(R.array.currenciesResult)

        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, currencies,)
            val adapterResul = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, currenciesResult,)
            spinner.adapter = adapter
            spinner.adapter = adapterResul
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    binding.ivAsalTf.setImageResource(images[position])
                    binding.etDropAsal.setText(currencies[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        if (spinnerTujuan != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, currencies,)
            val adapterResul = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, currenciesResult,)
            spinnerTujuan.adapter = adapter
            spinnerTujuan.adapter = adapterResul
            spinnerTujuan.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    binding.ivTujuanTf.setImageResource(images[position])
                    binding.etDropTujuan.setText(currencies[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

    }

}