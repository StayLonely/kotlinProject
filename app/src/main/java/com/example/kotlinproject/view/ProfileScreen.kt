package com.example.kotlinproject.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import coil3.compose.rememberAsyncImagePainter
import com.example.kotlinproject.models.userProfile.UserProfile
import com.example.kotlinproject.models.userProfile.UserProfileManager

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun ProfileScreen(modifier: Modifier, onEditProfileClick: () -> Unit) {
    val context = LocalContext.current;
    val userProfileManager = UserProfileManager(context = context)
    val userProfile = userProfileManager.loadUserProfile()
    Log.d("Image", userProfile.avatarUri.toString())
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Профиль", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "ФИО: ${userProfile.fullName}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Должность: ${userProfile.position}", style = MaterialTheme.typography.bodySmall)

            // Отображаем аватарку, если она доступна
            userProfile.avatarUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Аватарка",
                    modifier = Modifier.size(100.dp) // Задайте размер изображения
                )
            } ?: run {
                Text(text = "Аватарка не установлена", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp)) // Отступ перед кнопками

            // Кнопка Редактировать
            Button(onClick = onEditProfileClick, modifier = Modifier.fillMaxWidth()) {
                Text("Редактировать")
            }
            Spacer(modifier = Modifier.height(8.dp))  // Отступ между кнопками

            // Кнопка Резюме
            Button(onClick = { saveResume(context, userProfile)
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Резюме")
            }
        }
    }
}

private fun saveResume(context: Context, userProfile: UserProfile) {

    try {
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Resumes")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = "${userProfile.fullName.replace(" ", "_")}_Resume.txt"
        val file = File(directory, fileName)

        FileOutputStream(file).use { output ->
            output.write("ФИО: ${userProfile.fullName}\n".toByteArray())
            output.write("Должность: ${userProfile.position}\n".toByteArray())
        }

        Toast.makeText(context, "Резюме сохранено в ${file.absolutePath}", Toast.LENGTH_LONG).show()

    } catch (e: Exception) {
        Log.e("ProfileScreen", "Ошибка при сохранении резюме: ${e.message}")
        Toast.makeText(context, "Ошибка при сохранении резюме", Toast.LENGTH_SHORT).show()
    }
}
