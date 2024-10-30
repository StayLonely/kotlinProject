package com.example.kotlinproject.models.MangaFromApi

data class Data(
    val attributes: Attributes,
    val id: String,
    val relationships: List<Relationship>,
    val type: String
)