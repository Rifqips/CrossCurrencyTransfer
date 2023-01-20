package com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.rpc.Code
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "datauser")
@Suppress("PrivatePropertyName", "unused")
class DataStoreTransaksi(private val context: Context){

    private val Id = stringPreferencesKey("id")
    private val JenisBank = stringPreferencesKey("jenisBank")
    private val NamaPenerima = stringPreferencesKey("namapenerima")
    private val NoRekening = stringPreferencesKey("norekening")
    private val TipeTransaksi = stringPreferencesKey("tipetransaksi")
    private val Total = stringPreferencesKey("total")
    private val CodeSwift = stringPreferencesKey("codeswift")

    suspend fun saveData(
        id: String,
        jenisBank: String,
        namaPenerima: String,
        noRekening: String,
        tipeTransaksi: String,
        total: String,
        codeSwift: String
    ){
        context.dataStore.edit {

            it[Id] = id
            it[JenisBank] = jenisBank
            it[NamaPenerima] = namaPenerima
            it[NoRekening] = noRekening
            it[TipeTransaksi] = tipeTransaksi
            it[Total] = total
            it[CodeSwift] = codeSwift
        }
    }

    fun getId() : Flow<String>{
        return context.dataStore.data.map {
            it[Id] ?: ""
        }
    }
    fun getJenisBank() : Flow<String>{
        return context.dataStore.data.map {
            it[JenisBank] ?: ""
        }
    }

    fun getNamaPenerima() : Flow<String>{
        return context.dataStore.data.map {
            it[NamaPenerima] ?: "undefined"
        }
    }
    fun getNoRekening() : Flow<String>{
        return context.dataStore.data.map {
            it[NoRekening] ?: "undefined"
        }
    }
    fun getTipeTransaksi() : Flow<String>{
        return context.dataStore.data.map {
            it[TipeTransaksi] ?: "undefined"
        }
    }
    fun getTotal() : Flow<String>{
        return context.dataStore.data.map {
            it[Total] ?: "undefined"
        }
    }
    fun getSwift() : Flow<String>{
        return context.dataStore.data.map {
            it[CodeSwift] ?: "undefined"
        }
    }

    suspend fun clearData(){
        context.dataStore.edit { it.clear() }
    }

    val transaksiId: Flow<String> = context.dataStore.data.map { it[Id] ?: ""}
    val transaksiJenisBank: Flow<String> = context.dataStore.data.map { it[JenisBank] ?: "" }
    val transaksiNamaPenerima: Flow<String> = context.dataStore.data.map { it[NamaPenerima] ?: "" }
    val transaksiNoRekening: Flow<String> = context.dataStore.data.map { it[NoRekening] ?: "" }
    val transaksiTipeTransaksi: Flow<String> = context.dataStore.data.map { it[TipeTransaksi] ?: "" }
    val transaksiTotal: Flow<String> = context.dataStore.data.map { it[Total] ?: "" }
    val codeSwift: Flow<String> = context.dataStore.data.map { it[CodeSwift] ?: "" }
}