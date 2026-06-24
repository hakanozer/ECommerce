package com.works.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto (
    val meta: ProductMetaDto,
    val data: List<ProductItemDto>
)
