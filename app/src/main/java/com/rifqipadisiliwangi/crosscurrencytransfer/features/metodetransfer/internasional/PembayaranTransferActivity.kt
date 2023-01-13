package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

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
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPembayaranTransferBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PembayaranTransferActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityPembayaranTransferBinding
    private lateinit var dataTransaksi : DataStoreTransaksi
    var transaksiTotal = ""
    var metodePembayaran = ""
    var pilihBank = ""
    var noRekeningTransaksi = ""
    var namaPenerima = ""
    var bankIndonesia = arrayOf("Pilih Bank","Mandiri", "BCA", "Cimb Niaga", "BRI", "BNI")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSpiner()
        loadDataStore()


        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, BankInternationalActivity::class.java))
        }
    }

    private fun loadDataStore(){
        dataTransaksi = DataStoreTransaksi(this)
        dataTransaksi.transaksiTotal.asLiveData().observe(this) {
            transaksiTotal = it
            binding.tvSaldoTotal.text = it.toString()
        }
        dataTransaksi.transaksiJenisBank.asLiveData().observe(this) {
            pilihBank = it
        }

        dataTransaksi.transaksiNoRekening.asLiveData().observe(this) {
            noRekeningTransaksi = it
        }

        dataTransaksi.transaksiNamaPenerima.asLiveData().observe(this) {
            namaPenerima = it
        }
        binding.btnSelanjutnya.setOnClickListener {
            transaksiTotal = binding.tvSaldoTotal.text.toString()
            metodePembayaran = binding.mySpinner.selectedItem.toString()
            GlobalScope.launch {
                dataTransaksi.saveData(id = "", pilihBank, namaPenerima, noRekeningTransaksi, metodePembayaran, transaksiTotal)
            }
            startActivity(Intent(this, PengirimTransferActivity::class.java))
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
            onItemSelectedListener = this@PembayaranTransferActivity
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