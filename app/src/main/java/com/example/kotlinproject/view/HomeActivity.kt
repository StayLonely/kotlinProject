package com.example.kotlinproject.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.kotlinproject.components.MangaList
import com.example.kotlinproject.components.getFakeDataBase
import com.example.kotlinproject.models.Manga

@Composable
fun HomeScreen(modifier: Modifier, navController: NavHostController
) {
    Surface(modifier = modifier.fillMaxSize()) {
        val mangaList = getFakeDataBase();
        MangaList(mangaList = mangaList) { selectedManga ->
            navController.navigate("MangaDetail/${selectedManga.id}")
        }
    }

    }
