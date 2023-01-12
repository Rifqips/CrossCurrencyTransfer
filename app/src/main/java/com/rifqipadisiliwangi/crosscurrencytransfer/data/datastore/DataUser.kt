package com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataUser: DataStore<Preferences> by preferencesDataStore(name = "datauser_profile")
class DataUser(@ApplicationContext private val context: Context){
    private val Username = stringPreferencesKey("Username")
    private val Email = stringPreferencesKey("Email")
    private val NoHp = stringPreferencesKey("NoHp")
    private val TglLahir = stringPreferencesKey("TglLahir")
    private val Kota = stringPreferencesKey("Kota")
    private val Image = stringPreferencesKey("Image")


    suspend fun saveData(
        username : String,
        email : String,
        nohp : String,
        tgllahir: String,
        kota:String,
        image:String ){

        context.dataStore.edit {
            it[Username] = username
            it[Email] = email
            it[NoHp] = nohp
            it[TglLahir] = tgllahir
            it[Kota] = kota
            it[Image] = image
        }    }
    fun getUsername() : Flow<String>{
        return context.dataUser.data.map {
            it[Username] ?: ""
        }
    }
    fun getEmail() : Flow<String>{
        return context.dataUser.data.map {
            it[Email] ?: ""
        }
    }

    fun getDataNoHp() : Flow<String>{
        return context.dataUser.data.map {
            it[NoHp] ?: "undefined"
        }
    }
    fun getDataTglLahir() : Flow<String>{
        return context.dataUser.data.map {
            it[TglLahir] ?: "undefined"
        }
    }
    fun getDataKota() : Flow<String>{
        return context.dataUser.data.map {
            it[Kota] ?: "undefined"
        }
    }
    fun getDataImage() : Flow<String>{
        return context.dataUser.data.map {
            it[Image] ?: "undefined"
        }
    }
    suspend fun deleteDataUser(){
        context.dataUser.edit {
            it.clear()
        }
    }
}