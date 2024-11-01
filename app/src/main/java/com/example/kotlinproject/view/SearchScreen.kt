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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.kotlinproject.models.Data
import com.example.kotlinproject.models.MangaFromApi

@Composable
fun SearchScreen(modifier: Modifier, navController: NavHostController) {
    val viewModel: MangaViewModel = viewModel()
    val mangaResponse by viewModel.mangaResponse
    val coverUrlsMap by viewModel.coverUrlsMap

    Surface(modifier = modifier.fillMaxSize()) {
        MangaFromApiScreen(mangaList = mangaResponse, coversUrlMap = coverUrlsMap, navController = navController)
    }
}


@Composable
fun MangaFromApiScreen(mangaList: MangaFromApi?, coversUrlMap: Map<String, String>, navController: NavHostController) {
    LazyColumn {
        mangaList?.data?.let { data ->
            items(data, key = { it.id }) { selectedManga ->
                MangaItem(selectedManga, coversUrlMap, onClick = { navController.navigate("MangaDetail/${selectedManga.id}") })
            }
        } ?: run {
            item {
                Text(text = "No manga available", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun MangaItem(data: Data, coversUrlMap: Map<String, String>, onClick: () -> Unit) {
    val title = data.attributes.title.en
    val id = data.id
    val imageUrl = coversUrlMap[id] ?: ""
    val rating = data.attributes.contentRating

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

