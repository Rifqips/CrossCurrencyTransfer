package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.getpin.PinSchemeResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.pin.PinApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPinBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class PinActivity : AppCompatActivity(), PinView {

    var pin = "hidePin"
    private lateinit var binding: ActivityPinBinding
    private val presenter = PinPresenter(PinApi())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        formValidation()
        binding.btnKirimPin.setOnClickListener {
            presenter.pin(
                binding.etPin.text.toString()
            )
        }

        presenter.onAttach(this)
    }



    private fun formValidation() {

        binding.ibShowPin.setOnClickListener {
            if (pin == "hidePin") {
                binding.etPin.transformationMethod = PasswordTransformationMethod.getInstance()
                pin = "showPin"
            }else {
                binding.etPin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                pin = "hidePin"
            }
        }

        binding.ibShowKonfirmasiPin.setOnClickListener {
            if (pin == "hidePin") {
                binding.etKonfirmasiPin.transformationMethod = PasswordTransformationMethod.getInstance()
                pin = "showPin"
            }else {
                binding.etKonfirmasiPin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                pin = "hidePin"
            }
        }

        binding.etPin.doOnTextChanged { text, start, before, count ->
            binding.btnKirimPin.setBackgroundColor(Color.rgb(216,216,216) )

            if (binding.etPin.text.toString().isEmpty() ) {
                binding.tvWarningPin.isVisible = true
            }
            else if (binding.etPin.text.length < 6 ) {
                binding.tvWarningPin.text = "Pin harus memuat 6 angka"
                binding.tvWarningPin.isVisible = true
            }
            else if (binding.etPin.text.length == 6)
                binding.tvWarningPin.isVisible = false
        }

        binding.etKonfirmasiPin.doOnTextChanged { text, start, before, count ->
            binding.btnKirimPin.setBackgroundColor(Color.rgb(216,216,216) )

            if (binding.etKonfirmasiPin.text.toString().isEmpty()) {
                binding.tvWarningKonfirmasiPin.text = "Anda harus mengisi bagian ini"
                binding.tvWarningKonfirmasiPin.isVisible = true
            } else if ((binding.etPin.text.toString() == binding.etKonfirmasiPin.text.toString()) &&
                (binding.etKonfirmasiPin.text.length == 6) )
            {
                binding.btnKirimPin.setBackgroundColor(Color.rgb(32,117,243))
                binding.tvWarningKonfirmasiPin.isVisible = false
                binding.btnKirimPin.isEnabled = true
            } else {
                binding.tvWarningKonfirmasiPin.text = "Pin tidak sama"
                binding.tvWarningKonfirmasiPin.isVisible = true
            }
        }
    }

    override fun onLoading() {
        binding.progressBar.isVisible
    }

    override fun onFinishedLoading() {
        binding.progressBar.isInvisible
    }

    override fun onError(code: Int, message: String) {
        Toast.makeText(this,"onError $message", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessPin(otp: PinSchemeResponse) {
        Toast.makeText(this,"onSuccessPin", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HomeBottomActivity::class.java))
    }


}