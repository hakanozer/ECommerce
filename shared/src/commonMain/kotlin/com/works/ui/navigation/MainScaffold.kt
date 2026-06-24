package com.works.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * Root composable that owns the [NavHostController] and renders the
 * BottomNavigationBar only while the user is inside the Main graph.
 */
@Composable
fun MainScaffold(isLoggedIn: Boolean) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val isBottomBarVisible = MainRoute.bottomBarTabs.any { tab ->
        currentDestination?.hierarchy?.any { it.route == tab.route } == true
    }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                NavigationBar {
                    MainRoute.bottomBarTabs.forEach { tab ->
                        val isSelected = currentDestination
                            ?.hierarchy
                            ?.any { it.route == tab.route } == true

                        NavigationBarItem(
                            selected = isSelected,
                            icon     = {
                                Icon(
                                    imageVector  = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.label,
                                )
                            },
                            label    = { Text(tab.label) },
                            onClick  = {
                                navController.navigate(tab.route) {
                                    // Avoid building up a large back stack
                                    popUpTo(navController.graph.findStartDestination()) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState    = true
                                }
                            },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        AppNavHost(
            navController    = navController,
            startDestination = if (isLoggedIn) Graph.MAIN else Graph.AUTH,
            modifier         = Modifier.padding(innerPadding),
        )
    }
}