package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.otp.OtpApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksi.TranskasiApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPengirimTransferLokalBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.OtpDataSingleton
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.OtpPresenter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.OtpView
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.SuksesTransferActivity

class PengirimTransferLokalActivity : AppCompatActivity(), OtpView {

    private lateinit var binding : ActivityPengirimTransferLokalBinding
    var digit_on_screen = StringBuilder()
//    private lateinit var dataTransaksi : DataStoreTransaksi
//    var transaksiTotal = ""
//    var metodePembayaran = ""
//    var pilihBank = ""
//    var noRekeningTransaksi = ""
//    var namaPenerima = ""
    private val presenter = OtpPresenter(OtpApi(), TranskasiApi())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengirimTransferLokalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeButtons()
        postOtp()
//        getDataStore()
        presenter.onAttach(this)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, PembayaranTransferLokalActivity::class.java))
        }
    }

    private fun postOtp(){
        OtpDataSingleton.otp
        binding.btnSend.setOnClickListener {
            presenter.otp(
                binding.resultId.text.toString().toInt()
            )
        }

    }

//    private fun getDataStore(){
//        dataTransaksi = DataStoreTransaksi(this)
//        dataTransaksi.transaksiTotal.asLiveData().observe(this) {
//            transaksiTotal = it
//            binding.tvGetTotal.text = it.toString()
//        }
//        dataTransaksi.transaksiJenisBank.asLiveData().observe(this) {
//            pilihBank = it
//            binding.tvGetJenisBank.text = it.toString()
//        }
//
//        dataTransaksi.transaksiNoRekening.asLiveData().observe(this) {
//            noRekeningTransaksi = it
//            binding.tvGetNoRekening.text = it.toString()
//        }
//
//        dataTransaksi.transaksiTipeTransaksi.asLiveData().observe(this) {
//            metodePembayaran = it
//            binding.tvGetTipeTransaksi.text = it.toString()
//        }
//
//        dataTransaksi.transaksiNamaPenerima.asLiveData().observe(this) {
//            namaPenerima = it
//            binding.tvGetNamaPenerima.text = it.toString()
//        }
//    }

    private fun initializeButtons() {
        functionalButtons()
        operationalButtons()
        numericalButtons()
    }

    private fun numericalButtons() {

        binding.oneBtn.setOnClickListener {
            appendToDigitOnScreen("1")
        }

        binding.twoBtn.setOnClickListener {
            appendToDigitOnScreen("2")
        }

        binding.threeBtn.setOnClickListener {
            appendToDigitOnScreen("3")
        }

        binding.fourBtn.setOnClickListener {
            appendToDigitOnScreen("4")
        }

        binding.fiveBtn.setOnClickListener {
            appendToDigitOnScreen("5")
        }

        binding.sixBtn.setOnClickListener {
            appendToDigitOnScreen("6")
        }

        binding.sevenBtn.setOnClickListener {
            appendToDigitOnScreen("7")
        }

        binding.eightBtn.setOnClickListener {
            appendToDigitOnScreen("8")
        }

        binding.nineBtn.setOnClickListener {
            appendToDigitOnScreen("9")
        }

        binding.zeroBtn.setOnClickListener {
            appendToDigitOnScreen("0")
        }


    }

    /**
     *  Insert the button been clicked onto the screen so user can see
     *  inputs for the button clicked
     */
    private fun appendToDigitOnScreen(digit: String) {

        // Add each digit to our string builder
        digit_on_screen.append(digit)

        // display it on the screen of our mobile app
        binding.resultId.text = digit_on_screen.toString()
    }

    /**
     *  Initialize the operation keys in our calculator like the
     *  addition key, subtraction key and the likes
     */
    private fun operationalButtons() {

    }

    /**
     * Function to assign operational sign to our math calculations
     */

    /**
     * Handles functional operations in out application like
     * clear button, backspace button and the clear everything button
     */
    private fun functionalButtons() {

        binding.backspaceBtn.setOnClickListener {
            clearDigit()
        }
    }

    /**
     *  This function performs our Math Operation which is then showed on the screen.
     */

    /**
     *  This function remove the last digit on the screen.
     */
    private fun clearDigit() {

        val length = digit_on_screen.length
        digit_on_screen.deleteCharAt(length - 1)
        binding.backspaceBtn.isVisible = length != 0
        binding.resultId.text = digit_on_screen.toString()

    }


    override fun onLoading() {
        binding.progressBarPengirim.isVisible = true
    }

    override fun onFinishedLoading() {
        binding.progressBarPengirim.isVisible = false
    }

    override fun onError(code: Int, message: String) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        binding.progressBarPengirim.isVisible = true
    }

    override fun onSuccessOtp(otp: Int) {
        startActivity(Intent(this, SuksesTransferLokalActivity::class.java))
    }

    override fun onSuccessTransaksi() {
        Toast.makeText(this, "Thank u for trust Trans Evilz", Toast.LENGTH_SHORT).show()
    }
}