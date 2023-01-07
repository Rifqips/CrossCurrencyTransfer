package com.rifqipadisiliwangi.crosscurrencytransfer.data.room.pin

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DataPin(
    @PrimaryKey
    val id : Int?,
    @ColumnInfo(name = "pin")
    var pin : String,
    var pinKonfirmasi : String,
) : Parcelable
