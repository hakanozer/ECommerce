package com.works.ui.navigation


import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

// ─── Top-level graph identifiers ──────────────────────────────────────────────

object Graph {
    const val AUTH = "graph_auth"
    const val MAIN = "graph_main"
}

// ─── Navigation argument keys ──────────────────────────────────────────────────

object NavArgs {
    const val PRODUCT_ID = "productId"
}

// ─── Auth destinations ─────────────────────────────────────────────────────────

@Serializable
sealed class AuthRoute(val route: String) {
    @Serializable
    data object Login  : AuthRoute("auth/login")
    @Serializable
    data object Signup : AuthRoute("auth/signup")
}

// ─── Main (BottomBar) destinations ────────────────────────────────────────────

@Serializable
sealed class MainRoute(
    val route: String,
    val label: String,
    @Transient
    val selectedIcon: ImageVector = Icons.Default.ShoppingBag,
    @Transient
    val unselectedIcon: ImageVector = Icons.Outlined.ShoppingBag,
) {
    // BottomBar tabs ───────────────────────────────────────────────────────────

    @Serializable
    data object Products : MainRoute(
        route         = "main/products",
        label         = "Products",
        selectedIcon  = Icons.Filled.ShoppingBag,
        unselectedIcon = Icons.Outlined.ShoppingBag,
    )

    @Serializable
    data object Likes : MainRoute(
        route         = "main/likes",
        label         = "Likes",
        selectedIcon  = Icons.Filled.FavoriteBorder,
        unselectedIcon = Icons.Outlined.Favorite,
    )

    @Serializable
    data object Profile : MainRoute(
        route         = "main/profile",
        label         = "Profile",
        selectedIcon  = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    )

    // Detail screens (not in BottomBar) ───────────────────────────────────────

    @Serializable
    data object ProductDetail : MainRoute(
        route          = "main/products/{${NavArgs.PRODUCT_ID}}",
        label          = "Product Detail",
        selectedIcon   = Icons.Filled.ShoppingBag,
        unselectedIcon = Icons.Outlined.ShoppingBag,
    ) {
        fun createRoute(productId: Int) = "main/products/$productId"
    }

    companion object {
        /** Only the tabs that appear in the BottomNavigationBar. */
        val bottomBarTabs: List<MainRoute> = listOf(Products, Likes, Profile)
    }
}