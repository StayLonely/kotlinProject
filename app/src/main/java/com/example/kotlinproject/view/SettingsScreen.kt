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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Settings", fontWeight = FontWeight.Bold, fontSize = 24.sp)

            Text("Select a Status", style = MaterialTheme.typography.titleLarge)
            StatusCards(statuses, selectedStatus) { status ->
                selectedStatus = status
            }

            Text("Select Content Rating", style = MaterialTheme.typography.titleLarge)
            StatusCards(contentRatings, selectedContentRating) { rating ->
                selectedContentRating = rating
            }

            Text("Select Included Tags", style = MaterialTheme.typography.titleLarge)
            IncludedTagCards(tagsMap.keys.toList(), selectedTags) { tag ->
                if (selectedTags.contains(tag)) {
                    selectedTags.remove(tag)
                } else {
                    selectedTags.add(tag)
                }
            }


            Spacer(modifier = Modifier.weight(1f))


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
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
                    Text("Сбросить")
                }
            }
        }
    }
}

@Composable
fun StatusCards(statuses: List<String>, selectedStatus: String, onStatusSelected: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        statuses.forEach { status ->
            StatusCard(
                status = status,
                isSelected = status == selectedStatus,
                onClick = { onStatusSelected(status) }
            )
        }
    }
}

@Composable
fun StatusCard(status: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp, 50.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.DarkGray else Color.White,
            contentColor = Color.White
        ),
        border = if (isSelected) {
            BorderStroke(2.dp, Color.Blue)
        } else {
            null
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = status)
        }
    }
}

@Composable
fun IncludedTagCards(tags: List<String>, selectedTags: SnapshotStateList<String>, onTagSelected: (String) -> Unit) {
    Column {
        tags.chunked(2).forEach { rowTags ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                rowTags.forEach { tag ->
                    TagCard(
                        tag = tag,
                        isSelected = selectedTags.contains(tag),
                        onClick = { onTagSelected(tag) }
                    )
                }
            }
        }
    }
}
@Composable
fun TagCard(tag: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.DarkGray else Color.LightGray,
            contentColor = Color.White
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = tag, fontWeight = FontWeight.Bold)
        }
    }
}