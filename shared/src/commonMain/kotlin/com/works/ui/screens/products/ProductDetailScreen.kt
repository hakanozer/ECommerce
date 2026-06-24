package com.works.ui.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.works.data.dto.ProductData
import com.works.data.remote.ProductApi
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.works.data.local.AppDatabase

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onNavigateBack: () -> Unit
) {

    val productApi: ProductApi = koinInject()
    val database: AppDatabase = koinInject()
    val scope = rememberCoroutineScope()


    var isLoading by remember { mutableStateOf(true) }
    var product by remember { mutableStateOf<ProductData?>(null) }

    LaunchedEffect(productId) {
        isLoading = true
        val response = productApi.getProduct(productId)
        product = response.data
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.heightIn(min = 80.dp),
                windowInsets = WindowInsets(0), // 🔥 üst boşluğu sıfırlar
                title = {
                    Text(text = product?.title ?: "Ürün Detayı")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
            Button(
                onClick = {
                    product?.let { data ->
                        scope.launch {
                            database.productQueries.insertProduct(
                                id = data.id?.toLong() ?: 0L,
                                title = data.title ?: "",
                                description = data.description ?: "",
                                category = data.category ?: "",
                                price = data.price ?: 0.0,
                                discountPercentage = data.discountPercentage ?: 0.0,
                                rating = data.rating ?: 0.0,
                                stock = data.stock?.toLong() ?: 0L,
                                tags = data.tags?.joinToString(",") ?: "",
                                brand = data.brand,
                                sku = data.sku,
                                minimumOrderQuantity = data.minimumOrderQuantity?.toLong() ?: 0L,
                                images = data.images?.joinToString(",") ?: ""
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Favorilere Ekle")
            }
        }
    ) { padding ->

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        val data = product ?: return@Scaffold

        val pagerState = rememberPagerState(pageCount = {
            data.images?.size ?: 0
        })

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // ---------------- IMAGE SLIDER ----------------
            if (!data.images.isNullOrEmpty()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) { page ->

                    AsyncImage(
                        model = data.images[page],
                        contentDescription = data.title,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // simple indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(data.images.size) { index ->
                        val color =
                            if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .padding(2.dp)
                                .background(color, RoundedCornerShape(50))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------------- TITLE + BRAND ----------------
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                Text(
                    text = data.title ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = data.brand ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ---------------- PRICE SECTION ----------------
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "${data.price} ₺",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    if ((data.discountPercentage ?: 0.0) > 0) {
                        Text(
                            text = "%${data.discountPercentage} indirim",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ---------------- STOCK + RATING ----------------
                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(
                        text = "Stok: ${data.stock ?: 0}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "⭐ ${data.rating ?: 0.0}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ---------------- TAGS ----------------
                if (!data.tags.isNullOrEmpty()) {
                    Text(
                        text = "Etiketler",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        data.tags.forEach { tag ->
                            AssistChip(
                                onClick = {},
                                label = { Text(tag) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // ---------------- DESCRIPTION ----------------
                Text(
                    text = "Açıklama",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = data.description ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}