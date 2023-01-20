package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreUser
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterModel
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentHomeBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.InternationalTransferActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal.LokalTransferActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.profile.DetailProfileActivity
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
    }

    private lateinit var binding : FragmentHomeBinding
    private lateinit var registerModel: RegisterModel
    private val uiContext: CoroutineContext = Dispatchers.Main
    private val job = SupervisorJob()
    private val scope = CoroutineScope(job + uiContext)

    private val Context.dataStoreUser by preferencesDataStore("dsuser")

    lateinit var dataStoreUser : DataStoreUser
    var namaUser = ""
    private var imageMultiPart = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStoreUser = DataStoreUser(requireContext())
        dataStore()

        dataStoreUser.namaUser.asLiveData().observe(viewLifecycleOwner){
            namaUser = it
            binding.tvUsername.text = it.toString()
        }

        binding.ivSetImage.setOnClickListener {
            startActivity(Intent(requireActivity(), DetailProfileActivity::class.java))
        }

        binding.contraintTfInter.setOnClickListener {
            startActivity(Intent(context, InternationalTransferActivity::class.java))
        }
        binding.contraintTfLokal.setOnClickListener {
            startActivity(Intent(context, LokalTransferActivity::class.java))
        }
    }

    private fun dataStore(){

//        dataStoreUser.fotoUser.asLiveData().observe(requireActivity()){
//            if (it != null && it != "undefined"){
//                Log.d("PHOTO_URL",it)
//                binding.apply {
//                    Glide.with(root.context).load(it).into(ivSetImage)
//                }
//            }
//
//        }
    }
}