import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

import com.example.kotlinproject.models.Manga

@Composable
fun MangaItem(manga: Manga, onFavoriteClick: (Manga) -> Unit , onClick: () -> Unit) {
    var isFavorite by remember { mutableStateOf(manga.isFavorite) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(manga.imageUrl),
            contentDescription = manga.title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 8.dp)
        )
        Column(modifier = Modifier.weight(1f).clickable(onClick = onClick)) {
            Text(text = manga.title, style = MaterialTheme.typography.h2)
            Text(text = "Rating: ${manga.rating}", style = MaterialTheme.typography.body1)
        }
        IconButton(onClick = {
            isFavorite = !isFavorite
            onFavoriteClick(manga.copy(isFavorite = isFavorite))
        }) {
            Icon(imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = "Favorite")
        }
    }
}