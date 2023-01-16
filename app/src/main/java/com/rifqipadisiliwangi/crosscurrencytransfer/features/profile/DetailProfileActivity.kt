package com.rifqipadisiliwangi.crosscurrencytransfer.features.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.EXTRA_POSITION
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.EXTRA_REGISTRATION
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.REQUEST_ADD
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.REQUEST_UPDATE
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.RESULT_ADD
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.RESULT_DELETE
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.RESULT_UPDATE
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.mapCursorToArrayList
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterModel
import com.rifqipadisiliwangi.crosscurrencytransfer.data.utility.RegisterHelper
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityDetailProfileBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.register.RegisterAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class DetailProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailProfileBinding
    private lateinit var registerHelper: RegisterHelper
    private lateinit var adapter: RegisterAdapter
    private val EXTRA_STATE = "EXTRA_SATE"

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var coordinatorLayout: CoordinatorLayout? = null

    @SuppressLint("SwitchIntDef")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.daftar_user)
        binding.rvRegister.layoutManager = LinearLayoutManager(this)
        binding.rvRegister.setHasFixedSize(true)
        adapter = RegisterAdapter(this)
        binding.rvRegister.adapter = adapter

        coordinatorLayout = binding.clDetail

        handleViewOnEmpty()

        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                showSnackbarMessage(getString(R.string.error_no_hardware))
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                showSnackbarMessage(getString(R.string.error_hw_unavailable))
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                showSnackbarMessage(getString(R.string.error_none_enrolled))
            }
        }

        val executor: Executor = ContextCompat.getMainExecutor(this)

        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showSnackbarMessage(errString.toString())
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    showSnackbarMessage(getString(R.string.success))
                    coordinatorLayout!!.visibility = View.VISIBLE
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.title_biometric_prompt))
            .setDescription(getString(R.string.description_biometric_prompt))
            .setDeviceCredentialAllowed(true).build()

        biometricPrompt.authenticate(promptInfo)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, DataDiriActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }

        registerHelper = RegisterHelper.getInstance(applicationContext)
        registerHelper.open()
        if (savedInstanceState == null) {
            loadRegister()
        } else {
            val list = savedInstanceState.getParcelableArrayList<RegisterModel>(EXTRA_STATE)
            if (list != null) {
                adapter.listRegister = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listRegister)
    }

    private fun loadRegister() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressbar.visibility = View.VISIBLE
            val cursor = registerHelper.queryAll()
            val register = mapCursorToArrayList(cursor)
            binding.progressbar.visibility = View.INVISIBLE

            if (register.size > 0) {
                adapter.listRegister = register
                handleViewOnEmpty()
            } else {
                adapter.listRegister = ArrayList()
                handleViewOnEmpty()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun handleViewOnEmpty() {
        if (adapter.listRegister.size > 0) {
            binding.tvEmpty.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.VISIBLE
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvRegister, message, Snackbar.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                    val regist =
                        data.getParcelableExtra<RegisterModel>(EXTRA_REGISTRATION) as RegisterModel
                    adapter.addItem(regist)
                    binding.rvRegister.smoothScrollToPosition(adapter.itemCount - 1)
                    adapter.notifyDataSetChanged()
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                    handleViewOnEmpty()

                }
                REQUEST_UPDATE -> when (resultCode) {
                    RESULT_UPDATE -> {
                        val regist =
                            data.getParcelableExtra<RegisterModel>(EXTRA_REGISTRATION) as RegisterModel
                        val position = data.getIntExtra(EXTRA_POSITION, 0)
                        adapter.updateItem(position, regist)
                        binding.rvRegister.smoothScrollToPosition(position)
                        adapter.notifyDataSetChanged()
                        showSnackbarMessage("Satu item berhasil diubah")
                        handleViewOnEmpty()
                    }
                    RESULT_DELETE -> {
                        val position = data.getIntExtra(EXTRA_POSITION, 0)
                        adapter.removeItem(position)
                        adapter.notifyDataSetChanged()
                        showSnackbarMessage("Satu item berhasil dihapus")
                        handleViewOnEmpty()
                    }

                }
            }
        }
    }
}