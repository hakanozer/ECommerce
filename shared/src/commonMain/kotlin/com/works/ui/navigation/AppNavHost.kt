package com.works.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.works.ui.screens.auth.LoginScreen
import com.works.ui.screens.auth.SignupScreen
import com.works.ui.screens.likes.LikesScreen
import com.works.ui.screens.products.ProductDetailScreen
import com.works.ui.screens.products.ProductsScreen
import com.works.ui.screens.profile.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Graph.AUTH,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController    = navController,
        startDestination = startDestination,
        modifier         = modifier,
    ) {
        // ─── Auth Graph ────────────────────────────────────────────────────────
        navigation(
            route = Graph.AUTH,
            startDestination = AuthRoute.Login.route,
        ) {
            composable(route = AuthRoute.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Graph.MAIN) {
                            popUpTo(Graph.AUTH) { inclusive = true }
                        }
                    },
                    onNavigateToSignup = {
                        navController.navigate(AuthRoute.Signup.route)
                    },
                )
            }

            composable(route = AuthRoute.Signup.route) {
                SignupScreen(
                    onSignupSuccess = {
                        navController.navigate(Graph.MAIN) {
                            popUpTo(Graph.AUTH) { inclusive = true }
                        }
                    },
                    onNavigateBack = navController::popBackStack,
                )
            }
        }

        // ─── Main Graph (BottomBar host) ───────────────────────────────────────
        navigation(
            route = Graph.MAIN,
            startDestination = MainRoute.Products.route,
        ) {
            // Products tab ─────────────────────────────────────────────────────
            composable(route = MainRoute.Products.route) {
                ProductsScreen { productId ->
                    navController.navigate(MainRoute.ProductDetail.createRoute(productId))
                }
            }

            composable(
                route = MainRoute.ProductDetail.route,
                arguments = listOf(
                    navArgument(NavArgs.PRODUCT_ID) { type = NavType.IntType },
                ),
            ) { backStackEntry ->
                val productId = backStackEntry.arguments
                    ?.getInt(NavArgs.PRODUCT_ID)
                    ?: return@composable

                ProductDetailScreen(
                    productId = productId,
                    onNavigateBack = navController::popBackStack,
                )
            }

            // Likes tab ────────────────────────────────────────────────────────
            composable(route = MainRoute.Likes.route) {
                LikesScreen(
                    onNavigateToDetail = { productId ->
                        navController.navigate(MainRoute.ProductDetail.createRoute(productId))
                    },
                )
            }

            // Profile tab ──────────────────────────────────────────────────────
            composable(route = MainRoute.Profile.route) {
                ProfileScreen(
                    onLogout = {
                        navController.navigate(Graph.AUTH) {
                            popUpTo(Graph.MAIN) { inclusive = true }
                        }
                    },
                )
            }
        }
    }
}