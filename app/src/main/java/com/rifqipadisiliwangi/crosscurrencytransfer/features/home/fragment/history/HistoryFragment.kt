package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.HistoryDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransaksiDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.HistoryApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.otp.OtpApi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.transaksi.TranskasiApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentHistoryBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history.HistoryAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.HistoryPresenter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.OtpPresenter

class HistoryFragment : Fragment(), HistoryView.View {

    private lateinit var binding : FragmentHistoryBinding
    private val adapterUser: HistoryAdapter by lazy { HistoryAdapter() }
    private val recipeLiveData = MutableLiveData<List<HistoryDataItem>>()
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

        presenter = HistoryPresenter(this@HistoryFragment, HistoryApi()).apply {
            onAttach()
        }

        Toast.makeText(context,"Loading", Toast.LENGTH_LONG).show()

        binding.rvHistory.apply {
            adapter = this@HistoryFragment.adapterUser
            layoutManager = LinearLayoutManager(context)
            LinearLayoutManager.VERTICAL
        }

        recipeLiveData.observe(viewLifecycleOwner){
            adapterUser.submitList(it)
        }
    }

    override fun onLoading() {
        binding.progressBarPengirim.visibility = View.VISIBLE
        binding.rvHistory.visibility = View.GONE
        Toast.makeText(context,"onLoading", Toast.LENGTH_LONG).show()

    }

    override fun onFinishedLoading() {
        binding.progressBarPengirim.visibility = View.GONE
        binding.rvHistory.visibility = View.VISIBLE
        Toast.makeText(context,"onFinishedLoading", Toast.LENGTH_LONG).show()

    }

    override fun onError(message: String) {
        Toast.makeText(context,"onError", Toast.LENGTH_LONG).show()
    }

    override fun onSuccessGetUser(user: List<HistoryDataItem>) {
        adapterUser.submitList(user)
        Toast.makeText(context,"onSuccessGetUser", Toast.LENGTH_LONG).show()

    }
}