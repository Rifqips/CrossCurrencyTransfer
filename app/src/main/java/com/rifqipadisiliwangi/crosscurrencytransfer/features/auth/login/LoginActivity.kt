package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.PrivateData
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
//    val token : PrivateData = ""
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
                binding.ibShowPassword.setImageResource(R.drawable.eye_on)
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                password = "showPassword"
            }else {
                binding.ibShowPassword.setImageResource(R.drawable.eye_off)
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                password = "hidePassword"
            }
        }
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
//        Toast.makeText(this,"onLoading",Toast.LENGTH_SHORT).show()
    }

    override fun onFinishedLoading() {
//        Toast.makeText(this,"onFinishedLoading",Toast.LENGTH_SHORT).show()
    }

    override fun onError(code: Int, message: String) {
        when (code) {
            1 -> {
                binding.tvWarningKataSandi.text = message
                binding.tvWarningKataSandi.isVisible = true
            }
            2 -> {
                binding.tvWarningKataSandi.text = message
                binding.tvWarningKataSandi.isVisible = true
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
        Toast.makeText(this,"onErrorPassword",Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessGetUser(username: String, password: String) {
        startActivity(Intent(this, HomeBottomActivity::class.java))
        Toast.makeText(this, "Success Login", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSuccessLogin() {
        startActivity(Intent(this,HomeBottomActivity::class.java))
        Toast.makeText(this,"onSuccessLogin",Toast.LENGTH_SHORT).show()
    }
}
