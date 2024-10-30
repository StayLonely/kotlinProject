package com.example.kotlinproject.data.api

import com.example.kotlinproject.models.MangaFromApi.MangaFromApi
import com.example.kotlinproject.models.MangaItemFromApi.MangaItemFromApi
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/manga?limit=10&includes[]=cover_art")
    suspend fun getManga(): Response<MangaFromApi>

    @GET("/manga/{id}")
    suspend fun getMangaWithId(
        @Path("id") id: String,
        @Query("includes[]") includes: List<String> = listOf("cover_art")
    ): Response<MangaItemFromApi>
}