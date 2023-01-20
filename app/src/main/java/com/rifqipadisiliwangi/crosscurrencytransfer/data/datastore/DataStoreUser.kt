package com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody

val Context.dataStoreUser: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "dsuser")
@Suppress("PrivatePropertyName", "unused")
class DataStoreUser(private val context: Context) {

    private val NoPhone = stringPreferencesKey("noPhone")
    private val NamaUser = stringPreferencesKey("namaUser")
    private val FotoUser = stringPreferencesKey("fotoUser")

    suspend fun saveData(
        noPhone: String,
        namaUser: String,
        fotoUser: String,
    ) {
        context.dataStoreUser.edit {
            it[NoPhone] = noPhone
            it[NamaUser] = namaUser
            it[FotoUser] = fotoUser.toString()
        }
    }

    @JvmName("getNoPhone1")
    fun getNoPhone(): Flow<String> {
        return context.dataStoreUser.data.map {
            it[NoPhone] ?: ""
        }
    }


    @JvmName("getNamaUser1")
    fun getNamaUser(): Flow<String> {
        return context.dataStoreUser.data.map {
            it[NamaUser] ?: ""
        }
    }


    @JvmName("getFotoUser1")
    fun getFotoUser(): Flow<String> {
        return context.dataStoreUser.data.map {
            it[FotoUser] ?: "undefined"
        }
    }

    suspend fun clearData() {
        context.dataStoreUser.edit { it.clear() }
    }

    val noPhone: Flow<String> = context.dataStoreUser.data.map { it[NoPhone] ?: "" }
    val namaUser: Flow<String> = context.dataStoreUser.data.map { it[NamaUser] ?: "" }
    val fotoUser: Flow<String> = context.dataStoreUser.data.map { it[FotoUser] ?: "" }
}