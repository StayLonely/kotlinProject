package com.example.kotlinproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.kotlinproject.models.Manga

@Composable
fun MangaDetailScreen(manga: Manga, navController: NavController) {

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Назад")
            }


            Text(
                text = manga.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
            Spacer(modifier = Modifier.height(8.dp))


        val painter = rememberAsyncImagePainter(manga.imageUrl)
        Image(
            painter = painter,
            contentDescription = manga.title,
            modifier = Modifier.size(500.dp).align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Автор: ${manga.author}", style= MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Главы: ${manga.chapters}" , style= MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Описание: ${manga.description} " , style= MaterialTheme.typography.bodyLarge)
    }
}

