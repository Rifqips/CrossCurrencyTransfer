package com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.codepicker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.codepicker.CountryData
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ItemCodepickerBinding
import kotlinx.coroutines.flow.combine

class CountrySpinnerAdapter( context: Context, dataSource: List<CountryData>,
//                             private val clickListener: (CountryData?) -> Unit

): ArrayAdapter<CountryData>(context, 0, dataSource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        return this.createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.createView(position, convertView, parent)
    }

    private fun createView(position: Int, itemView: View?, parent: ViewGroup):View{
//        val view = itemView?: LayoutInflater.from(context).inflate(R.layout.item_codepicker, parent)
        val view  = ItemCodepickerBinding.inflate(LayoutInflater.from(context),parent,false)
        val item: CountryData? = getItem(position)

        if (item != null){
            view.ivCodepicker.setImageResource(item.flagResource)
            itemView?.setOnClickListener {
//                clickListener.invoke(item)
            }
       }
        return parent
    }
}