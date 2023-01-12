package com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.EXTRA_POSITION
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.EXTRA_REGISTRATION
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.REQUEST_LOCATION
import com.rifqipadisiliwangi.crosscurrencytransfer.data.helper.helper.REQUEST_UPDATE
import com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.register.RegisterModel
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.RcItemBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.maps.MapsActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.profile.EditProfileActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class RegisterAdapter(private val activity: Activity) :
    RecyclerView.Adapter<RegisterAdapter.RegisterViewHolder>() {
    var listRegister = ArrayList<RegisterModel>()
    private val context: Context = activity.baseContext

    val location: Location? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RegisterAdapter.RegisterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rc_item, parent, false)
        return RegisterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegisterAdapter.RegisterViewHolder, position: Int) {
        holder.bind(listRegister[position])

    }

    override fun getItemCount(): Int = this.listRegister.size


    fun createLoopId(position: Int): String {
        var loopId = ""
        for (i in 0 until position) {
            loopId += "0"
        }
        return loopId
    }


    inner class RegisterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RcItemBinding.bind(itemView)
        fun bind(registerModel: RegisterModel) {
            binding.tvNomor.text = createLoopId(adapterPosition)
            binding.tvNama.text = registerModel.name
            binding.tvAlamat.text = registerModel.alamat
            binding.tvPhone.text = registerModel.phone
            binding.tvGender.text = registerModel.jk
            binding.tvLokasi.text = registerModel.location

            val registerImage: ByteArray = registerModel.image!!
            val bitmap = BitmapFactory.decodeByteArray(registerImage, 0, registerImage.size)
            binding.ivFoto.setImageBitmap(bitmap)

//            binding.cvItemRegister.setOnClickListener {
//                val intent = Intent(activity, AddActivity::class.java)
//                intent.putExtra(EXTRA_POSITION, adapterPosition)
//                intent.putExtra(EXTRA_REGISTRATION, registerModel)
//                activity.startActivityForResult(intent, REQUEST_UPDATE)
//            }

            //show popup menu
            binding.cvItemRegister.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.inflate(R.menu.options_menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu1 -> {
                            val intent = Intent(activity, DataDiriActivity::class.java)
                            intent.putExtra(EXTRA_POSITION, adapterPosition)
                            intent.putExtra(EXTRA_REGISTRATION, registerModel)
                            activity.startActivityForResult(intent, REQUEST_UPDATE)
                            true
                        }
                        R.id.menu2 -> {
                            val intent = Intent(activity, MapsActivity::class.java)
                            intent.putExtra(EXTRA_POSITION, adapterPosition)
                            intent.putExtra(EXTRA_REGISTRATION, registerModel)
                            activity.startActivityForResult(intent, REQUEST_LOCATION)
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
    }


    fun addItem(registerModel: RegisterModel) {
        this.listRegister.add(registerModel)
        notifyItemInserted(this.listRegister.size - 1)
    }

    fun updateItem(position: Int, registerModel: RegisterModel) {
        this.listRegister[position] = registerModel
        notifyItemChanged(position, registerModel)
    }

    fun removeItem(position: Int) {
        this.listRegister.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listRegister.size)

    }
}