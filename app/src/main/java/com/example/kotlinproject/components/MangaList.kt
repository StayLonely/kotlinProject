package com.example.kotlinproject.components

import MangaItem
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import coil3.compose.rememberAsyncImagePainter
import com.example.kotlinproject.models.Manga

@Composable
fun MangaList(mangaList: List<Manga>, onMangaClick: (Manga) -> Unit) {
    LazyColumn {
        items(mangaList) { manga ->
            // Каждый элемент списка манги
            MangaItem(manga = manga, onFavoriteClick = { }, onClick = { onMangaClick(manga) });
        }
    }
}



