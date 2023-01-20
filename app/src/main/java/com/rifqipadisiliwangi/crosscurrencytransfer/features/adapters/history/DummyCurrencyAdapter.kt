package com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.history.ListDummyCurrency
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemDummyCurrencyBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemDummyHistoryBinding

class DummyCurrencyAdapter (var historyList : ArrayList<ListDummyCurrency>): RecyclerView.Adapter<DummyCurrencyAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemDummyCurrencyBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemDummyCurrencyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ivAvatar.setImageResource(historyList[position].gambar)
        holder.binding.tvCodeCountry.text = historyList[position].codeNedara
        holder.binding.tvNoRekening.text = historyList[position].noRek
        holder.binding.tvTotal.text = historyList[position].total
    }

    fun setDataHistory(HistoryList : ArrayList<ListDummyCurrency>){
        this.historyList = HistoryList
    }

    override fun getItemCount(): Int {
        return historyList.size

    }
}
