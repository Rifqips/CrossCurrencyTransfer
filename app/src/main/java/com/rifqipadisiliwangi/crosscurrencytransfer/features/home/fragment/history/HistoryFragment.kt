package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransactionSchemeResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.HistoryApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.pin.PinApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentHistoryBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history.HistoryAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin.PinPresenter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin.PinView
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.HistoryPresenter

class HistoryFragment : Fragment(), HistoryView {

    private lateinit var binding : FragmentHistoryBinding
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }
    private val recipeLiveData = MutableLiveData<List<HistorySchemeItem>>()
    private val presenter = HistoryPresenter(HistoryApi())

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
        presenter.onAttach(this)
        binding.rvHistory.apply {
            adapter = this@HistoryFragment.adapter
            layoutManager = LinearLayoutManager(requireActivity())

        }

        recipeLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }

    override fun onSuccessHistory(user: List<HistorySchemeItem>) {
        adapter.submitList(user)
    }

    override fun onLoading() {
        Toast.makeText(context, "onLoading", Toast.LENGTH_SHORT).show()
    }

    override fun onFinishedLoading() {
        Toast.makeText(context, "onFinishedLoading", Toast.LENGTH_SHORT).show()
    }

    override fun onError(code: Int, message: String) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }
}