package com.works

import androidx.compose.runtime.*
import com.works.ui.navigation.MainScaffold
import com.works.ui.theme.ECommerceTheme


@Composable
fun App() {
    ECommerceTheme { MainScaffold(isLoggedIn = true) }
}