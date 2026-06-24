package com.works.ui.screens.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.works.data.remote.ProductApi
import org.koin.compose.koinInject

@Composable
fun ProductDetailScreen(
    productId: Int,
    onNavigateBack: () -> Unit
) {

    val productApi: ProductApi = koinInject()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val singleProduct = productApi.getProduct(productId)
        println(singleProduct.data)
    }

    Column {
        Text(text = "Signup Screen: $productId")
    }
}