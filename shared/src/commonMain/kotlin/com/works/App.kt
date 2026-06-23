package com.works

import androidx.compose.runtime.*
import com.works.di.networkModule
import com.works.ui.navigation.MainScaffold
import com.works.ui.theme.ECommerceTheme
import org.koin.core.context.startKoin
import org.koin.dsl.module


@Composable
fun App() {
    initKoin()
    ECommerceTheme { MainScaffold(isLoggedIn = false) }
}

fun initKoin() {
    startKoin {
        modules(networkModule)
    }
}