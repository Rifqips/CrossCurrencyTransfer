package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqipadisiliwangi.crosscurrencytransfer.data.room.DataTransaksiRoom
import com.rifqipadisiliwangi.crosscurrencytransfer.data.room.TransaksiDatabase
import com.rifqipadisiliwangi.crosscurrencytransfer.data.utility.LoadingDialog
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentHistoryBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history.HistoryAdapterMvvm
import com.rifqipadisiliwangi.crosscurrencytransfer.viewmodel.ViewModelHistory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding

    var TransaksiDB : TransaksiDatabase? = null
    lateinit var adapterNote : HistoryAdapterMvvm
    lateinit var viewModel: ViewModelHistory

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
        historyVm()
        TransaksiDB = TransaksiDatabase.getInstance(requireActivity())

        viewModel = ViewModelProvider(this).get(ViewModelHistory::class.java)
        viewModel.getAllNoteObservers().observe(requireActivity(),{
            adapterNote.setNoteData(it as ArrayList<DataTransaksiRoom>)
        })
    }

    fun historyVm(){

        val loading = LoadingDialog(requireActivity())
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object : java.lang.Runnable {
            override fun run() {
                loading.isDismiss()
            }
        },500)
        adapterNote = HistoryAdapterMvvm(ArrayList())
        binding.rvHistory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvHistory.adapter = adapterNote
    }

    fun getAllNote(){
        GlobalScope.launch {
            var data = TransaksiDB?.noteDao()?.getDataTransaksi()
            run{
                adapterNote = HistoryAdapterMvvm(data!!)
                binding.rvHistory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rvHistory.adapter = adapterNote
            }
        }
    }
}