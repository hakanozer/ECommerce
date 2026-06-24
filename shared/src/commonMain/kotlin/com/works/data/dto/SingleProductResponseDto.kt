package com.works.data.dto

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class SingleProductResponseDto (
    val meta: ProductMeta,
    val data: ProductData
)

@Serializable
data class ProductData (
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
    val price: Double? = null,
    val discountPercentage: Double? = null,
    val rating: Double? = null,
    val stock: Int? = null,
    val tags: List<String>? = null,
    val brand: String? = null,
    val sku: String? = null,
    val minimumOrderQuantity: Int? = null,
    val images: List<String>? = null
)

@Serializable
data class ProductMeta (
    val status: Int? = null,
    val message: String? = null
)