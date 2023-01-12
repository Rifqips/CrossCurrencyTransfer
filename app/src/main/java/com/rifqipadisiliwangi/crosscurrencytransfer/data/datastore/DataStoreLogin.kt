package com.rifqipadisiliwangi.crosscurrencytransfer.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datauser")
class DataStoreLogin(private val context: Context){

    private val Username = stringPreferencesKey("Username")
    private val Email = stringPreferencesKey("Email")
    private val PW = stringPreferencesKey("password")
    private val PWU = stringPreferencesKey("passwordulang")

    suspend fun saveData(
        username : String,
        email: String,
        pw:String,
        pwu:String ){

        context.dataStore.edit {

            it[Username] = username
            it[Email] = email
            it[PW] = pw
            it[PWU] = pwu
        }    }
    suspend fun clearData(){
        context.dataStore.edit { it.clear() } }

    val userName: Flow<String> = context.dataStore.data.map { it[Username] ?: ""}
    val userEmail: Flow<String> = context.dataStore.data.map { it[Email] ?: "" }
    val userPw: Flow<String> = context.dataStore.data.map { it[PW] ?: "" }
    val userPWU: Flow<String> = context.dataStore.data.map { it[PWU] ?: "" }}