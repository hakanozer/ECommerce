package com.works.ui.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.works.data.dto.ProductData
import com.works.data.remote.ProductApi
import com.works.data.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onNavigateBack: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val productApi: ProductApi = koinInject()
    val database: AppDatabase = koinInject()

    var isLikes by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var product by remember { mutableStateOf<ProductData?>(null) }

    // SnackbarManager bağlama
    LaunchedEffect(Unit) {
        scope.launch {
            database.productQueries.selectProductId(productId.toLong()).executeAsOneOrNull()?.let {
                isLikes = true
            }
        }
        SnackbarManager.snackbarHostState = snackbarHostState
    }

    LaunchedEffect(productId) {
        isLoading = true
        try {
            val response = productApi.getProduct(productId)
            product = response.data
        } catch (e: Exception) {
            SnackbarManager.showError(scope, "Ürün yüklenemedi")
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                modifier = Modifier.heightIn(min = 80.dp),
                windowInsets = WindowInsets(0),
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
        },
        bottomBar = {

            Button(
                onClick = {
                    product?.let { data ->
                        scope.launch {
                            try {
                                val dbPro = database.productQueries.selectProductId(data.id?.toLong() ?: 0L).executeAsOneOrNull()
                                if (dbPro != null) {
                                    database.productQueries.deleteProductByPid(dbPro.pid)
                                    SnackbarManager.showSuccess(scope, "Favorilerden Çıkarıldı")
                                    isLikes = false
                                }else {
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
                                    isLikes = true
                                    SnackbarManager.showSuccess(scope, "Favorilere eklendi")
                                }

                            } catch (e: Exception) {
                                SnackbarManager.showError(scope, "Bu ürün daha önce eklenmiş")
                                isLikes = false
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = if (isLikes) "Favorilerden Çıkar" else "Favorilere Ekle")
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

        val pagerState = rememberPagerState(
            pageCount = { data.images?.size ?: 0 }
        )

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // IMAGE SLIDER
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
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Stok: ${data.stock ?: 0}")
                Text(text = "⭐ ${data.rating ?: 0.0}")

                Spacer(modifier = Modifier.height(16.dp))

                if (!data.tags.isNullOrEmpty()) {

                    Text(
                        text = "Etiketler",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(data.tags) { tag ->
                            AssistChip(
                                onClick = {},
                                label = { Text(tag) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = "Açıklama",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = data.description ?: "")

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


object SnackbarManager {

    lateinit var snackbarHostState: SnackbarHostState

    fun showSuccess(
        scope: CoroutineScope,
        message: String = "Ürün favorilere eklendi"
    ) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    fun showError(
        scope: CoroutineScope,
        message: String = "İşlem başarısız oldu"
    ) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
        }
    }
}