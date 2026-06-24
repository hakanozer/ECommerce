package com.works.data.remote

import com.works.data.dto.ProductResponseDto
import com.works.data.dto.SingleProductResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProductApi(private val client: HttpClient) {

    suspend fun getProducts(page: Int = 1, per_page: Int = 10) : ProductResponseDto =
        client.get("products?page=$page&per_page=$per_page"){
            contentType(ContentType.Application.Json)
        }.body()

    suspend fun getProduct(productId: Int) : SingleProductResponseDto =
        client.get("products/$productId"){
            contentType(ContentType.Application.Json)
        }.body()

}