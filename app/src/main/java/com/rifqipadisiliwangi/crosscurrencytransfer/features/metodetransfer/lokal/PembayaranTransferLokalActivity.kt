package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPembayaranTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPembayaranTransferLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.BankInternationalActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.PengirimTransferActivity

class PembayaranTransferLokalActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityPembayaranTransferLokalBinding
    var bankIndonesia = arrayOf("Pilih Bank","Mandiri", "BCA", "Cimb Niaga", "BRI", "BNI")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranTransferLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSpiner()
        val myData = intent.getStringExtra("total")
        binding.tvSaldoTotal.text = myData.toString()

        binding.btnSelanjutnya.setOnClickListener {
            startActivity(Intent(this, PengirimTransferLokalActivity::class.java))
        }

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, BankLokalActivity::class.java))
        }
    }


    private fun loadSpiner(){

        binding.btnSelanjutnya.isVisible = false
        binding.btnInvisibleSelanjutnya.isVisible = true
        var aa = ArrayAdapter(this, R.layout.spinner_right_aligned, bankIndonesia)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding.mySpinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@PembayaranTransferLokalActivity
            prompt = "Bank Amerika"
            gravity = Gravity.CENTER


        }

        val ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        ll.setMargins(10, 40, 10, 10)

        aa = ArrayAdapter(this, R.layout.spinner_right_aligned, bankIndonesia)
        aa.setDropDownViewResource(R.layout.spinner_right_aligned)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (view?.id) {
            1 -> showToast(message = "")
            else -> {
                Snackbar.make(binding.btnSelanjutnya, "Bank Tujuan ${bankIndonesia[position]}", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.btnSelanjutnya.isVisible = true
        binding.btnInvisibleSelanjutnya.isVisible = false
    }

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()

    }
}