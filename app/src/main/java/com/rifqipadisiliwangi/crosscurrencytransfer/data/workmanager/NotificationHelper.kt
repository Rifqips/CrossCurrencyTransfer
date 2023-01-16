package com.rifqipadisiliwangi.crosscurrencytransfer.data.workmanager

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity

class NotificationHelper(val context : Context) {
    private val CHANNEL_ID = "notif"
    private val NOTIFICATION_ID = 1


    @SuppressLint("UnspecifiedImmutableFlag")
    fun createNotif(tittle : String, message : String){
        createNotifChannel()
        val intent = Intent(context, DataDiriActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        val pendingIntent = PendingIntent.getActivity(context,0,intent,0)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.logo)

        val notification = NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(icon)
            .setContentTitle(tittle)
            .setContentText(message)
            .setStyle(
//                NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null)
                NotificationCompat.BigTextStyle().bigText(null).setBigContentTitle(null)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotifChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)
                .apply {
                    description = "aplikasi telah ditutup"
                }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }
}