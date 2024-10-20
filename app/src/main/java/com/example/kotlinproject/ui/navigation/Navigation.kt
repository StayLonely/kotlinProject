package com.example.kotlinproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import com.example.kotlinproject.models.getMangaById
import com.example.kotlinproject.ui.theme.MyAppTheme
import com.example.kotlinproject.view.HomeScreen
import com.example.kotlinproject.view.MangaDetailScreen
import com.example.kotlinproject.view.ProfileScreen
import com.example.kotlinproject.view.SearchScreen
import com.example.kotlinproject.view.SettingsScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = "Home", modifier = modifier) {
        composable("Home") { HomeScreen(modifier, navController) }
        composable("Search") { SearchScreen(modifier) }
        composable("Settings") { SettingsScreen(modifier) }
        composable("Profile") { ProfileScreen(modifier) }
        composable("MangaDetail/{mangaId}") { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getString("mangaId")
            mangaId?.let {
                val manga = getMangaById(it.toInt())
                if (manga != null) {
                    MyAppTheme{
                    MangaDetailScreen(manga, navController)}
                }
            }
        }
    }
}

