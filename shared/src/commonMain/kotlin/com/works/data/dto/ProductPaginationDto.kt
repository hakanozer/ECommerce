package com.works.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ProductPaginationDto (
    val page: Int,

    @SerialName("per_page")
    val perPage: Int,

    @SerialName("total_items")
    val totalItems: Int,

    @SerialName("total_pages")
    val totalPages: Int
)
