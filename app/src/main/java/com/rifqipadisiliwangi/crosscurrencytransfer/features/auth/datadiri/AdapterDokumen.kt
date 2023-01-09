package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.rifqipadisiliwangi.crosscurrencytransfer.R

class AdapterDokumen(internal var context: Context, internal var listDokumen: Array<String>):
    BaseAdapter(){
    internal var inflter: LayoutInflater

    init {
        inflter = LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return listDokumen.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val view = inflter.inflate(R.layout.custom_spinner_dokumen, null)
        val names = view.findViewById<View>(R.id.tvNama) as TextView?
        names!!.text = listDokumen[i]
        return view
    }
}