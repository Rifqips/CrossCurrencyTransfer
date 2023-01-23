package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.login.LoginApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLoginBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin.PinActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register.RegisterActivity

class LoginActivity : AppCompatActivity(), LoginView {
    var password = "hidePassword"
    private lateinit var binding: ActivityLoginBinding
    private val presenterLogin = LoginPresenter(LoginApi())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postLogin()

        presenterLogin.onAttach(this)

        binding.etEmail.doOnTextChanged { text, start, before, count ->
            presenterLogin.validateEmail(binding.etEmail.text.toString())
        }

        binding.etPassword.doOnTextChanged { text, start, before, count ->
            presenterLogin.validasiPassword(binding.etPassword.text.toString())
        }
        binding.tvPerDescSatu.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.ibShowPassword.setOnClickListener {
            if (password == "hidePassword")  {
                binding.ibShowPassword.setImageResource(R.drawable.eye_off)
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                password = "showPassword"
            }else {
                binding.ibShowPassword.setImageResource(R.drawable.eye_on)
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                password = "hidePassword"
            }
        }
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    override fun onLoading() {
//        Toast.makeText(this,"onLoading",Toast.LENGTH_SHORT).show()
    }

    override fun onFinishedLoading() {
//        Toast.makeText(this,"Password atau Email salah",Toast.LENGTH_SHORT).show()
    }

    override fun onError(code: Int, message: String) {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val viewPopup = layoutInflater.inflate(R.layout.pop_up_aktivasi_akun, null)
        val lanjut = viewPopup.findViewById<Button>(R.id.btnCobaNanti)

        builder.setView(viewPopup)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        lanjut.setOnClickListener {
            builder.dismiss()
        }
    }

    private fun postLogin(){
        binding.btnLogin.setOnClickListener {
            presenterLogin.loginUser(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }
    override fun onErrorEmail(code: Int, message: String) {
//        Toast.makeText(this,"onErrorEmail",Toast.LENGTH_SHORT).show()
        when (code) {
            1 -> {
                binding.tvWarningEmail.text = message
                binding.tvWarningEmail.isVisible = true
            }
            2 -> {
                binding.tvWarningEmail.text = message
                binding.tvWarningEmail.isVisible = true
            }
            3 -> {
                binding.tvWarningEmail.isVisible = false
            }
        }
    }

    override fun onErrorPassword(visible: Boolean, message: String) {
        binding.tvWarningKataSandi.isVisible = visible
        binding.tvWarningKataSandi.text = message
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSuccessGetUser(username: String, password: String) {
//        PrivateData.accessToken
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val viewPopup = layoutInflater.inflate(R.layout.popup_aktivasi_akun_berhasil, null)

        builder.setView(viewPopup)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        Handler().postDelayed({startActivity(Intent(this, PinActivity::class.java))}, 2000)
//        lanjut.setOnClickListener {
//            builder.dismiss()
//            startActivity(Intent(this, PinActivity::class.java))
//        }

//        Toast.makeText(this, "Success Login", Toast.LENGTH_SHORT).show()
//        finish()
    }

    override fun onSuccessLogin() {
        Toast.makeText(this,"onSuccessLogin",Toast.LENGTH_SHORT).show()
    }
}
