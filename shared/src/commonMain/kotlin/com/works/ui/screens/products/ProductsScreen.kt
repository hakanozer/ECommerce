package com.works.ui.screens.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.works.data.dto.ProductItemDto
import com.works.data.remote.ProductApi
import com.works.domain.AppStore
import com.works.ui.components.PaginationBar
import com.works.ui.components.ProductItem
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ProductsScreen(
    onNavigateToDetail: (productId: Int) -> Unit
) {
    val state by AppStore.state.collectAsState()

    val productApi: ProductApi = koinInject()
    val scope = rememberCoroutineScope()

    var products by remember { mutableStateOf<List<ProductItemDto>>(emptyList()) }
    var currentPage by remember { mutableStateOf(1) }
    var totalPages by remember { mutableStateOf(1) }
    var loading by remember { mutableStateOf(false) }

    fun loadProducts(page: Int) {

        if (loading) return

        scope.launch {
            loading = true

            try {

                val response = productApi.getProducts(page)

                products = response.data

                currentPage = response.meta.pagination.page.toInt()
                totalPages = response.meta.pagination.totalPages.toInt()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            loading = false
        }
    }

    LaunchedEffect(Unit) {
        loadProducts(1)
        println("${state.token} - ${state.username}")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(products) { _, product ->
                ProductItem(
                    product = product,
                    onClick = {
                        onNavigateToDetail(product.id.toInt())
                    }
                )
            }
        }

        PaginationBar(
            currentPage = currentPage,
            totalPages = totalPages,
            onPrevious = {
                if (currentPage > 1) {
                    loadProducts(currentPage - 1)
                }
            },
            onNext = {
                if (currentPage <= totalPages) {
                    loadProducts(currentPage + 1)
                    println("page: $currentPage")
                }
            }
        )
    }
}