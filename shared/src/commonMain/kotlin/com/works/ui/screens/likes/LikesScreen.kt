package com.works.ui.screens.likes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.works.data.dto.ProductItemDto
import com.works.data.local.AppDatabase
import com.works.domain.AppStore
import com.works.ui.components.ProductItem
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun LikesScreen(
    onNavigateToDetail: (productId: Int) -> Unit
) {

    val state by AppStore.state.collectAsState()
    val database: AppDatabase = koinInject()
    var products by remember { mutableStateOf<List<ProductItemDto>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val dbProducts = database.productQueries.selectAllProducts().executeAsList()
            products = dbProducts.map { product ->
                ProductItemDto(
                    id = product.id.toInt(),
                    title = product.title,
                    description = product.description,
                    category = product.category,
                    price = product.price,
                    discountPercentage = product.discountPercentage,
                    rating = product.rating,
                    stock = product.stock.toInt(),
                    tags = product.tags.split(","),
                    brand = product.brand,
                    sku = product.sku,
                    minimumOrderQuantity = product.minimumOrderQuantity.toInt(),
                    images = product.images.split(",")
                )
            }
        }
        AppStore.login("Ali Bilmem", state.token)
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
    }

}