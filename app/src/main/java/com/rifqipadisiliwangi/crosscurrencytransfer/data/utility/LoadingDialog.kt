package com.rifqipadisiliwangi.crosscurrencytransfer.data.utility

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.CalendarContract.Colors
import com.rifqipadisiliwangi.crosscurrencytransfer.R

class LoadingDialog(val mActivity: Activity) {
    private lateinit var isdialog: AlertDialog
    fun startLoading(){
        /**set View*/
        val infalter = mActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_item,null)
        /**set Dialog*/
        val bulider = AlertDialog.Builder(mActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()
        isdialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}