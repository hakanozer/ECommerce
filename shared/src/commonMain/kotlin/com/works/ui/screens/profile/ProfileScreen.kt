package com.works.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.works.data.dto.UserProfileResponseDto
import com.works.domain.ApiResult
import com.works.domain.AppStore
import com.works.data.remote.AuthApi
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    val authApi: AuthApi = koinInject()
    val state by AppStore.state.collectAsState()
    val scope = rememberCoroutineScope()

    var userProfile by remember { mutableStateOf<UserProfileResponseDto?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            error = null

            val res = authApi.profile(state.token)

            when (res) {
                is ApiResult.Success -> {
                    userProfile = res.data
                }

                is ApiResult.Error -> {
                    error = res.message
                }

                is ApiResult.NetworkError -> {
                    error = res.message
                }
            }

            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil Bilgileri") },
                actions = {
                    TextButton(onClick = {
                        scope.launch {
                            val response = authApi.logout(state.token)
                            println("Resonse : $response")
                            onLogout()
                        }
                    }) {
                        Text("Çıkış")
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }

                error != null -> {
                    Text(
                        text = error ?: "Hata oluştu",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                userProfile?.data != null -> {
                    val data = userProfile!!.data!!

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        // HEADER CARD
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Profil Detayları",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "Hesap bilgilerinizi görüntüleyin",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        // ID
                        ProfileField(label = "ID", value = data.id?.toString(), readOnly = true)

                        // NAME
                        ProfileField(label = "Ad Soyad", value = data.name)

                        // EMAIL
                        ProfileField(label = "E-posta", value = data.email)

                        // ROLE
                        ProfileField(label = "Rol", value = data.role, readOnly = true)

                        // CREATED AT
                        ProfileField(label = "Kayıt Tarihi", value = data.createdAt, readOnly = true)

                        // UPDATED AT
                        ProfileField(label = "Güncellenme", value = data.updatedAt, readOnly = true)

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    val response = authApi.logout(state.token)
                                    println("Resonse : $response")
                                    onLogout()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Çıkış Yap")
                        }
                    }
                }

                else -> {
                    Text(
                        text = "Profil bulunamadı",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String?,
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = value ?: "-",
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        readOnly = readOnly,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )

}


