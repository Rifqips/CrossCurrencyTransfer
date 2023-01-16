package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransaksiDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.network.api.history.HistoryApi
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityCobaHistoryBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history.HistoryAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.HistoryPresenter

class CobaHistoryActivity : AppCompatActivity(), HistoryView.View {

    private lateinit var binding : ActivityCobaHistoryBinding

    private val adapterUser: HistoryAdapter by lazy { HistoryAdapter() }
    private val recipeLiveData = MutableLiveData<List<TransaksiDataItem>>()
    private lateinit var presenter: HistoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCobaHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HistoryPresenter(this@CobaHistoryActivity, HistoryApi()).apply {
            onAttach()
        }

        Toast.makeText(this,"Loading", Toast.LENGTH_LONG).show()

        binding.rvHistory.apply {
            adapter = this@CobaHistoryActivity.adapterUser
            layoutManager = LinearLayoutManager(this@CobaHistoryActivity)
            LinearLayoutManager.VERTICAL
        }

        recipeLiveData.observe(this){
            adapterUser.submitList(it)
        }
    }

    override fun onPause() {
        super.onPause()
        presenter = HistoryPresenter(this@CobaHistoryActivity, HistoryApi()).apply {
            onDetach()
        }
    }

    override fun onLoading() {
        binding.progressBarPengirim.visibility = View.VISIBLE
        binding.rvHistory.visibility = View.GONE
        Toast.makeText(this,"onLoading", Toast.LENGTH_LONG).show()

    }

    override fun onFinishedLoading() {
        binding.progressBarPengirim.visibility = View.GONE
        binding.rvHistory.visibility = View.VISIBLE
        Toast.makeText(this,"onFinishedLoading", Toast.LENGTH_LONG).show()

    }

    override fun onError(message: String) {
        Toast.makeText(this,"onError", Toast.LENGTH_LONG).show()
    }

    override fun onSuccessGetUser(user: List<TransaksiDataItem>) {
        adapterUser.submitList(user)
        Toast.makeText(this,"onSuccessGetUser", Toast.LENGTH_LONG).show()

    }
}
