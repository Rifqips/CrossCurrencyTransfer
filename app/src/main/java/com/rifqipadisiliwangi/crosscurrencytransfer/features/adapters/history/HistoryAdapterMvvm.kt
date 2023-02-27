package com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore.DataStoreTransaksi
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.transaksimvvm.TransaksiTransferItem
import com.rifqipadisiliwangi.crosscurrencytransfer.data.room.DataTransaksiRoom
import com.rifqipadisiliwangi.crosscurrencytransfer.data.room.TransaksiDatabase
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemHistoryBinding

class HistoryAdapterMvvm(private var listHistory : List<DataTransaksiRoom>) : RecyclerView.Adapter<HistoryAdapterMvvm.ViewHolder>(){

    var DBNote: TransaksiDatabase? = null

    class ViewHolder(var binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        val view = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapterMvvm.ViewHolder {
        val view = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: HistoryAdapterMvvm.ViewHolder, position: Int) {
        holder.binding.tvName.text = listHistory[position].namaPenerima
        holder.binding.tvCodeBank.text = listHistory[position].jenisBank
        holder.binding.tvNoRekening.text = listHistory[position].noRekening
        holder.binding.tvTotal.text = listHistory[position].total
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    fun setNoteData(listNote: ArrayList<DataTransaksiRoom>) {
        this.listHistory = listNote
    }
}