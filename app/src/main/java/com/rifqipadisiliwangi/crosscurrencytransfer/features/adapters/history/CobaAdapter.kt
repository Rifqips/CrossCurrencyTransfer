package com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.HistorySchemeItem
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemHistoryBinding

class CobaAdapter(val listHistory : List<HistorySchemeItem>): RecyclerView.Adapter<CobaAdapter.ViewHolder>() {
    class ViewHolder (var binding : ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvName.text = listHistory[position].receipentName
        holder.binding.tvCodeBank.text = listHistory[position].virtualAccount
        holder.binding.tvNoRekening.text = listHistory[position].receipentNorek
        holder.binding.tvTotal.text = listHistory[position].total
        holder.binding.tvTotal.text = listHistory[position].typeTransaction
    }


    override fun getItemCount(): Int {
        return listHistory.size
    }
}