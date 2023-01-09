package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.content.ContentValues
import android.content.Intent
<<<<<<< HEAD
=======
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
>>>>>>> dev-roni
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rifqipadisiliwangi.crosscurrencytransfer.data.sqlite.DB_class
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityPinBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

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

//    fun addPin(){
//        GlobalScope.async {
//            var pin = binding.etPin.text.toString()
//            var konfirmPin = binding.etKonfirmasiPin.text.toString()
//
//            dbPin!!.PinDaoTransEvilz().addToPin(DataPin(0,pin,konfirmPin))
//        }
//    }

//    private fun setAction() {
//        // accomodate all user credential
//        val pin = binding.etPin.text.toString()
//        val pinConf = binding.etKonfirmasiPin.text.toString()
//
//
//        val pinSet = DataTransEvilz(pin)
//
//        /**
//         * Create user action using Firebase Auth
//         * @param email is val user email
//         * @param password is password user
//         */
//        auth.createUserWithEmailAndPassword(pin, pinConf).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                binding.progressBar.visibility = View.GONE
//                val currentUser = auth.currentUser
//                val profile = userProfileChangeRequest {
//                    displayName = pin
//                }
//                currentUser!!.updateProfile(profile)
//                firestore.collection("Users").document(pin).set(pinSet)
//                updateUI(currentUser)
//            } else {
//                binding.progressBar.visibility = View.GONE
//                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
//            }
//        }
//
//    }

//    /**
//     * Update UI when user registered
//     * @param currentUser current user instance from create user action
//     */
//
//    private fun updateUI(currentUser: FirebaseUser) {
//        if (currentUser != null) {
//            val intent = Intent(this@PinActivity, HomeBottomActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
}