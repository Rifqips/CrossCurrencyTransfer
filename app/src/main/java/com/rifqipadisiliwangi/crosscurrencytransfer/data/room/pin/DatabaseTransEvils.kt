package com.rifqipadisiliwangi.crosscurrencytransfer.data.room.pin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataPin::class], version = 1)
abstract class DatabaseTransEvils : RoomDatabase() {
    abstract fun PinDaoTransEvilz() : PinDao

    companion object{
        private var INSTANCE : DatabaseTransEvils? = null

        fun getInstance(context : Context): DatabaseTransEvils? {
            if (INSTANCE == null){
                synchronized(DatabaseTransEvils::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DatabaseTransEvils::class.java,"DataPin.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}