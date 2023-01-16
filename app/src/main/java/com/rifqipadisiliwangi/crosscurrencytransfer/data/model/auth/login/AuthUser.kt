package com.rifqipadisiliwangi.crosscurrencytransfer.data.model.auth.login

data class UserLogin (
    val email : String,
    val password : String
)

data class ResponseUserLogin(
    val token : String
)