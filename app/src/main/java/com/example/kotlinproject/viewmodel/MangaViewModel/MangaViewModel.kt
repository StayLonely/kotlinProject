import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinproject.models.Data
import com.example.kotlinproject.models.MangaFromApi
import com.example.kotlinproject.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MangaViewModel : ViewModel() {
    private val _mangaResponse = mutableStateOf<MangaFromApi?>(null)
    val mangaResponse = _mangaResponse

    private val _coverUrlsMap = mutableStateOf(mutableMapOf<String, String>())
    val coverUrlsMap = _coverUrlsMap

    init {
        fetchManga()
    }

    private fun fetchManga() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getManga()
                if (response.isSuccessful && response.body() != null) {
                    _mangaResponse.value = response.body()
                    loadCoverImages(response.body()?.data)
                }
            } catch (e: Exception) {

            }
        }
    }

    private suspend fun loadCoverImages(mangaData: List<Data>?) {
        mangaData?.let { data ->
            val coverIds = data.map { it.id }
            val coverResponse = RetrofitInstance.api.getCover(coverIds)

            if (coverResponse.isSuccessful && coverResponse.body() != null) {
                val coverData = coverResponse.body()!!.data
                coverData?.forEach { cover ->
                    val id = cover.relationships.first { it.type == "manga" }.id
                    val fileName = cover.attributes.fileName
                    val fullUrl = "https://uploads.mangadex.org/covers/$id/$fileName.256.jpg"
                    _coverUrlsMap.value[id] = fullUrl
                }
            }
        }
    }
}