package com.example.kotlinproject.models

import com.example.kotlinproject.components.getFakeDataBase

data class Manga(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Float,
    var isFavorite: Boolean,
    var author: String,
    var chapters: Int,
    var description: String

)


fun getMangaById(id: Int): Manga? {

    val mangaList = getFakeDataBase()

    return mangaList.find { it.id == id }
}
