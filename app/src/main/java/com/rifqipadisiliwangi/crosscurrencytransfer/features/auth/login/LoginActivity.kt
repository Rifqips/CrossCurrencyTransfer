package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.AuthDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login.LoginData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.login.LoginApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLoginBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register.RegisterActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class LoginActivity : AppCompatActivity(), LoginView {


    var password = "hidePassword"
    var enableButtonPassword = "disablePw"
    var enableButtonEmail = "disableEmail"
    private lateinit var binding: ActivityLoginBinding
    private val presenterLogin = LoginPresenter(LoginApi())
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    var firebaseAuth= FirebaseAuth.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postLogin()
        auth = Firebase.auth

        presenterLogin.onAttach(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        firebaseAuth= FirebaseAuth.getInstance()

//        binding.tvLupaPassword.setOnClickListener {
//            startActivity(Intent(this, LupaPasswordActivity::class.java))
//        }

//        binding.btnLogin.setOnClickListener {
//            startActivity(Intent(this, PinActivity::class.java))
//        }

        binding.btnLoginGoogle.setOnClickListener {
            signInGoogle()
        }

        binding.tvPerDescSatu.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

//        binding.etEmail.doOnTextChanged { text, start, before, count ->
//            enableButtonEmail = "resetBtn"
//            binding.btnLogin.isEnabled = false
//            binding.btnLogin.setBackgroundColor(Color.rgb(216,216,216))
//
//
//                if (binding.etEmail.text.toString().isEmpty()) {
//                    binding.tvWarningEmail.text = "Anda harus mengisi bagian ini"
//                    binding.tvWarningEmail.isVisible = true
//                }else if (!isValidEmail(binding.etEmail.text.toString())) {
//                    binding.tvWarningEmail.text = "Format email salah"
//                    binding.tvWarningEmail.isVisible = true
//                }else if (isValidEmail(binding.etEmail.text.toString()) ) {
//                    binding.tvWarningEmail.isVisible = false
//                    enableButtonEmail = "enabledEmail"
//                }
//            if ( enableButtonEmail == "enabledEmail" && enableButtonPassword == "enabledPwd" ) {
//                binding.btnLogin.isEnabled = true
//                binding.btnLogin.setBackgroundColor(Color.rgb(32,117,243))
//            }
//        }

        binding.ibShowPassword.setOnClickListener {
            if (password == "hidePassword")  {
                binding.ibShowPassword.setImageResource(R.drawable.eye_on)
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                password = "showPassword"
            }else {
                binding.ibShowPassword.setImageResource(R.drawable.eye_off)
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                password = "hidePassword"
            }
        }

//        binding.etPassword.doOnTextChanged { text, start, before, count ->
//            enableButtonPassword = "resetBtn"
//            binding.btnLogin.isEnabled = false
//            binding.btnLogin.setBackgroundColor(Color.rgb(216,216,216))
//
//            if (binding.etPassword.text.toString().isEmpty()) {
//                    binding.tvWarningKataSandi.text = "Anda harus mengisi bagian ini"
//                binding.tvWarningKataSandi.isVisible = true
//            }
//            else if ( !(binding.etPassword.text.toString().length >= 8 &&
//                        binding.etPassword.text.toString().contains("[A-Z]".toRegex()) &&
//                        binding.etPassword.text.toString().contains("[a-z]".toRegex()) &&
//                        binding.etPassword.text.toString().contains("[0-9]".toRegex()) &&
//                        binding.etPassword.text.toString().contains("[@*#&]".toRegex()))
//            ){
//                binding.tvWarningKataSandi.text = "Kata sandi harus berisi huruf besar, angka dan simbol (@ * # &)"
//                binding.tvWarningKataSandi.isVisible = true
//            }
//            else if (binding.etPassword.text.toString().length >= 8 &&
//                binding.etPassword.text.toString().contains("[A-Z]".toRegex()) &&
//                binding.etPassword.text.toString().contains("[a-z]".toRegex()) &&
//                binding.etPassword.text.toString().contains("[0-9]".toRegex()) &&
//                binding.etPassword.text.toString().contains("[@*#&]".toRegex()) ) {
//                    binding.tvWarningKataSandi.isVisible = false
//                    enableButtonPassword = "enabledPwd"
//            }
//            if (enableButtonEmail == "enabledEmail" && enableButtonPassword == "enabledPwd" ){
//                binding.btnLogin.isEnabled = true
//                binding.btnLogin.setBackgroundColor(Color.rgb(32,117,243))
//            }
//        }
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

    override fun onLoading() {
        Toast.makeText(this,"onLoading",Toast.LENGTH_SHORT).show()
    }

    override fun onFinishedLoading() {
        Toast.makeText(this,"onFinishedLoading",Toast.LENGTH_SHORT).show()
    }

    override fun onError(code: Int, message: String) {
        when (code) {
            1 -> {
                binding.tvWarningEmail.text = message
            }
            2 -> {
                binding.tvWarningEmail.text = message
            }
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
        Toast.makeText(this,"onErrorEmail",Toast.LENGTH_SHORT).show()
    }

    override fun onErrorPassword(visible: Boolean, message: String) {
        Toast.makeText(this,"onErrorPassword",Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessGetUser(user: AuthDataItem) {
        Toast.makeText(this,"onSuccessGetUser",Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessLogin() {
        startActivity(Intent(this,HomeBottomActivity::class.java))
        Toast.makeText(this,"onSuccessLogin",Toast.LENGTH_SHORT).show()
    }
}
