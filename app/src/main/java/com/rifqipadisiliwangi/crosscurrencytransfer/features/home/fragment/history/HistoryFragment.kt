package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreUser
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransactionSchemeResponse
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.HistoryApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.ListScheme
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.pin.PinApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentHistoryBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history.HistoryAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin.PinPresenter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.pin.PinView
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.HistoryPresenter

class HistoryFragment : Fragment(), HistoryView.View {

    private lateinit var binding : FragmentHistoryBinding
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }
    private val recipeLiveData = MutableLiveData<List<HistorySchemeItem>>()
    private lateinit var presenter: HistoryPresenter

    lateinit var dataStoreUser : DataStoreUser
    var namaUser = ""

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
        presenter = HistoryPresenter(this@HistoryFragment, HistoryApi()).apply {
            onAttach()
        }

        binding.rvHistory.apply {
            adapter = this@HistoryFragment.adapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        Log.e("Data Ditemukan:", adapter.toString())

        recipeLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }
    override fun onLoading() {
        Toast.makeText(context, "onLoading", Toast.LENGTH_SHORT).show()
    }

    override fun onFinishedLoading() {
        Toast.makeText(context, "onFinishedLoading", Toast.LENGTH_SHORT).show()
    }

    override fun onErrorHistory(code: Int, message: String) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessHistory(user: ListScheme) {
        Toast.makeText(context, "onSuccessHistory", Toast.LENGTH_SHORT).show()
        Log.d("sukses-gethistory","$user")
    }

    override fun onError(message: String) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessGetHistory(history: List<HistorySchemeItem>) {
//        adapter.submitList(history)
        recipeLiveData.value = history
    }
}