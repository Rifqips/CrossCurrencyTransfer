package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.view.isVisible
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.register.RegisterApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.otp.OtpApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityVerifikasiBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.RegisterPresenter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.RegisterView
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register.RegisterActivity

class VerifikasiActivity : AppCompatActivity(), OtpView {

    private lateinit var binding : ActivityVerifikasiBinding
    private var digit_on_screen = StringBuilder()
    private val presenter = OtpPresenter(OtpApi())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeButtons()
//        postRegister()
        presenter.onAttach(this)

        binding.ibBack.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        timer()
        binding.tvKirimUlangKode.setOnClickListener {
            timer()
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

    override fun onSuccessOtp() {
        presenter.otp(0)
        Toast.makeText(this, "Success OTP", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, DataDiriActivity::class.java))
    }

    fun timer () {
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
    private fun postRegister(){

//        presenter.otp(
//            binding.resultId.text.toString().toInt()
//        )
    }
}