package com.example.kotlinproject.models.MangaEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_manga")
data class MangaEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val contentRating: String = "",
    val imageUrl: String = "",
    val year: Int = 0,
    val chapter: Int = 0,
    val description: String = "",
    val imageBytes: ByteArray? = null
)