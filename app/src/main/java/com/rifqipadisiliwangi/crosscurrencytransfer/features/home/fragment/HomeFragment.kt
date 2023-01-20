package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreUser
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterModel
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.ListDummy
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.ListDummyCurrency
import com.rifqipadisiliwangi.crosscurrencytransfer.data.utility.LoadingDialog
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentHomeBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemCodepickerBinding.inflate
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.LoadingItemBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history.DummyCurrencyAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history.DummyHistoryAdapter
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
    lateinit var dialogBinding: LoadingItemBinding

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
        setListDummy()

        val loading = LoadingDialog(requireActivity())
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object : java.lang.Runnable {
            override fun run() {
                loading.isDismiss()
            }

        },2000)

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
    }

    private fun setListDummy(){
        var listHistory = arrayListOf(
            ListDummy(R.drawable.indonesia,"IDR ke USD", "BCC-12341234","Rp. 1000.000"),
            ListDummy(R.drawable.australia,"IDR ke USD", "BCC-12341234","Rp. 1000.000"),
            ListDummy(R.drawable.usa,"IDR ke USD", "BCC-12341234","Rp. 1000.000"),
        )

        binding.rvTransaksiTerakhir.adapter = DummyHistoryAdapter(listHistory)
        binding.rvTransaksiTerakhir.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL,false)

        var listCurrency = arrayListOf(
            ListDummyCurrency(R.drawable.japan,"IDR ke USD", "+0,20","Rp. 1000.000"),
            ListDummyCurrency(R.drawable.australia,"IDR ke USD", "+0,20","Rp. 1000.000"),
            ListDummyCurrency(R.drawable.usa,"IDR ke USD", "+0,20","Rp. 1000.000"),
        )

        binding.rvCurrency.adapter = DummyCurrencyAdapter(listCurrency)
        binding.rvCurrency.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL,false)

    }

}