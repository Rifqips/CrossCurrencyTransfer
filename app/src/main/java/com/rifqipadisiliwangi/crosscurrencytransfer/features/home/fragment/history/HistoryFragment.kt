package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransaksiDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentHistoryBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history.HistoryAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.HistoryPresenter

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding
    private val adapterUser: HistoryAdapter by lazy { HistoryAdapter() }
    private val recipeLiveData = MutableLiveData<List<TransaksiDataItem>>()
    private lateinit var presenter: HistoryPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}