package com.example.kotlinproject.models

data class MangaFromApi(
    val data: List<Data>? = null,
    val limit: Int = 0,
    val offset: Int = 0,
    val response: String = "",
    val result: String = "",
    val total: Int = 0
)