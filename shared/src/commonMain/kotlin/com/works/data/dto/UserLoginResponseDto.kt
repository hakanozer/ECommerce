package com.works.data.dto

import kotlinx.serialization.Serializable

@Serializable data class UserLoginResponseDto(
    val meta: Meta,
    val data: Data,
)

@Serializable data class Meta(
    val status: Long,
    val message: String,
)

@Serializable data class Data(
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
    val user: User,
)

@Serializable data class User(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val remember_token: String?,
    val created_at: String,
    val updated_at: String,
)




