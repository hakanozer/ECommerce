package com.works.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector
import ecommerce.shared.generated.resources.Res
import ecommerce.shared.generated.resources.nav_likes
import ecommerce.shared.generated.resources.nav_product_detail
import ecommerce.shared.generated.resources.nav_products
import ecommerce.shared.generated.resources.nav_profile
import org.jetbrains.compose.resources.StringResource

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

sealed class AuthRoute(val route: String) {
    data object Login  : AuthRoute("auth/login")
    data object Signup : AuthRoute("auth/signup")
}

// ─── Main (BottomBar) destinations ────────────────────────────────────────────

sealed class MainRoute(
    val route: String,
    val labelRes: StringResource,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    // BottomBar tabs ───────────────────────────────────────────────────────────

    data object Products : MainRoute(
        route         = "main/products",
        labelRes      = Res.string.nav_products,
        selectedIcon  = Icons.Filled.ShoppingBag,
        unselectedIcon = Icons.Outlined.ShoppingBag,
    )

    data object Likes : MainRoute(
        route         = "main/likes",
        labelRes      = Res.string.nav_likes,
        selectedIcon  = Icons.Filled.FavoriteBorder,
        unselectedIcon = Icons.Outlined.Favorite,
    )

    data object Profile : MainRoute(
        route         = "main/profile",
        labelRes      = Res.string.nav_profile,
        selectedIcon  = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    )

    // Detail screens (not in BottomBar) ───────────────────────────────────────

    data object ProductDetail : MainRoute(
        route          = "main/products/{${NavArgs.PRODUCT_ID}}",
        labelRes       = Res.string.nav_product_detail,
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