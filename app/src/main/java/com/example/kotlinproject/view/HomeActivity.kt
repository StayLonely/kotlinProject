package com.example.kotlinproject.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import com.example.kotlinproject.models.MangaEntity.MangaEntity
import com.example.kotlinproject.viewmodel.MangaViewModel.MangaViewWorkWithLikes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun FavoritesScreen(modifier: Modifier = Modifier, navController: NavHostController ) {

    val mangaViewWorkWithLikes: MangaViewWorkWithLikes = viewModel()
    LaunchedEffect(key1 = true) {
        mangaViewWorkWithLikes.loadFavoriteManga()
    }

    val mangaList = mangaViewWorkWithLikes.favoriteMangaList

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Избранная манга",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(mangaList, key = { it.id }) { manga ->
                    MangaItem(manga,
                        onClick = { navController.navigate("MangaDetailFromDataStore /${manga.id}") },
                        onRemoveClick = { mangaViewWorkWithLikes.removeMangaFromFavorite(manga) }
                    )
                }
            }
        }
    }
}

@Composable
fun MangaItem(
    mangaEntity: MangaEntity,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    val title = mangaEntity.title
    val imageUrl = mangaEntity.imageUrl
    val contentRating = mangaEntity.contentRating
    val year: Int = mangaEntity.year
    val chapter: Int = mangaEntity.chapter
    val description: String = mangaEntity.description

    val imageBytes = mangaEntity.imageBytes
    val imageBitmap = imageBytes?.let {
        BitmapFactory.decodeByteArray(it, 0, it.size)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Показ изображения
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap.asImageBitmap(),
                    contentDescription = title,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    imageVector = Icons.Filled.Image,
                    contentDescription = "Default Image",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Информация о манге
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Год: $year", style = MaterialTheme.typography.bodySmall)
                Text(text = "Глава: $chapter", style = MaterialTheme.typography.bodySmall)
                Text(text = "Рейтинг: $contentRating", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }

            IconButton(onClick = onRemoveClick) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Remove from favorites")
            }
        }
    }
}

