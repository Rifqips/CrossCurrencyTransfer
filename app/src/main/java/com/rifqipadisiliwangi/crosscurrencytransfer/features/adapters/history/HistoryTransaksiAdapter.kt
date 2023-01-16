package com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksi.TransaksiDataItem
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemHistoryBinding

class HistoryTransaksiAdapter : RecyclerView.Adapter<HistoryTransaksiAdapter.ViewHolder>() {

    private val historyData = mutableListOf<TransaksiDataItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(historyData[position])
    }

    override fun getItemCount(): Int = historyData.size

    fun submitList(list: List<TransaksiDataItem>){
        val initSize = itemCount
        historyData.clear()
        notifyItemRangeRemoved(0, initSize)
        historyData.addAll(list)
        notifyItemRangeInserted(0, historyData.size)
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun setData(item : TransaksiDataItem){
            with(binding){
                tvName.text = item.namaPenerima
                tvCodeBank.text = item.jenisBank
                tvNoRekening.text = item.noRekening
                tvTotal.text = item.total
                Log.e("Data Muncul Dong", tvName.toString())
            }
        }
    }

}