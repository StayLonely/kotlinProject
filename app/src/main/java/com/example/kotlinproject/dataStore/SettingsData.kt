package com.example.kotlinproject.dataStore

data class SettingsData(
    val status: String,
    val contentRating: String,
    val tags: MutableList<String>
)
