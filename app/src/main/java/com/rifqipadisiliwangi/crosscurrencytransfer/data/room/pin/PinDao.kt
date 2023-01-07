package com.rifqipadisiliwangi.crosscurrencytransfer.data.room.pin

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PinDao {

    @Insert
    fun addToPin(dataPinTranzEvils: DataPin):Long

    @Query("SELECT * FROM DataPin")
    fun getPinTransEvils() : List<DataPin>

    //
    @Query("SELECT count(*) FROM DataPin WHERE DataPin.id = :id")
    fun checkPin(id: Int):Int

    @Delete
    fun deletePin(dataPinTranzEvils: DataPin) : Int
}