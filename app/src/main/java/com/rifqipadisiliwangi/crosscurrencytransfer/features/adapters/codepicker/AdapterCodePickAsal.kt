package com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.codepicker

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.codepicker.ListCodeAsal
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemCodepickerBinding

class AdapterCodePickAsal (): RecyclerView.Adapter<AdapterCodePickAsal.ViewHolder>() {

    private var codeAsal : ArrayList<ListCodeAsal> = ArrayList()

    class ViewHolder(var binding: ItemCodepickerBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCodepickerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCodeCountry.text = codeAsal[position].codeCurency
        holder.binding.tvDefaultCountry.text = codeAsal[position].defaultcurency
        holder.binding.ivCodepicker.setImageResource(codeAsal[position].image)
    }

    override fun getItemCount(): Int {
        return codeAsal.size
    }
}