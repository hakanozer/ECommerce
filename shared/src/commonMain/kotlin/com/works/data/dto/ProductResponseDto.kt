
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
    val title: String,
    val description: String,
    val category: Category,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Long,
    val tags: List<String>,
    val brand: String,
    val sku: String,
    val minimumOrderQuantity: Long,
    val images: List<String>
)

@Serializable
enum class Category(val value: String) {
    @SerialName("beauty") Beauty("beauty"),
    @SerialName("fragrances") Fragrances("fragrances");
}

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
