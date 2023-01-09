package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rifqipadisiliwangi.crosscurrencytransfer.data.sqlite.DB_class
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPinBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class PinActivity : AppCompatActivity() {

    var pin = "hidePin"
    private lateinit var binding: ActivityPinBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        formValidation()
        auth = Firebase.auth
        firestore = Firebase.firestore
        setPin()
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

    private fun setPin(){
        val dbhelp= DB_class(applicationContext)
        val db = dbhelp.readableDatabase
        binding.btnKirimPin.setOnClickListener {
            val pin = binding.etPin.text.toString()
            if(pin.isNotEmpty() ) {
                val data = ContentValues()
                data.put("pin", binding.etPin.text.toString())
                val rs:Long = db.insert("pintrans", null, data)
                if(!rs.equals(-1)) {
                    val ad = AlertDialog.Builder(this)
                    ad.setTitle("Message")
                    ad.setMessage("Created Pin uccessfully")
                    ad.setPositiveButton("Ok", null)
                    ad.show()
                    binding.etPin.text.clear()
                    startActivity(Intent(this, HomeBottomActivity::class.java))
                }else{
                    val ad = AlertDialog.Builder(this)
                    ad.setTitle("Message")
                    ad.setMessage("Record not addedd")
                    ad.setPositiveButton("Ok", null)
                    ad.show()
                    binding.etPin.text.clear()
                }
            }else{
                Toast.makeText(this,"All fields required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}