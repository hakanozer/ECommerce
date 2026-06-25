package com.works.data.local

data class AppState(
    val isLoggedIn: Boolean = false,
    val username: String = "",
    var token: String = ""
)