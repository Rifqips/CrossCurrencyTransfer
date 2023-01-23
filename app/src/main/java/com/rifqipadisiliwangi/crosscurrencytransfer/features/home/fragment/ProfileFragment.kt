package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterModel
import com.rifqipadisiliwangi.crosscurrencytransfer.data.utility.LoadingDialog
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentProfileBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register.RegisterActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.maps.MapsActivity
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    var currLang = ""
    private lateinit var registerModel: RegisterModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLang()
        validate()
        binding.constrainSyarat.setOnClickListener {
            val url = "https://www.privacypolicyonline.com/live.php?token=LAdIxN9mK04Y9nItIY7JWXheDzZ2wI5w"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

//        val registerImage: ByteArray = registerModel.image!!
//        val bitmap = BitmapFactory.decodeByteArray(registerImage, 0, registerImage.size)
//        binding.ivSetImage.setImageBitmap(bitmap)

    }

    @SuppressLint("InflateParams", "MissingInflatedId")
    private fun validate(){
        binding.tvLogout.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity())
            val viewLogout = layoutInflater.inflate(R.layout.bottom_sheet_logout, null)
            val btnAgree = viewLogout.findViewById<Button>(R.id.idBtnAgree)
            val btnDisagree = viewLogout.findViewById<Button>(R.id.idBtnDismiss)
            btnAgree.setOnClickListener {
                startActivity(Intent(context, LoginActivity::class.java))
            }
            btnDisagree.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(viewLogout)
            dialog.show()
        }
    }

    private fun setUpLang(){
        binding.swLanguange.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                "Switch1:ON"
                val loading = LoadingDialog(requireActivity())
                loading.startLoading()
                val handler = Handler()
                handler.postDelayed(object : java.lang.Runnable {
                    override fun run() {
                        getLocale()
                        setLocate("en")
                        loading.isDismiss()
                    }

                },500)
                Toast.makeText(context,"You should change the view first !", Toast.LENGTH_SHORT).show()
            } else {
                // The switch is disabled
                "Switch1:OFF"
                val loading = LoadingDialog(requireActivity())
                loading.startLoading()
                val handler = Handler()
                handler.postDelayed(object : java.lang.Runnable {
                    override fun run() {
                        getLocale()
                        setLocate("in")
                        loading.isDismiss()
                        onResume()
                    }

                },500)
                Toast.makeText(context,"You should change the view first !", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setLocate(lang : String){
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val resources = context?.resources
        val configuration = resources?.configuration
        configuration?.locale = locale
        configuration?.setLayoutDirection(locale)
        resources?.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun getLocale() {
        val lang = resources.getConfiguration().locale.getLanguage()
        currLang = lang
    }
}