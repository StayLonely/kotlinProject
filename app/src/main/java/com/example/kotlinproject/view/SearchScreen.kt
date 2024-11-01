package com.example.kotlinproject.view

import MangaViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.kotlinproject.dataStore.DataStoreManager
import com.example.kotlinproject.models.MangaFromApi.Data
import com.example.kotlinproject.models.MangaFromApi.MangaFromApi

@Composable
fun SearchScreen(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)

    var selectedStatus by remember { mutableStateOf(listOf<String>()) }
    var selectedContentRating by remember { mutableStateOf(listOf<String>()) }
    val selectedTags = remember { mutableStateListOf<String>() }

    // Получаем параметры из DataStore
    LaunchedEffect(key1 = true) {
        dataStoreManager.getSettings().collect { settings ->
            selectedStatus = settings.status.split(",")
            selectedContentRating = settings.contentRating.split(",")
            selectedTags.clear()
            selectedTags.addAll(settings.tags)
        }
    }

    val viewModel: MangaViewModel = viewModel()


    LaunchedEffect(selectedStatus, selectedContentRating, selectedTags) {
        viewModel.fetchManga(selectedStatus, selectedContentRating, selectedTags.toList())
    }

    val mangaResponse by viewModel.mangaResponse

    Surface(modifier = modifier.fillMaxSize()) {
        MangaFromApiScreen(mangaList = mangaResponse, navController = navController)
    }
}


@Composable
fun MangaFromApiScreen(mangaList: MangaFromApi?, navController: NavHostController) {
    LazyColumn {
        mangaList?.data?.let { data ->
            items(data, key = { it.id }) { selectedManga ->
                MangaItem(selectedManga, onClick = { navController.navigate("MangaDetail/${selectedManga.id}") })
            }
        } ?: run {
            item {
                Text(text = "No manga available", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun MangaItem(data: Data, onClick: () -> Unit) {
    val title = data.attributes.title.en
    val id = data.id
    val rating = data.attributes.contentRating

    val fileName = data.relationships.firstOrNull { it.type == "cover_art" }
        ?.attributes
        ?.fileName

    val imageUrl = "https://uploads.mangadex.org/covers/${id}/${fileName}.512.jpg"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 8.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Text(text = "Rating: $rating", style = MaterialTheme.typography.bodySmall)
        }
    }
}

