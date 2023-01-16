package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.ALERT_DIALOG_CLOSE
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.ALERT_DIALOG_DELETE
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.EXTRA_POSITION
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.EXTRA_REGISTRATION
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.RESULT_ADD
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.RESULT_DELETE
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.RESULT_UPDATE
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterModel
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.auth.register.RegisterApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.utility.DatabaseRegsiter
import com.rifqipadisiliwangi.crosscurrencytransfer.data.utility.RegisterHelper
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityDataDiriBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.profile.DetailProfileActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.helper.makeClearableEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*

@Suppress("DEPRECATION")
class DataDiriActivity : AppCompatActivity(),RegisterView, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var binding : ActivityDataDiriBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var registerHelper: RegisterHelper
    private var imageView: ImageView? = null

    private val permissionId = 55
    private val RESULT_LOAD_IMAGE = 123
    private val REQUEST_CODE_GALLERY = 999
    private var jenisKelamin: String = ""
    private var lokasi: String = ""

    private var isEdit = false
    private var register: RegisterModel? = null
    private var position: Int = 0

    private var longitude: Double = 0.0
    private var latitude: Double = 0.0

    var tipeDokumen = arrayOf("Passport", "KTP", "SIM")
    private val presenter = RegisterPresenter(RegisterApi())
    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DATE)
    var validasiTglLahir = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataDiriBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onAttach(this)

        loadSpiner()
        clearValidasi()
        validasiEditText()
        addSqliteUser()

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        registerHelper = RegisterHelper.getInstance(applicationContext)
        registerHelper.open()

        binding.ibCalender.setOnClickListener {
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val bulan = monthOfYear + 1
                binding.tvIsiTgllahir.text = "$dayOfMonth/$bulan/$year"
                validasiTglLahir = "benar"
            }, year,month,day )

            val minDay = 1
            val minMonth = 0
            val minYear = 2004

            calender.set(minYear, minMonth, minDay)
            dpd.datePicker.minDate = calender.timeInMillis
            dpd.show()
        }
    }

    override fun onLoading() {
        binding.progressBar.isVisible = true
    }

    override fun onFinishedLoading() {
        binding.progressBar.isVisible = false
    }

    override fun onError(code: Int, message: String) {
        when(code){
            1 -> {
                binding.tvWarningKatasandiDatadiri.text = message
            }
            2 -> {
                binding.tvWarningKatasandiDatadiri.text = message
                binding.tvWarningKatasandiDatadiri.isVisible = false
            }
            else -> {binding.tvWarningKatasandiDatadiri.isVisible = false}
        }

    }

    override fun onErrorEmail(code: Int, message: String) {
        when (code){
            2 -> {
                binding.tvWarningEmail.text = message
            }
            3 -> {
                binding.tvWarningEmail.text = message
                binding.tvWarningEmail.isVisible = false
            }else ->{
                binding.tvWarningEmail.text = "Format email salah"

            }
        }
    }

    override fun onErrorPassword(visible: Boolean, message: String) {
        Toast.makeText(this, "Error Password When Login", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessRegister() {
//        startActivity(Intent(this, LoginActivity::class.java))
        presenter.register("","",0,"","","","",0,"","",)
        Toast.makeText(this, "Success Register", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessGetUser(user: RegisterDataItem) {
        AlertDialog.Builder(this)
            .setMessage("user -> $user")
            .setPositiveButton("Ok", this::dialogClickListener)
            .setNegativeButton("Cancel", this::dialogClickListener)
            .create()
            .show()
    }
    private fun clearValidasi(){

        addRightCancelDrawable(binding.etEmail)
        addRightCancelDrawable(binding.etMasukkanDokumen)
        addRightCancelDrawable(binding.etNamaDepan)
        addRightCancelDrawable(binding.etNamaBelakang)
        addRightCancelDrawable(binding.etTempatlahir)
        addRightCancelDrawable(binding.etAlamat)
        binding.etEmail.makeClearableEditText(null, null)
        binding.etMasukkanDokumen.makeClearableEditText(null, null)
        binding.etNamaDepan.makeClearableEditText(null, null)
        binding.etNamaBelakang.makeClearableEditText(null, null)
        binding.etTempatlahir.makeClearableEditText(null, null)
        binding.etAlamat.makeClearableEditText(null, null)

    }

    private fun validasiEditText(){

        binding.etkonfirmasiKatasandiDatadiri.addTextChangedListener { confirmPassword ->
            if (confirmPassword.toString() != binding.etKatasandi.text.toString()) {
                binding.tvWarningkonfirmasiKatasandiDatadiri.isVisible = true
                binding.tvWarningkonfirmasiKatasandiDatadiri.text = "Kata Sandi harus Sama"
            } else {
                binding.tvWarningkonfirmasiKatasandiDatadiri.isVisible = false
            }
        }

        binding.etKatasandi.addTextChangedListener {
            presenter.validateCredential(
                it.toString()
            )
        }

        binding.etEmail.addTextChangedListener {
            presenter.validateEmail(
                it.toString()
            )
        }

        binding.etMasukkanDokumen.addTextChangedListener{ binding.tvWarningDokumen.isVisible = it == null }
        binding.etNamaDepan.addTextChangedListener { binding.tvWarningNamaDepan.isVisible = it == null }
        binding.etNamaBelakang.addTextChangedListener { binding.tvWarningNamaBelakang.isVisible = it == null }
        binding.etTempatlahir.addTextChangedListener { binding.tvWarningTtl.isVisible = it == null }
        binding.etAlamat.addTextChangedListener { binding.tvWarningAlamat.isVisible = it == null }
    }

    private fun loadSpiner(){
        binding.btnLanjut.isVisible = false
        binding.btnLanjutInvisible.isVisible = true
        var aa = ArrayAdapter(this, R.layout.spinner_right_aligned, tipeDokumen)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(binding.mySpinner)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@DataDiriActivity
            prompt = "Pilih Dokumen"
            gravity = Gravity.CENTER

        }

        val ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        ll.setMargins(10, 40, 10, 10)

        aa = ArrayAdapter(this, R.layout.spinner_right_aligned, tipeDokumen)
        aa.setDropDownViewResource(R.layout.spinner_right_aligned)
    }

    private fun postRegister(){
            presenter.register(
                binding.etEmail.text.toString(),
                binding.mySpinner.selectedItem.toString(),
                binding.etMasukkanDokumen.text.toString().toInt(),
                binding.etNamaDepan.text.toString(),
                binding.etNamaBelakang.text.toString(),
                binding.etTempatlahir.text.toString(),
                binding.etAlamat.text.toString(),
                binding.etPhone.text.toString().toInt(),
                binding.etKatasandi.text.toString(),
                binding.rbPria.text.toString()
            )
    }

    private fun dialogClickListener(dialogInterface: DialogInterface, button: Int) {
        when (button) {
            DialogInterface.BUTTON_NEGATIVE -> {}
            DialogInterface.BUTTON_POSITIVE -> {}
            DialogInterface.BUTTON_NEUTRAL -> {}
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (view?.id) {
            1 -> showToast(message = "")
            else -> {
                Snackbar.make(binding.btnLanjutInvisible, "Dokumen Yang dipilih : ${tipeDokumen[position]}", Snackbar.LENGTH_LONG).show()
            }
        }
        binding.btnLanjut.isVisible = true
        binding.btnLanjutInvisible.isVisible = false
    }

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()
    }

    private fun addRightCancelDrawable(editText: EditText) {
        val cancel = ContextCompat.getDrawable(this, R.drawable.ic_baseline_cancel_24)
        cancel?.setBounds(0,0, cancel.intrinsicWidth, cancel.intrinsicHeight)
        editText.setCompoundDrawables(null, null, cancel, null)
    }

    private fun addSqliteUser(){

        register = intent.getParcelableExtra(EXTRA_REGISTRATION)
        if (register != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            register = RegisterModel()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit){
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            register?.let { it ->
                binding.etEmail.setText(it.name)
                binding.etAlamat.setText(it.alamat)
                binding.etPhone.setText(it.phone)

                //Handle Jenis Kelamin

                if (it.jk.equals("Laki-Laki")){
                    binding.rbPria.isChecked = true
                    jenisKelamin = "Laki Laki"
                } else {
                    binding.rbWanita.isChecked = true
                    jenisKelamin = "Perempuan"
                }

                //handle location

                binding.tvCurrentLocation.text = it.location

                val registerImage: ByteArray? = it.image
                val bitmap =
                    registerImage?.let { it1 -> BitmapFactory.decodeByteArray(registerImage, 0, it1.size) }
                binding.ivImageUser.setImageBitmap(bitmap)
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        actionBar?.title = actionBarTitle
        binding.btnLanjut.text = btnTitle
        binding.btnLanjut.setOnClickListener(this)

        imageView = binding.ivImageUser

        binding.btnCekLokasi.setOnClickListener {
            getLocation()
        }

        binding.ivImageUser.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE)
        }

        imageView!!.setOnClickListener {

            ActivityCompat.requestPermissions(
                this@DataDiriActivity, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE

                ),
                REQUEST_CODE_GALLERY
            )
        }

        binding.etEmail.setOnEditorActionListener { v, actionId, event ->
            register?.let { it ->
                it.name = v.text.toString()
            }
            false
        }

        setOnCheckedChangeListener()

        binding.etPhone.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                //not focused
                binding.etPhone.clearFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etPhone.windowToken, 0)

                true
            } else {
                false
            }
        }

    }
    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Register"
        }
        val alertDialogBuilder = android.app.AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    val result = registerHelper.deleteById(register?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@DataDiriActivity,
                            "Gagal menghapus data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE_GALLERY)
            } else {
                Toast.makeText(
                    applicationContext,
                    "You don't have permission to access file location!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }

    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        GlobalScope.async {

                            val list: List<Address> =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            binding.apply {
                                tvCurrentLocation.text = list[0].getAddressLine(0)
                                lokasi = list[0].getAddressLine(0)
                                latitude = list[0].latitude
                                longitude = list[0].longitude
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            val uri: Uri? = data.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                val resized = Bitmap.createScaledBitmap(bitmap, 400, 400, true)
                imageView!!.setImageBitmap(resized)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun setOnCheckedChangeListener() {
        binding.rgJenisKelamin.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            jenisKelamin = radio.text.toString()
            Log.i(ContentValues.TAG, "onCheckedChangeListener: $jenisKelamin")
            Toast.makeText(this, radio.text, Toast.LENGTH_SHORT).show()
        }
    }


    private fun imageViewToByte(image: ImageView): ByteArray? {
        val bitmap = (image.getDrawable() as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onClick(view: View?) {
        if(view?.id == R.id.btnLanjut) {
            postRegister()
            val name = binding.etEmail.text.toString().trim()
            val alamat = binding.etAlamat.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            if(name.isEmpty()) {
                binding.etEmail.error = "Nama tidak boleh kosong"
                binding.etEmail.requestFocus()
                return
            }

            register?.name = name
            register?.alamat = alamat
            register?.phone = phone
            register?.jk = jenisKelamin
            register?.location = lokasi
            register?.latitude = latitude
            register?.longitude = longitude
            register?.image = imageViewToByte(imageView!!)

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.putExtra(EXTRA_REGISTRATION, register)
            intent.putExtra(EXTRA_POSITION, position)
            val values = ContentValues()
            values.put(DatabaseRegsiter.RegisterColumns.NAME, register?.name)
            values.put(DatabaseRegsiter.RegisterColumns.ALAMAT, register?.alamat)
            values.put(DatabaseRegsiter.RegisterColumns.PHONE, register?.phone)
            values.put(DatabaseRegsiter.RegisterColumns.JK, register?.jk)
            values.put(DatabaseRegsiter.RegisterColumns.LOCATION, register?.location)
            values.put(DatabaseRegsiter.RegisterColumns.LATITUDE, register?.latitude)
            values.put(DatabaseRegsiter.RegisterColumns.LONGITUDE, register?.longitude)
            values.put(DatabaseRegsiter.RegisterColumns.IMAGE, register?.image)
            startActivity(Intent(this, HomeBottomActivity::class.java))

            if(isEdit) {
                val result = registerHelper.update(
                    register?.id.toString(),
                    values
                ).toLong()
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                }
            } else {
                val result = registerHelper.insert(values)
                if (result > 0) {
                    register?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@DataDiriActivity,
                        "Gagal menambah data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}