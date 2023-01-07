package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLoginBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword.LupaPasswordActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register.RegisterActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class LoginActivity : AppCompatActivity() {

    var password = "hidePassword"
    var enableButtonPassword = ""
    var enableButtonEmail = "l"
    private lateinit var binding: ActivityLoginBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    var firebaseAuth= FirebaseAuth.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        firebaseAuth= FirebaseAuth.getInstance()

        binding.tvLupaPassword.setOnClickListener {
            startActivity(Intent(this, LupaPasswordActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, PinActivity::class.java))
        }

        binding.btnLoginGoogle.setOnClickListener {
            signInGoogle()
        }

        binding.tvPerDescSatu.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.etEmail.doOnTextChanged { text, start, before, count ->
            binding.btnLogin.isEnabled = false

                if (binding.etEmail.text.toString().isEmpty()) {
                    binding.tvWarningEmail.text = "Anda harus mengisi bagian ini"
                    binding.tvWarningEmail.isVisible = true
                    enableButtonEmail = "v"
                }else if (!isValidEmail(binding.etEmail.text.toString())) {
                    binding.tvWarningEmail.text = "Format email salah"
                    binding.tvWarningEmail.isVisible = true
                    enableButtonEmail = "q"
                }else if (isValidEmail(binding.etEmail.text.toString()) ) {
                    binding.tvWarningEmail.isVisible = false
                    enableButtonEmail = "berhasil"
                }
            if (enableButtonEmail == enableButtonPassword) {
                binding.btnLogin.isEnabled = true
                enableButtonEmail = "resetEmail"
            }
        }

        binding.ibShowPassword.setOnClickListener {
            if (password == "hidePassword")  {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                password = "showPassword"
            }else {
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                password = "hidePassword"
            }
        }

        binding.etPassword.doOnTextChanged { text, start, before, count ->
            binding.btnLogin.isEnabled = false

            if (binding.etPassword.text.toString().isEmpty()) {
                    binding.tvWarningKataSandi.text = "Anda harus mengisi bagian ini"
                binding.tvWarningKataSandi.isVisible = true
                enableButtonPassword = "p"
            }
            else if ( !(binding.etPassword.text.toString().length >= 8 &&
                        binding.etPassword.text.toString().contains("[A-Z]".toRegex()) &&
                        binding.etPassword.text.toString().contains("[a-z]".toRegex()) &&
                        binding.etPassword.text.toString().contains("[0-9]".toRegex()) &&
                        binding.etPassword.text.toString().contains("[@*#&]".toRegex()))
            ){
                binding.tvWarningKataSandi.text = "Kata sandi harus berisi huruf besar, angka dan simbol (@ * # &)"
                binding.tvWarningKataSandi.isVisible = true
            }
            else if (binding.etPassword.text.toString().length >= 8 &&
                binding.etPassword.text.toString().contains("[A-Z]".toRegex()) &&
                binding.etPassword.text.toString().contains("[a-z]".toRegex()) &&
                binding.etPassword.text.toString().contains("[0-9]".toRegex()) &&
                binding.etPassword.text.toString().contains("[@*#&]".toRegex()) ) {
                    binding.tvWarningKataSandi.isVisible = false
                    enableButtonPassword = "berhasil"
            }
            if (enableButtonEmail == enableButtonPassword){
                binding.btnLogin.isEnabled = true
                enableButtonPassword = "resetPassword"
            }
        }
    }

    private  fun signInGoogle(){

        val signInIntent:Intent=mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)
    }

    // onActivityResult() function : this is where we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException){
            startActivity(Intent(this, HomeBottomActivity::class.java))
//            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                //SavedPreference.setEmail(this,account.email.toString())
                // SavedPreference.setUsername(this,account.displayName.toString())
                startActivity(Intent(this, HomeBottomActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, HomeBottomActivity::class.java))
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}
