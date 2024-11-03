package com.example.kotlinproject.viewmodel.MangaViewModel

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.kotlinproject.dao.MangaDao
import com.example.kotlinproject.data.AppDatabase.AppDatabase
import com.example.kotlinproject.models.MangaEntity.MangaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import androidx.compose.runtime.State

class MangaViewWorkWithLikes(application: Application) : AndroidViewModel(application) {
    private val mangaDao: MangaDao = AppDatabase.getDatabase(application).mangaDao()
    private val _favoriteMangaList = mutableStateListOf<MangaEntity>()
    val favoriteMangaList: List<MangaEntity> = _favoriteMangaList
    private val _mangaById = mutableStateOf<MangaEntity?>(null)




    fun loadFavoriteManga() {
        viewModelScope.launch {
            _favoriteMangaList.clear()
            _favoriteMangaList.addAll(mangaDao.getAllFavoriteManga())
        }
    }

    fun addMangaToFavorite(manga: MangaEntity, imageUrl: String? = null) {
        viewModelScope.launch {

            if (!imageUrl.isNullOrEmpty()) {
                saveImageToDatabase(manga, imageUrl)
            } else {
                mangaDao.insert(manga)
            }
            _favoriteMangaList.clear()
            _favoriteMangaList.addAll(mangaDao.getAllFavoriteManga())
        }
    }

    fun removeMangaFromFavorite(manga: MangaEntity) {
        viewModelScope.launch {
            mangaDao.delete(manga)
            loadFavoriteManga()
        }
    }
    fun findMangaById(mangaId: String) {
        viewModelScope.launch {
            val manga = mangaDao.getMangaById(mangaId)
            _mangaById.value = manga
        }
    }

    private suspend fun saveImageToDatabase(manga: MangaEntity, imageUrl: String) {
        val context = getApplication<Application>().applicationContext

        val bitmap = withContext(Dispatchers.IO) {
            try {
                Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get()
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

        bitmap?.let { bmp ->

            val byteArrayOutputStream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()


            val mangaWithImage = manga.copy(imageBytes = byteArray)
            mangaDao.insert(mangaWithImage)
        }
    }
}
