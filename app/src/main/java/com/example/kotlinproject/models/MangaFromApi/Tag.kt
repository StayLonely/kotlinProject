package com.example.kotlinproject.models.MangaFromApi

data class Tag(
    val attributes: AttributesX,
    val id: String,
    val relationships: List<Any?>,
    val type: String
)