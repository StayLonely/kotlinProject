package com.example.kotlinproject.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlinproject.dataStore.DataStoreManager
import com.example.kotlinproject.dataStore.SettingsData
import com.example.kotlinproject.utils.Util.contentRatings
import com.example.kotlinproject.utils.Util.statuses
import com.example.kotlinproject.utils.Util.tagsMap
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)

    var selectedStatus by remember { mutableStateOf("Select Status") }
    var selectedContentRating by remember { mutableStateOf("Select Content Rating") }
    var selectedTags = remember { mutableStateListOf<String>() }

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        dataStoreManager.getSettings().collect { settings ->
            selectedStatus = settings.status
            selectedContentRating = settings.contentRating
            selectedTags = settings.tags.toMutableStateList()
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Settings", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {


            Button(
                onClick = {
                    coroutine.launch {
                        dataStoreManager.saveSettings(
                            SettingsData(selectedStatus, selectedContentRating, selectedTags)
                        )
                    }
                }

            ) {
                Text("Применить")
            }

            Button(
                onClick = {
                    coroutine.launch {
                        dataStoreManager.resetSettings()
                    }
                }
            ) {
                Text("Сброс")
            }
        }


            Text(
                "Select a Status",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                color = Color.Gray
            )
            StatusCards(statuses, selectedStatus) { status ->
                selectedStatus = status
            }

            Text(
                "Select Content Rating",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                color = Color.Gray
            )
            StatusCards(contentRatings, selectedContentRating) { rating ->
                selectedContentRating = rating
            }

            Text(
                "Select Included Tags",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                color = Color.Gray
            )
            IncludedTagCards(tagsMap.keys.toList(), selectedTags) { tag ->
                if (selectedTags.contains(tag)) {
                    selectedTags.remove(tag)
                } else {
                    selectedTags.add(tag)
                }
            }

                Spacer(Modifier.weight(1f)) // Заполнение оставшегося пространства
            }
        }
}

@Composable
fun StatusCards(items: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().wrapContentSize()) {
        Card(
            modifier = Modifier
                .clickable { expanded = true }, // Открытие выпадающего меню
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Отображение текущего выбранного статуса
                Text(
                    text = selected,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1, // Не более одной строки
                    overflow = TextOverflow.Ellipsis, // Обрезка текста с многоточием
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth() // Заполнить ширину карточки
                )
            }
        }

        // Выпадающее меню
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { status ->
                DropdownMenuItem(onClick = {
                    onSelect(status) // Передает выбранный статус
                    expanded = false // Закрыть меню после выбора
                }, text = {Text(
                        text = status,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = if (status == selected) FontWeight.Bold else FontWeight.Normal) })
            }
        }
    }
}

@Composable
fun IncludedTagCards(tags: List<String>, selectedTags: List<String>, onTagClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Интервал между тегами
    ) {
        items(tags) { tag ->
            Card(
                modifier = Modifier
                    .clickable { onTagClick(tag) }
                    .padding(4.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(if (selectedTags.contains(tag)) 2.dp else 0.dp, Color.Blue),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier.padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tag,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center // Выравнивание текста по центру
                    )
                }
            }
        }
    }
}