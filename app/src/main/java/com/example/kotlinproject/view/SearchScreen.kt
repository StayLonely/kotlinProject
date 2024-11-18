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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.bumptech.glide.Glide
import com.example.kotlinproject.dao.MangaDao
import com.example.kotlinproject.dataStore.DataStoreManager
import com.example.kotlinproject.models.MangaEntity.MangaEntity
import com.example.kotlinproject.models.MangaFromApi.Data
import com.example.kotlinproject.models.MangaFromApi.MangaFromApi
import com.example.kotlinproject.viewmodel.MangaViewModel.MangaViewWorkWithLikes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.material3.CircularProgressIndicator

import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.java.KoinJavaComponent.inject
import java.io.ByteArrayOutputStream


@Composable
fun SearchScreen(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)
    val viewModel: MangaViewModel = viewModel()


    LaunchedEffect(dataStoreManager) { viewModel.fetchManga(dataStoreManager) }


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
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun MangaItem(data: Data, onClick: () -> Unit) {

    val title = data.attributes.title.en.takeIf { !it.isNullOrEmpty() }
        ?: data.attributes.title.ja.takeIf { !it.isNullOrEmpty() }
        ?: "Не удалось получить название"
    val id = data.id
    val rating = data.attributes.contentRating

    val fileName = data.relationships.firstOrNull { it.type == "cover_art" }
        ?.attributes
        ?.fileName

    val imageUrl = "https://uploads.mangadex.org/covers/${id}/${fileName}.512.jpg"

    val chapter = if (data.attributes.lastChapter.isNotEmpty()) {
        data.attributes.lastChapter.toDoubleOrNull()?.toInt() ?: 0 // Преобразуем к Double, затем к Int
    } else {
        0
    }

    // Получаем экземпляр MangaViewWorkWithLikes из ViewModel
    val mangaViewWorkWithLikes: MangaViewWorkWithLikes = viewModel()

    // Проверяем, является ли манга избранной
    val isFavorite = mangaViewWorkWithLikes.favoriteMangaList.any { it.id == id }

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

        IconButton(onClick = {
            val mangaEntity = MangaEntity(id, title, rating, imageUrl, data.attributes.year , chapter, data.attributes.description.en)
            if (isFavorite) {
                mangaViewWorkWithLikes.removeMangaFromFavorite(mangaEntity)
            } else {
                mangaViewWorkWithLikes.addMangaToFavorite(mangaEntity, imageUrl )

            }
        }) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                tint = if (isFavorite) Color.Red else Color.Gray
            )
        }
    }
}


