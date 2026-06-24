package com.works.ui.screens.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.works.data.remote.ProductApi
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ProductsScreen(
    onNavigateToDetail : (productId: Int) -> Unit
) {

    val productApi: ProductApi = koinInject()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val response = productApi.getProducts()
            println("Products: ${response.data}")
        }
    }

    Column {
        Text(text = "Products Screen")
    }
}