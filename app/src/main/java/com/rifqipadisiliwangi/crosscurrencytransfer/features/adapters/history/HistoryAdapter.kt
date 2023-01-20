package com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemHistoryBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.history.HistorySingleton

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val data = mutableListOf<HistorySchemeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data[position])

    }

    override fun getItemCount(): Int = data.size

    fun submitList(list: List<HistorySchemeItem>){
        val initSize = itemCount
        data.clear()
        notifyItemRangeRemoved(0, initSize)
        data.addAll(list)
        notifyItemRangeInserted(0, data.size)
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun setData(list: HistorySchemeItem){
            with(binding){
                tvName.text = list.receipentName
                tvCodeBank.text = list.virtualAccount
                tvNoRekening.text = list.receipentNorek
                tvTotal.text = list.total
                tvId.text = list.id
                tvTipeTransaksi.text = list.typeTransaction
            }
        }
    }
}