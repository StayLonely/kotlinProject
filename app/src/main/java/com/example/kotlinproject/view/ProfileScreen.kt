package com.example.kotlinproject.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(modifier: Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Text(text = "Home Screen")
    }
}
