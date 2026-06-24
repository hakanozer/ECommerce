package com.works.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ProductItemDto (
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("category")
    val category: String = "",
    @SerialName("price")
    val price: Double = 0.0,
    @SerialName("discountPercentage")
    val discountPercentage: Double = 0.0,
    @SerialName("rating")
    val rating: Double = 0.0,
    @SerialName("stock")
    val stock: Int = 0,
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("brand")
    val brand: String? = null,
    @SerialName("sku")
    val sku: String? = null,
    @SerialName("minimumOrderQuantity")
    val minimumOrderQuantity: Int = 0,
    @SerialName("images")
    val images: List<String> = emptyList()
) {
    companion object
}
