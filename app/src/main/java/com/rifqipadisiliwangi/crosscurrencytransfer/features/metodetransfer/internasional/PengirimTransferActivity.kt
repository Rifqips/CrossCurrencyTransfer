package com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransactionSchemeResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksi.TranskasiApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPengirimTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.TransaksiPresenter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.TransaksiView

class PengirimTransferActivity : AppCompatActivity(), TransaksiView {

    private lateinit var binding : ActivityPengirimTransferBinding
    private lateinit var dataTransaksi : DataStoreTransaksi
    var digit_on_screen = StringBuilder()

    var transaksiTotal = ""
    var metodePembayaran = ""
    var pilihBank = ""
    var noRekeningTransaksi = ""
    var namaPenerima = ""
    var codeSwift = ""

    private val presenter = TransaksiPresenter(TranskasiApi())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengirimTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeButtons()
        getDataStore()
        postRegister()
        presenter.onAttach(this)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, PembayaranTransferActivity::class.java))
        }

    }
    private fun postRegister(){
        binding.btnSend.setOnClickListener {
            presenter.transaksiUser(
                codeSwift,
                noRekeningTransaksi,
                transaksiTotal,
                binding.resultId.text.toString()
            )
        }
    }

    override fun onLoading() {
        binding.progressBarPengirim.isVisible
    }

    override fun onFinishedLoading() {
        binding.progressBarPengirim.isInvisible
    }

    override fun onError(code: Int, message: String) {
        Toast.makeText(this,"onError $message",Toast.LENGTH_SHORT).show()
    }

    override fun onErrorTransasksi(code: Int, message: String) {
        Toast.makeText(this, "onErrorTransasksi", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessTransaction(transaksi: TransactionSchemeResponse) {
        startActivity(Intent(this, SuksesTransferActivity::class.java))
        Toast.makeText(this,"onSuccessTransaksi",Toast.LENGTH_SHORT).show()
    }

    private fun getDataStore(){
        dataTransaksi = DataStoreTransaksi(this)
        dataTransaksi.transaksiTotal.asLiveData().observe(this) {
            transaksiTotal = it
            binding.tvGetTotal.text = it.toString()
        }
        dataTransaksi.transaksiJenisBank.asLiveData().observe(this) {
            pilihBank = it
            binding.tvGetJenisBank.text = it.toString()
        }

        dataTransaksi.transaksiNoRekening.asLiveData().observe(this) {
            noRekeningTransaksi = it
            binding.tvGetNoRekening.text = it.toString()
        }

        dataTransaksi.transaksiTipeTransaksi.asLiveData().observe(this) {
            metodePembayaran = it
            binding.tvGetTipeTransaksi.text = it.toString()
        }

        dataTransaksi.transaksiNamaPenerima.asLiveData().observe(this) {
            namaPenerima = it
            binding.tvGetNamaPenerima.text = it.toString()
        }

        dataTransaksi.codeSwift.asLiveData().observe(this) {
            codeSwift = it
            binding.tvGetSwift.text = it.toString()
        }
    }

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
}