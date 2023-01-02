package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityRegisterBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityRegisterBinding
    val imagesNegara = intArrayOf(R.drawable.australia, R.drawable.japan, R.drawable.indonesia, R.drawable.singapore, R.drawable.usa)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter.createFromResource(this, R.array.listNegara, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerNegara.adapter = adapter
        binding.spinnerNegara.onItemSelectedListener = this
        binding.btnkirim.setOnClickListener {
            startActivity(Intent(this, DataDiriActivity::class.java))
        }


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        if (text ==  "Australia") {
            binding.imgNegara.setImageResource(imagesNegara[0])
            binding.tvKodeNegara.text = "+61"
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

//val adapter = ArrayAdapter.createFromResource(this, R.array.listNegara, android.R.layout.simple_spinner_item)
//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//binding.spinnerNegara.adapter = adapter