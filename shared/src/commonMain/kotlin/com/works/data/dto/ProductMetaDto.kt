package com.works.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductMetaDto (
    val status: Int,
    val message: String,
    val pagination: ProductPaginationDto
)
