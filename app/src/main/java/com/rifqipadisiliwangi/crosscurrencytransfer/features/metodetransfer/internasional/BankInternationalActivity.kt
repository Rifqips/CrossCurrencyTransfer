package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityBankInternationalBinding

class BankInternationalActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityBankInternationalBinding

    var bankAmerika = arrayOf("Pilih Bank","Bank Of America", "JPMorgan Chase", "Wells Fargo", "Citigroup", "Goldman Sachs Group")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankInternationalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSpiner()
        userTranser()
        val myData = intent.getStringExtra("total")
        binding.tvSaldoTotal.text = myData.toString()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, InternationalTransferActivity::class.java))
        }

        binding.btnSelanjutnya.setOnClickListener {

            val intent = Intent(this, PembayaranTransferActivity::class.java)
            val total = binding.tvSaldoTotal.text.toString()
            intent.putExtra("total", total)
            startActivity(intent)
        }

    }

    private fun userTranser(){
        binding.etNorekening.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when(binding.etNorekening.text.toString()){
                    "123" ->{
                        binding.etNamaPenerima.setText("Joko")
                    }
                    "1234" ->{
                        binding.etNamaPenerima.setText("Paidi")
                    }
                    "12345" ->{
                        binding.etNamaPenerima.setText("Sujatmiko")
                    }else -> {
                    binding.etNamaPenerima.text.clear()
                    Snackbar.make(binding.btnSelanjutnya, "Please Input Field", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun loadSpiner(){

        binding.btnSelanjutnya.isVisible = false
        binding.btnInvisibleSelanjutnya.isVisible = true
        var aa = ArrayAdapter(this, R.layout.spinner_right_aligned, bankAmerika)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding.mySpinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@BankInternationalActivity
            prompt = "Bank Amerika"
            gravity = Gravity.CENTER

        }

        val ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        ll.setMargins(10, 40, 10, 10)

        aa = ArrayAdapter(this, R.layout.spinner_right_aligned, bankAmerika)
        aa.setDropDownViewResource(R.layout.spinner_right_aligned)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (view?.id) {
            1 -> showToast(message = "")
            else -> {
                Snackbar.make(binding.btnSelanjutnya, "Bank Tujuan ${bankAmerika[position]}", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.btnSelanjutnya.isVisible = true
        binding.btnInvisibleSelanjutnya.isVisible = false
    }

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()

    }
}