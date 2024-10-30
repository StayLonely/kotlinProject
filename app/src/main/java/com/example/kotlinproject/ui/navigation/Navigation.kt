package com.example.kotlinproject

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.network.HttpException

import com.example.kotlinproject.ui.theme.MyAppTheme
import com.example.kotlinproject.utils.RetrofitInstance
import com.example.kotlinproject.view.HomeScreen

import com.example.kotlinproject.view.MangaDetailScreenFromApi
import com.example.kotlinproject.view.ProfileScreen
import com.example.kotlinproject.view.SearchScreen
import com.example.kotlinproject.view.SettingsScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = "Home", modifier = modifier) {
        composable("Home") { HomeScreen(modifier, navController) }
        composable("Search") { SearchScreen(modifier, navController) }
        composable("Settings") { SettingsScreen(modifier) }
        composable("Profile") { ProfileScreen(modifier) }
        composable("MangaDetail/{mangaId}") { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getString("mangaId")
            mangaId?.let {
                    MyAppTheme{
                    //MangaDetailScreen(manga, navController)}

                        MangaDetailScreenFromApi(mangaId, navController)
                    }

            }
        }
    }
}

