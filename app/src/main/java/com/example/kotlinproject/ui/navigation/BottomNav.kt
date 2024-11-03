
package com.example.kotlinproject

import android.util.Log
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.kotlinproject.dataStore.DataStoreManager
import com.example.kotlinproject.utils.Util.getTagIdsByNames
import com.example.kotlinproject.utils.Util.isFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun BottomNav(navController: NavController) {
    val bottomNavController = remember { mutableStateOf(0) }

    BottomNavigation {
        val navItems = listOf(
            "Favorite" to Icons.Filled.Favorite,
            "Search" to Icons.Filled.Search,
            "Settings" to Icons.Filled.Settings,
            "Profile" to Icons.Filled.Person
        )
        val dataStoreManager = DataStoreManager(LocalContext.current)
        LaunchedEffect(key1=true) {
            dataStoreManager.getSettings().collect { settings ->

                val savedStatus = settings.status.split(",").filter { it.isNotBlank() }
                val savedContentRating =
                    settings.contentRating.split(",").filter { it.isNotBlank() }
                val savedTags = getTagIdsByNames(settings.tags)

                isFilter.value = savedStatus.isNotEmpty() || savedContentRating.isNotEmpty() || (savedTags != null && savedTags.isNotEmpty())

            }
        }

        navItems.forEachIndexed { index, pair ->

            val (item, icon) = pair
            Log.d("ABOBA", icon.name)
            BottomNavigationItem(

                icon = {
                    BadgedBox(
                        badge=  {
                            if(isFilter.value && icon.name == "Filled.Search"){
                                Badge()
                            }
                        }
                    ) {
                        Icon(icon, contentDescription = null)
                    }
                },
               // icon = { Icon(icon, contentDescription = null) },
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
