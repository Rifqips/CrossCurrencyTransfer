package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.codepicker.ListCodeAsal
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityInternationalTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.codepicker.AdapterCodePickAsal
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

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
            imgView.scaleType = ImageView.ScaleType.CENTER_CROP
            imgView })

        val spinner = binding.spinnerAsal
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

    }

}