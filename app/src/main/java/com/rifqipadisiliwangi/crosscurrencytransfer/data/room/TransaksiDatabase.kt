package com.rifqipadisiliwangi.crosscurrencytransfer.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataTransaksiRoom::class], version = 2, exportSchema = false)

abstract class TransaksiDatabase : RoomDatabase() {

    abstract fun noteDao(): TransaksiDAO

    companion object{

        private var INSTANCE : TransaksiDatabase? = null

        fun getInstance(context: Context):TransaksiDatabase? {
            if (INSTANCE == null){
                synchronized(TransaksiDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TransaksiDatabase::class.java,"Transaksi.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}