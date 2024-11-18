package com.example.kotlinproject.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinproject.models.MangaEntity.MangaEntity


@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(manga: MangaEntity)

    @Delete
    suspend fun delete(manga: MangaEntity)

    @Query("SELECT * FROM favorite_manga")
    suspend fun getAllFavoriteManga(): List<MangaEntity>

    @Query("SELECT * FROM favorite_manga WHERE id = :mangaId LIMIT 1")
    suspend fun getMangaById(mangaId: String): MangaEntity?
}