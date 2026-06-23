package com.works.data.dto

import kotlinx.serialization.Serializable

@Serializable data class UserLoginRequestDto(
    val email: String,
    val password: String,
)
