package com.example.kotlinproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.kotlinproject.models.userProfile.UserProfileManager

import com.example.kotlinproject.ui.theme.MyAppTheme
import com.example.kotlinproject.view.EditProfileScreen
import com.example.kotlinproject.view.FavoritesScreen

import com.example.kotlinproject.view.MangaDetailScreenFromApi
import com.example.kotlinproject.view.ProfileScreen
import com.example.kotlinproject.view.SearchScreen
import com.example.kotlinproject.view.SettingsScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = "Favorite", modifier = modifier) {
        composable("Favorite") { FavoritesScreen(modifier, navController) }
        composable("Search") { SearchScreen(modifier, navController) }
        composable("Settings") { SettingsScreen(modifier) }
        composable("Profile") {
            ProfileScreen(
                modifier = modifier,
                onEditProfileClick = { navController.navigate("EditProfile") }  // Навигация к редактированию профиля
            )
        }

        composable("EditProfile") {
            EditProfileScreen(
                userProfileManager = UserProfileManager(context = LocalContext.current),
                onSave = { navController.popBackStack() } // Вернуться назад после сохранения
            )
        }

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

