package com.example.kotlinproject.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinproject.models.userProfile.UserProfileManager
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import com.example.kotlinproject.models.userProfile.UserProfile

@Composable
fun EditProfileScreen(userProfileManager: UserProfileManager, onSave: () -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var resumeUrl by remember { mutableStateOf("") }
    var avatarUri by remember { mutableStateOf<String?>(null) }

    // Лаунчер для выбора изображения
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        avatarUri = uri.toString()  // Сохраните выбранный URI
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Редактирование профиля", style = MaterialTheme.typography.titleLarge)
            TextField(value = fullName, onValueChange = { fullName = it }, label = { Text("ФИО") })
            TextField(value = position, onValueChange = { position = it }, label = { Text("Должность") })
            TextField(value = resumeUrl, onValueChange = { resumeUrl = it }, label = { Text("URL резюме") })

            // Кнопка для выбора изображения
            Button(onClick = { imageLauncher.launch("image/*") }) {
                Text("Выбрать аватар")
            }

            // Кнопка для сохранения данных профиля
            Button(onClick = {
                val userProfile = UserProfile(fullName, avatarUri, resumeUrl, position)
                userProfileManager.saveUserProfile(userProfile)
                onSave()  // Выход на экран профиля
            }) {
                Text("Готово")
            }
        }
    }
}