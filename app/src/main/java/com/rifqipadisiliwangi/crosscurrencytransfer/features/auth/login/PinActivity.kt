package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPinBinding

class PinActivity : AppCompatActivity() {

    var pin = "hidePin"
    private lateinit var binding: ActivityPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        formValidation()
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


}