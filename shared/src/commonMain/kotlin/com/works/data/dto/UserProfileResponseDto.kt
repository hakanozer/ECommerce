package com.works.data.dto

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class UserProfileResponseDto (
    val meta: ProfileMeta? = null,
    val data: ProfileData? = null
)

@Serializable
data class ProfileData (
    val id: Long? = null,
    val name: String? = null,
    val email: String? = null,
    val role: String? = null,

    @SerialName("remember_token")
    val rememberToken: JsonElement? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
data class ProfileMeta (
    val status: Long? = null,
    val message: String? = null
)
