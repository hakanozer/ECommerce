package com.works.ui.screens.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProductsScreen(
    onNavigateToDetail : (productId: Int) -> Unit
) {
    Column {
        Text(text = "Products Screen")
    }
}