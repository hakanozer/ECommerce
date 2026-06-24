
package com.works.data.dto

import kotlinx.serialization.*

@Serializable
data class ProductResponseDto (
    val meta: MetaProduct,
    val data: List<Datum>
)

@Serializable
data class Datum (
    val id: Long,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Long = 0,
    val tags: List<String> = emptyList(),
    val brand: String? = null,
    val sku: String? = null,
    val minimumOrderQuantity: Long = 0,
    val images: List<String> = emptyList()
)



@Serializable
data class MetaProduct (
    val status: Long,
    val message: String,
    val pagination: Pagination
)

@Serializable
data class Pagination (
    val page: Long,

    @SerialName("per_page")
    val perPage: Long,

    @SerialName("total_items")
    val totalItems: Long,

    @SerialName("total_pages")
    val totalPages: Long
)
