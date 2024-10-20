
package com.example.kotlinproject

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.navigation.NavController

@Composable
fun BottomNav(navController: NavController) {
    val bottomNavController = remember { mutableStateOf(0) }

    BottomNavigation {
        val navItems = listOf(
            "Home" to Icons.Filled.Home,
            "Search" to Icons.Filled.Search,
            "Settings" to Icons.Filled.Settings,
            "Profile" to Icons.Filled.Person
        )

        navItems.forEachIndexed { index, pair ->
            val (item, icon) = pair
            BottomNavigationItem(
                icon = { Icon(icon, contentDescription = null) },
                label = { Text(item, style = MaterialTheme.typography.body1) },
                selected = bottomNavController.value == index,
                onClick = {
                    bottomNavController.value = index
                    navController.navigate(item) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
