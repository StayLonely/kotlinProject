package com.example.kotlinproject.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.kotlinproject.components.MangaList
import com.example.kotlinproject.components.getFakeDataBase
import com.example.kotlinproject.models.Manga

@Composable
fun HomeScreen(modifier: Modifier, navController: NavHostController) {

    val mangaList = getFakeDataBase();

    Surface(modifier = modifier.fillMaxSize()) {
        MangaList(mangaList = mangaList) { selectedManga ->
            navController.navigate("MangaDetail/${selectedManga.id}")
        }
    }

}

