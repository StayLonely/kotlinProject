package com.example.kotlinproject.data.api

import com.example.kotlinproject.models.CoversFromApi
import com.example.kotlinproject.models.MangaFromApi
import com.example.kotlinproject.models.MangaItemFromApi
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/manga?limit=10&includes[]=cover_art")
    suspend fun getManga(): Response<MangaFromApi>

    @GET("/cover")
    suspend fun getCover(@Query("manga[]") coverIds: List<String>): Response<CoversFromApi>

    @GET("/manga/{id}")
    suspend fun getMangaWithId(@Path("id") id: String): Response<MangaItemFromApi>
}