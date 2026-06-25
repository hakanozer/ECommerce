package com.works.domain

import com.works.data.local.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppStore {

    private val _state = MutableStateFlow(AppState())

    val state: StateFlow<AppState> =
        _state.asStateFlow()

    fun login(
        username: String,
        token: String
    ) {
        _state.value = _state.value.copy(
            isLoggedIn = true,
            username = username,
            token = token
        )
    }

    fun logout() {
        _state.value = AppState()
    }
}