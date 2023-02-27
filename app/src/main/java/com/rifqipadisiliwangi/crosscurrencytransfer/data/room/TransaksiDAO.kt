package com.rifqipadisiliwangi.crosscurrencytransfer.data.room

import androidx.room.*

@Dao
interface TransaksiDAO {

    @Insert
    fun insertTransaksi(note : DataTransaksiRoom)

    @Query(" SELECT * FROM DataTransaksiRoom ORDER BY id DESC")
    fun getDataTransaksi() :List<DataTransaksiRoom>

    @Delete
    fun deleteNote(note: DataTransaksiRoom) : Int

    @Update
    fun updateNote(note: DataTransaksiRoom) : Int

}