package com.example.kotlinproject.models.MangaItemFromApi

data class Data(
    val attributes: Attributes,
    val id: String,
    val relationships: List<Relationship>,
    val type: String
)