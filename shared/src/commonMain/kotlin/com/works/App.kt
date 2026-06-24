package com.works

import androidx.compose.runtime.*
import com.works.di.networkModule
import com.works.di.platformDatabaseModule
import com.works.ui.navigation.MainScaffold
import com.works.ui.theme.ECommerceTheme
import org.koin.core.context.startKoin


@Composable
fun App() {
    initKoin()
    ECommerceTheme { MainScaffold(isLoggedIn = false) }
}

private var koinStarted = false
fun initKoin() {
    if (!koinStarted) {
        startKoin {
            modules(networkModule, platformDatabaseModule())
        }
        koinStarted = true
    }
}