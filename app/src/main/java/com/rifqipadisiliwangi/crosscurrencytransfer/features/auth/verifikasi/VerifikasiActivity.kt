package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreUser
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.otp.OtpApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksi.TranskasiApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityVerifikasiBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register.RegisterActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VerifikasiActivity : AppCompatActivity(), OtpView {

    private lateinit var binding : ActivityVerifikasiBinding
    private var digit_on_screen = StringBuilder()
    private val presenter = OtpPresenter(OtpApi(), TranskasiApi())

    lateinit var dataStoreUser : DataStoreUser
    var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreUser = DataStoreUser(this)
        presenter.onAttach(this)
        initializeButtons()
        postOtp()
        timer()
        validate()
        dataStore()


    }


    private fun validate(){
        binding.tvBelumDapat.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }

        binding.ibBack.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }


        binding.tvKirimUlangKode.setOnClickListener {
            timer()
        }
    }

    private fun dataStore(){
        dataStoreUser.noPhone.asLiveData().observe(this){
            phoneNumber = it
            binding.dummyPhone.text = it.toString()
        }
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
        startActivity(Intent(this, DataDiriActivity::class.java))
    }

    override fun onSuccessTransaksi() {
        TODO("Not yet implemented")
    }

    private fun timer () {
        object : CountDownTimer(60000,1000) {
            override fun onTick(p0: Long) {
                binding.tvTimer.text = "${p0 / 1000}"
            }
            override fun onFinish() {
                binding.tvKirimUlangKode.setTextColor(Color.rgb(42,202,16))
                binding.tvKirimUlangKode.isEnabled = true
            }
        }.start()
    }

    private fun initializeButtons() {
        functionalButtons()
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
     *  This function remove the last digit on the screen.
     */
    private fun clearDigit() {
        val length = digit_on_screen.length
        digit_on_screen.deleteCharAt(length - 1)
        binding.backspaceBtn.isVisible = length != 0
        binding.resultId.text = digit_on_screen.toString()
    }

    private fun postOtp(){
        OtpDataSingleton.otp
        binding.btnSend.setOnClickListener {
            presenter.otp(
                binding.resultId.text.toString().toInt()
            )
            phoneNumber = binding.dummyPhone.text.toString()
            GlobalScope.launch {
                dataStoreUser.saveData(phoneNumber,"","")
            }
        }

    }


}