package com.example.kotlinproject.view

import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import coil3.network.HttpException
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinproject.models.MangaItemFromApi.MangaItemFromApi

import com.example.kotlinproject.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


@Composable
fun MangaDetailScreenFromApi(id: String, navController: NavController) {

    val context = LocalContext.current

    var mangaResponse by remember {
        mutableStateOf(MangaItemFromApi())
    }


    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var chapter by remember { mutableStateOf("") }



    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO){
            val response = try {
                RetrofitInstance.api.getMangaWithId(id)

            } catch (e: HttpException){
                Toast.makeText(context,"http error: ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            } catch (e: IOException){
                Toast.makeText(context, "error: ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main) {
                    mangaResponse = response.body()!!
                    val data = mangaResponse.data!!


                    title = data.attributes.title.en.ifEmpty { "Неизвестное название" }
                    description = data.attributes.description.en?.takeIf { it.isNotEmpty() } ?: "Нет описания"
                    year = data.attributes.year?.takeIf { it.isNotEmpty() } ?: "Нет информации о годе"
                    val fileName = data.relationships.firstOrNull { it.type == "cover_art" }
                        ?.attributes
                        ?.fileName ?: "default_cover"


                    imageUrl = "https://uploads.mangadex.org/covers/${data.id}/$fileName.512.jpg"
                     chapter = data.attributes.lastChapter?.takeIf { it.isNotEmpty() } ?: "Нет информации о главах"
                }
            }

        }
    }

    val scrollState = rememberScrollState()

    Log.d("Зашли", String.toString())


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
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))


        AndroidView(
            factory = { context ->
                ImageView(context).apply {
                }
            },
            update = { imageView ->
                Glide.with(imageView.context)
                    .load(imageUrl)
                    .apply(RequestOptions.overrideOf(512, 512))
                    .into(imageView)
            },
            modifier = Modifier.size(500.dp).align(Alignment.CenterHorizontally)
        )


        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Год: ${year}", style= MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Главы: ${chapter}" , style= MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Описание: ${description} " , style= MaterialTheme.typography.bodyLarge)
    }
}



